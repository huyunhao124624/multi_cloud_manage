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
 * @author fish
 * @since 2022-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("private_resource_limit")
public class PrivateResourceLimit extends Model<PrivateResourceLimit> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "private_resource_limit_id", type = IdType.AUTO)
    private Long privateResourceLimitId;

    @TableField("user_id")
    private Integer userId;

    @TableField("cpu_limit")
    private Integer cpuLimit;

    @TableField("memory_limit")
    private Integer memoryLimit;

    @TableField("disk_limit")
    private Integer diskLimit;


    @Override
    protected Serializable pkVal() {
        return this.privateResourceLimitId;
    }

}
