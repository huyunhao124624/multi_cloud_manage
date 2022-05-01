package com.hyh.netdev.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Table;
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
    private Double hourPricing;

    @TableField("half_month_pricing")
    private Double halfMonthPricing;

    @TableField("month_pricing")
    private Double monthPricing;

    @TableField("two_month_pricing")
    private Double twoMonthPricing;

    @TableField("three_month_pricing")
    private Double threeMonthPricing;

    @TableField("half_year_pricing")
    private Double halfYearPricing;

    @TableField("year_pricing")
    private Double yearPricing;

    @TableField("cloud_provider")
    private String cloudProvider;

    @TableId(value="vm_meta_id",type = IdType.AUTO)
    private Long vmMetaId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
