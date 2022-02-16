package com.hyh.netdev.service;

import com.hyh.netdev.bo.UserLoginResultBo;
import com.hyh.netdev.vo.UserInformation;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Albumen
 */
public interface SecurityService {
    /**
     * 签发Token
     *
     * @param userInformation 用户信息
     * @return token信息
     */
    String signToken(UserInformation userInformation);

    /**
     * 刷新 Token
     *
     * @param request HTTP请求
     * @return 用户信息
     */
    UserLoginResultBo refresh(HttpServletRequest request);

    /**
     * 下线用户
     *
     * @param userId 用户ID
     */
    void offlineUser(Integer userId);
}
