package com.hyh.netdev.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyh.netdev.bo.RegisterValidBo;
import com.hyh.netdev.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Albumen
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询账号对应的用户ID
     *
     * @param account 账号
     * @return 查询结果
     */
    User selectByAccount(@Param("account") String account);









}