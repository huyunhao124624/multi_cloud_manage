package com.hyh.netdev.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyh.netdev.entity.UserEmail;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyh
 * @since 2020-10-17
 */
public interface UserEmailService extends IService<UserEmail> {



    @Async
    public void sendRegisterActivateEmail(Integer userId,String email,String backendUrl) throws Exception;
    @Async
    public void sendProductConfirmEmail(Integer orderId,String productName,Integer productNum,Integer totalMoney,String desEmail,String backendUrl) throws Exception;

    public void testAsync();
}
