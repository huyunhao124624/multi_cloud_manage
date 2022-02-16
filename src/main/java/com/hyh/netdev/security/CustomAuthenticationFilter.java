package com.hyh.netdev.security;



import com.alibaba.fastjson.JSONObject;
import com.hyh.netdev.constant.ResultConstant;
import com.hyh.netdev.constant.SystemConst;
import com.hyh.netdev.security.util.JwtUtil;
import com.hyh.netdev.security.util.RedisUtil;
import com.hyh.netdev.util.PageCodeUtil;
import com.hyh.netdev.vo.UserInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * 认证状态检测
 *
 * @author Albumen
 */
public class CustomAuthenticationFilter extends BasicAuthenticationFilter {
    private final static Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);
    private JwtUtil jwtUtil;
    private RedisUtil redisUtil;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.redisUtil = redisUtil;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //File file = new File("/etc/jupyterhub/slog.txt");
        /**
        if(!file.exists()){
            file.createNewFile();
        }
        PrintStream ps = new PrintStream(new FileOutputStream(file));
        ps.append(request.getHeader(SystemConst.AUTHORIZATION));
         */
        String test = request.getHeader(SystemConst.AUTHORIZATION);

        if (request.getHeader(SystemConst.AUTHORIZATION) != null && request.getHeader(SystemConst.AUTHORIZATION).startsWith(SystemConst.AUTHORIZATION_PREFIX)) {
            String token = request.getHeader(SystemConst.AUTHORIZATION);
            //System.out.println(token);
            if (token != null && !token.isEmpty()) {
                String jwt = token.substring(SystemConst.AUTHORIZATION_PREFIX.length());
                String uuid = jwtUtil.getUuid(jwt);
                Integer userId = jwtUtil.getUserId(jwt);

                if (uuid != null) {
                    boolean isSuccess = redisUtil.contain(SystemConst.STATE_PREFIX + userId, uuid);
                    if (isSuccess) {
                        //验证成功
                        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                        UserInformation userInformation = JSONObject.parseObject(redisUtil.get(SystemConst.INFORMATION_PREFIX + userId), UserInformation.class);
                        List<String> permissionList = userInformation.getPermission();
                        if (Objects.nonNull(permissionList) && permissionList.size() > 0) {
                            for (String permission : permissionList) {
                                authorities.add(new GrantedAuthorityImpl("ROLE_" + permission));
                            }
                        }
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
                        authentication.setDetails(userInformation);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }
            //验证失败
            logger.trace("Login Failed");
            PageCodeUtil.printCode(response, ResultConstant.TOKEN_EXPIRE, 401);
        } else {
            //验证失败
            logger.trace("Login Failed");
            PageCodeUtil.printCode(response, ResultConstant.TOKEN_EXPIRE, 401);
        }
    }
}
