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
import java.math.BigDecimal;

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
@TableName("disk_meta")
public class DiskMeta extends Model<DiskMeta> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "disk_meta_id", type = IdType.AUTO)
    private Long diskMetaId;

    @TableField("gbprice_per_hour")
    private BigDecimal gbpricePerHour;

    @TableField("gbprice_per_month")
    private String gbpricePerMonth;

    @TableField("gbprice_per_week")
    private String gbpricePerWeek;

    @TableField("cloud_provider")
    private String cloudProvider;


    @Override
    protected Serializable pkVal() {
        return this.diskMetaId;
    }

}
