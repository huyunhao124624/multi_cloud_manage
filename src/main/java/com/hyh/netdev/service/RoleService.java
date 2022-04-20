package com.hyh.netdev.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hyh.netdev.bo.role.GetAllRoleListBo;
import com.hyh.netdev.dao.RoleMapper;
import com.hyh.netdev.entity.Role;
import com.hyh.netdev.vo.Result;

import java.util.List;

/**
 * @author hyh
 */
public interface RoleService extends IService<Role> {
    Result<List<GetAllRoleListBo>> getAllRoleList();
}
