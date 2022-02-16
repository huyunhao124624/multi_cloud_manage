package com.hyh.netdev.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Albumen
 */
@Data
@TableName(value = "user_role")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRole {
    /**
     * 用户ID（此处为一对多）
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private Integer userId;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private Integer roleId;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_PASSWORD = "password";
}