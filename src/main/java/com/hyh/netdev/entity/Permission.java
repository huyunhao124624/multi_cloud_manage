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
@TableName(value = "permission")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission {
    /**
     * 权限ID
     */
    @TableId(value = "permission_id", type = IdType.INPUT)
    private Integer permissionId;

    /**
     * 权限名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "descrption")
    private String descrption;

    public static final String COL_NAME = "name";

    public static final String COL_DESCRPTION = "descrption";
}