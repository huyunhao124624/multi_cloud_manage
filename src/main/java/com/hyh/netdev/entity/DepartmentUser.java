package com.hyh.netdev.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author hyh
 * @since 2022-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("department_user")
public class DepartmentUser {
    @TableField("user_id")
    private Integer userId;

    @TableField("department_id")
    private String departmentId;
}
