package com.hyh.netdev.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserEmail extends Model<UserEmail> {

    private static final long serialVersionUID = 1L;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField("email")
    private String email;

    @TableField("activate_status")
    private Integer activateStatus;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
