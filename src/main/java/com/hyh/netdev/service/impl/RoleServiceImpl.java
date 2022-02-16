package com.hyh.netdev.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyh.netdev.dao.RoleMapper;
import com.hyh.netdev.entity.Role;
import com.hyh.netdev.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author Albumen
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
