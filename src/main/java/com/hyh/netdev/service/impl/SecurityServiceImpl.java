package com.hyh.netdev.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.hyh.netdev.bo.UserLoginResultBo;
import com.hyh.netdev.constant.SystemConst;
import com.hyh.netdev.security.util.JwtUtil;
import com.hyh.netdev.security.util.RedisUtil;
import com.hyh.netdev.service.SecurityService;
import com.hyh.netdev.vo.UserInformation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author Albumen
 */
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private final static Logger logger = LoggerFactory.getLogger(SecurityService.class);
    @NonNull
    private RedisUtil redisUtil;
    @NonNull
    private JwtUtil jwtUtil;

    @Override
    public String signToken(UserInformation userInformation) {
        try {
            // Redis缓存用户信息
            redisUtil.set(SystemConst.INFORMATION_PREFIX + userInformation.getUserId(), JSONObject.toJSONString(userInformation));

            // 生成 Token
            String uuid = UUID.randomUUID().toString();
            String token = jwtUtil.create(uuid, userInformation.getUserId());
            redisUtil.add(SystemConst.STATE_PREFIX + userInformation.getUserId(), uuid);
            return SystemConst.AUTHORIZATION_PREFIX + token;
        } catch (Exception e) {
            logger.error("Redis 处理失败", e);
            return "";
        }
    }

    @Override
    public UserLoginResultBo refresh(HttpServletRequest request) {
        // 获取登陆信息
        String token = request.getHeader(SystemConst.AUTHORIZATION).substring(SystemConst.AUTHORIZATION_PREFIX.length());
        String uuid = jwtUtil.getUuid(token);
        Integer userId = jwtUtil.getUserId(token);
        UserInformation userInformation = JSONObject.parseObject(redisUtil.get(SystemConst.INFORMATION_PREFIX + userId), UserInformation.class);

        // 移除原登陆信息
        redisUtil.remove(SystemConst.STATE_PREFIX + userInformation.getUserId(), uuid);

        // 生成新 Token、刷新用户信息有效期
        uuid = UUID.randomUUID().toString();
        token = jwtUtil.create(uuid, userInformation.getUserId());
        redisUtil.add(SystemConst.STATE_PREFIX + userInformation.getUserId(), uuid);
        redisUtil.renew(SystemConst.INFORMATION_PREFIX + userId);

        // 生成返回信息
        UserLoginResultBo userLoginResultBo = new UserLoginResultBo();
        userLoginResultBo.setRole(userInformation.getRole());
        userLoginResultBo.setToken(SystemConst.AUTHORIZATION_PREFIX + token);

        return userLoginResultBo;
    }

    @Override
    public void offlineUser(Integer userId) {
        redisUtil.delete(SystemConst.INFORMATION_PREFIX + userId);
        redisUtil.delete(SystemConst.STATE_PREFIX + userId);
    }
}
