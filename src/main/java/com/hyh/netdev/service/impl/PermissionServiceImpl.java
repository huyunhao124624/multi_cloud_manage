package com.hyh.netdev.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyh.netdev.dao.PermissionMapper;
import com.hyh.netdev.entity.Permission;
import com.hyh.netdev.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * @author Albumen
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
