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
 * @since 2022-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ami_meta")
public class AmiMeta extends Model<AmiMeta> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ami_id", type = IdType.AUTO)
    private String amiId;

    @TableField("ami_name")
    private String amiName;

    @TableField("cloud_provider")
    private String cloudProvider;

    @TableField("ami_out_id")
    private String amiOutId;


    @Override
    protected Serializable pkVal() {
        return this.amiId;
    }

}
