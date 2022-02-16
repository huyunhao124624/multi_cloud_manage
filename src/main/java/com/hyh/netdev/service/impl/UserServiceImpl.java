package com.hyh.netdev.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyh.netdev.bo.*;
import com.hyh.netdev.constant.ResultConstant;
import com.hyh.netdev.dao.*;
import com.hyh.netdev.entity.*;
import com.hyh.netdev.security.util.RedisUtil;
import com.hyh.netdev.service.SecurityService;
import com.hyh.netdev.service.UserEmailService;
import com.hyh.netdev.service.UserService;
import com.hyh.netdev.vo.Result;
import com.hyh.netdev.vo.UserInformation;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author hyh
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final static String TIME_STAT_PREFIX = "UserStatTime-";


    @NonNull
    private UserMapper userMapper;
    @NonNull
    private UserEmailMapper userEmailMapper;
    @NonNull
    private UserEmailService userEmailService;
    @NonNull
    private UserRoleMapper userRoleMapper;

    @NonNull
    private RolePermissionMapper rolePermissionMapper;
    @NonNull
    private PermissionMapper permissionMapper;
    @NonNull
    private SecurityService securityService;
    @NonNull
    private RedisUtil redisUtil;




    @Value("${file.prefixURL}")
    private String prefixUrl;
    @Value("${file.uploadPath}")
    private String uploadPath;

    @Value("${backend.url}")
    private String backendUrl;











    @SuppressWarnings("unchecked")
    @Override
    public Result<UserLoginResultBo> login(UserLoginBo userLoginBo) {
        // 1、判断参数是否齐全
        if (Objects.isNull(userLoginBo.getUsername()) || Objects.isNull(userLoginBo.getPassword())) {
            return ResultConstant.ARGS_ERROR;
        }

        // 2、查询账号是否存在
        User user = getBaseMapper().selectByAccount(userLoginBo.getUsername());
        if (Objects.isNull(user)) {
            return ResultConstant.USERNAME_PASSWORD_ERROR;
        }

        // 2.1判断用户是否激活
        UserEmail userEmail = userEmailMapper.selectOne(new QueryWrapper<UserEmail>().eq("user_id",user.getUserId()));
        if(userEmail.getActivateStatus() == 0){
            return ResultConstant.USER_NOT_ACTIVATED;
        }

        // 3、查询账号角色信息
        UserRole userRole = userRoleMapper.selectById(user.getUserId());
        if (!userRole.getPassword().equals(userLoginBo.getPassword())) {
            return ResultConstant.USERNAME_PASSWORD_ERROR;
        }

        // 4、查询角色对应权限信息
        List<Permission> permissionList = permissionMapper.selectBatchIds(rolePermissionMapper.selectPermission(userRole.getRoleId()));
        List<String> permission = new LinkedList<>();
        permissionList.forEach(e -> permission.add(e.getName()));



        // 5、调用安全框架生成 token
        UserInformation userInformation = new UserInformation();
        userInformation.setUserId(user.getUserId());
        userInformation.setRole(userRole.getRoleId());
        userInformation.setPermission(permission);
        String token = securityService.signToken(userInformation);

        // 6、包装结果
        UserLoginResultBo userLoginResultBo = new UserLoginResultBo();
        userLoginResultBo.setRole(userRole.getRoleId());
        userLoginResultBo.setToken(token);

        // 7、重置实验开始时间
        redisUtil.delete(TIME_STAT_PREFIX + user.getUserId());

        return new Result<>(userLoginResultBo);
    }








    @SuppressWarnings("unchecked")
    @Override
    public Result<ImgUploadBo> imgUpload(MultipartFile file) {
        try {
            // 检测是否为图片
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(file.getInputStream());
            if (!ImageIO.getImageReaders(imageInputStream).hasNext()) {
                imageInputStream.close();
                return ResultConstant.FILE_ERROR;
            }
            imageInputStream.close();

            // 写入本地
            String filename = UUID.randomUUID() + ".jpg";
            Thumbnails
                    .of(file.getInputStream())
                    .scale(1)
                    .outputQuality(0.5f)
                    .outputFormat("jpg")
                    .toFile(uploadPath + filename);
            return new Result<>(new ImgUploadBo(filename, prefixUrl + filename));
        } catch (IOException e) {
            logger.warn("文件上传错误", e);
            return ResultConstant.FILE_ERROR;
        }
    }

    @Override
    @Transactional
    public Result register(RegisterBo registerBo) throws Exception {

        User existUser = userMapper.selectOne(new QueryWrapper<User>().eq("account",registerBo.getUsername()));
        if(existUser != null){
            return ResultConstant.ACCOUNT_ALLOCATED;
        }
        UserEmail existEmail = userEmailMapper.selectOne(new QueryWrapper<UserEmail>().eq("email",registerBo.getEmail()));
        if(existEmail != null){
            return ResultConstant.EMAIL_USED;
        }

        User user = new User();
        user.setAccount(registerBo.getUsername());
        userMapper.insert(user);

        UserRole userRole = new UserRole();
        userRole.setRoleId(1);
        userRole.setPassword(registerBo.getPassword());
        userRole.setUserId(user.getUserId());
        userRoleMapper.insert(userRole);

        UserEmail userEmail = new UserEmail();
        userEmail.setEmail(registerBo.getEmail());
        userEmail.setActivateStatus(0);
        userEmail.setUserId(user.getUserId());

        userEmailMapper.insert(userEmail);


        userEmailService.sendRegisterActivateEmail(user.getUserId(),registerBo.getEmail(),backendUrl);

        return ResultConstant.SUCCESS;
    }

    @Override
    @Transactional
    public Result<ActivateAccountBo> activateAccount(String token) {
        String s = redisUtil.get("email-active-" + token);
        Integer userId = Integer.parseInt(s);
        UserEmail userEmail = new UserEmail();
        userEmail.setUserId(userId);
        userEmail.setActivateStatus(1);

        Integer updated = userEmailMapper.update(userEmail,new UpdateWrapper<UserEmail>().eq("user_id",userId));
        User user = userMapper.selectById(userId);
        ActivateAccountBo activateAccountBo = new ActivateAccountBo();
        activateAccountBo.setUserId(userId);
        activateAccountBo.setUserName(user.getAccount());
        return new Result(activateAccountBo);
    }




}
