package com.hyh.netdev.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyh.netdev.bo.GetAccountListBo;
import com.hyh.netdev.bo.role.GetAllRoleListBo;
import com.hyh.netdev.dao.RoleMapper;
import com.hyh.netdev.entity.Role;
import com.hyh.netdev.service.RoleService;
import com.hyh.netdev.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Albumen
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Result<List<GetAllRoleListBo>> getAllRoleList() {
        List<Role> roleList = roleMapper.selectList(new QueryWrapper<>());
        List<GetAllRoleListBo> resultList = new ArrayList<>();
        roleList.forEach((role)->{
            GetAllRoleListBo getAllRoleListBo = new GetAllRoleListBo();
            getAllRoleListBo.setRoleId(role.getRoleId()+"");
            getAllRoleListBo.setRoleName(role.getName());
            resultList.add(getAllRoleListBo);
        });

        return new Result<>(resultList);
    }
}
