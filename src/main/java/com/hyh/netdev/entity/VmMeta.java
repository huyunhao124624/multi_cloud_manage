package com.hyh.netdev.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vm_meta")
public class VmMeta extends Model<VmMeta> {

    private static final long serialVersionUID = 1L;

    @TableField("cpu")
    private Integer cpu;

    @TableField("memory")
    private Integer memory;

    @TableField("instance_type")
    private String instanceType;

    @TableField("hour_pricing")
    private String hourPricing;

    @TableField("month_pricing")
    private String monthPricing;

    @TableField("cloud_provider")
    private String cloudProvider;

    @TableField("vm_meta_id")
    private Integer vmMetaId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
