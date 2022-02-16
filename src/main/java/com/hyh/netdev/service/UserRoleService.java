package com.hyh.netdev.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hyh.netdev.bo.PasswordUpdateBo;
import com.hyh.netdev.entity.UserRole;
import com.hyh.netdev.vo.Result;

/**
 * @author Albumen
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 个人更新密码
     *
     * @param userId           用户ID
     * @param passwordUpdateBo 密码信息
     * @return 更新结果
     */
    Result updatePasswordSelf(Integer userId, PasswordUpdateBo passwordUpdateBo);

    /**
     * 上级修改下级密码
     *
     * @param userId  上级ID
     * @param newData 下级信息
     * @return 更新结果
     */
    Result updatePasswordAdmin(Integer userId, UserRole newData);
}
