package com.hyh.netdev.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyh.netdev.entity.UserEmail;
import com.hyh.netdev.dao.UserEmailMapper;
import com.hyh.netdev.security.util.RedisUtil;
import com.hyh.netdev.service.IMailService;
import com.hyh.netdev.service.UserEmailService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyh
 * @since 2020-10-17
 */
@Service
@AllArgsConstructor
public class UserEmailServiceImpl extends ServiceImpl<UserEmailMapper, UserEmail> implements UserEmailService {

    IMailService iMailService;
    RedisUtil redisUtil;

    @Override
    @Async
    public void sendRegisterActivateEmail(Integer userId,String email,String backendUrl) throws Exception {
        String myUUID = UUID.randomUUID()+"";
        String redisKey = "email-active-"+myUUID;
        redisUtil.set(redisKey,String.valueOf(userId));
        iMailService.sendSimpleMail(email,"hyh商城注册确认邮件","欢迎注册，请点击如下链接对账号进行激活\n"+backendUrl+"/userActivate?token="+myUUID);
    }

    @Async
    public void sendProductConfirmEmail(Integer orderId,String productName,Integer productNum,Integer totalMoney,String desEmail,String backendUrl) throws Exception {
        String myUUID = UUID.randomUUID()+"";
        String redisKey = "product-confirm-"+myUUID;
        redisUtil.set(redisKey,String.valueOf(orderId));
        iMailService.sendSimpleMail(desEmail,"确认收货邮件","您在hyh商城购买了"+productName+"x"+productNum+"\n总金额:"+totalMoney+"\n"+"请点击一下链接进行确认收货\n"+backendUrl+"/confirmProduct?token="+myUUID);
    }

    @Override
    public void testAsync() {
        return;
    }


}
