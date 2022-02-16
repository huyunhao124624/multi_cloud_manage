package com.hyh.netdev.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyh.netdev.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Albumen
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}