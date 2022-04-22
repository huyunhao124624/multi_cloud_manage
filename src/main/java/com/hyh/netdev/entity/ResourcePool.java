package com.hyh.netdev.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyh
 * @since 2022-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("resource_pool")
public class ResourcePool extends Model<ResourcePool> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "resource_pool_id",type = IdType.AUTO)
    private Long resourcePoolId;

    @TableField("cpu_limit")
    private Integer cpuLimit;

    @TableField("memory_limit")
    private Integer memoryLimit;

    @TableField("cpu_used")
    private Integer cpuUsed;

    @TableField("memory_used")
    private Integer memoryUsed;

    @TableField("disk_used")
    private Integer diskUsed;

    @TableField("disk_limit")
    private Integer diskLimit;

    @TableField("department_id")
    private Long departmentId;

    @TableField("resource_pool_type")
    private String resourcePoolType;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
