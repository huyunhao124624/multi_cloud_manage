package com.hyh.netdev.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("resource")
public class Resource extends Model<Resource> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "resource_id", type = IdType.AUTO)
    private Integer resourceId;

    @TableField("user_id")
    private Integer userId;

    @TableField("vm_meta_id")
    private Integer vmMetaId;

    @TableField("disk_meta_id")
    private Integer diskMetaId;

    @TableField("department_id")
    private Integer departmentId;

    @TableField("path_uuid")
    private String pathUuid;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField("release_time")
    private LocalDateTime releaseTime;

    @TableField("is_delete")
    private Integer isDelete;

    @TableField("public_ip")
    private String publicIp;


    @Override
    protected Serializable pkVal() {
        return this.resourceId;
    }

}
