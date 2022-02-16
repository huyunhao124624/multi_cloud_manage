package com.hyh.netdev.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyh.netdev.bo.PasswordUpdateBo;
import com.hyh.netdev.constant.ResultConstant;
import com.hyh.netdev.dao.UserRoleMapper;
import com.hyh.netdev.entity.UserRole;
import com.hyh.netdev.service.UserRoleService;
import com.hyh.netdev.vo.Result;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Albumen
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Override
    public Result updatePasswordSelf(Integer userId, PasswordUpdateBo passwordUpdateBo) {
        // 判断旧密码
        UserRole userRole = getBaseMapper().selectById(userId);
        if (Objects.isNull(userRole) || !userRole.getPassword().equals(passwordUpdateBo.getOldPassword())) {
            return ResultConstant.PASSWORD_ERROR;
        }

        // 更新密码
        userRole.setPassword(passwordUpdateBo.getNewPassword());
        getBaseMapper().updateById(userRole);
        return ResultConstant.SUCCESS;
    }

    @Override
    public Result updatePasswordAdmin(Integer userId, UserRole newData) {
        // 判断空数据
        if (Objects.isNull(newData.getUserId()) || Objects.isNull(newData.getPassword())) {
            return ResultConstant.ARGS_ERROR;
        }

        // 获取下级信息
        UserRole userRole = getBaseMapper().selectById(newData.getUserId());
        if (Objects.isNull(userRole)) {
            return ResultConstant.ACCOUNT_NOT_FOUND;
        }

        // 获取上级权限信息
        UserRole admin = getBaseMapper().selectById(userId);

        // 判断权限
        if (admin.getRoleId() <= userRole.getRoleId()) {
            return ResultConstant.PERMISSION_ERROR;
        }

        // 更新密码
        userRole.setPassword(newData.getPassword());
        getBaseMapper().updateById(userRole);
        return ResultConstant.SUCCESS;
    }
}
