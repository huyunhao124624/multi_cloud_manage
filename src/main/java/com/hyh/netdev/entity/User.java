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
@TableName(value = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 头像文件名（不要带域名）
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 性别（0-未知、1-男、2-女）
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 账号（学号、工号）
     */
    @TableField(value = "account")
    private String account;

    /**
     * 学院
     */
    @TableField(value = "profess")
    private String profess;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    public static final String COL_AVATAR = "avatar";

    public static final String COL_NAME = "name";

    public static final String COL_SEX = "sex";

    public static final String COL_ACCOUNT = "account";

    public static final String COL_PROFESS = "profess";

    public static final String COL_PHONE = "phone";
}