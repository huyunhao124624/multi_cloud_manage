package com.hyh.netdev.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyh.netdev.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Albumen
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    /**
     * 获取权限
     *
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Integer> selectPermission(Integer roleId);
}