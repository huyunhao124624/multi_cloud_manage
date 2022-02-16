package com.hyh.netdev.service;


import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyh.netdev.bo.*;
import com.hyh.netdev.entity.User;
import com.hyh.netdev.vo.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Albumen
 */
public interface UserService extends IService<User> {










    /**
     * 用户登录（第一次登陆）
     *
     * @param userLoginBo 登陆信息
     * @return 登陆结果
     */
    Result<UserLoginResultBo> login(UserLoginBo userLoginBo);







    /**
     * 上传图片文件
     *
     * @param file 文件信息
     * @return 提交结果
     */
    Result<ImgUploadBo> imgUpload(MultipartFile file);


    /**
     * 注册接口
     * @param registerBo
     * @return
     */
    Result register(RegisterBo registerBo) throws Exception;

    Result<ActivateAccountBo> activateAccount(String token);


}
