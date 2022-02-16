package com.hyh.netdev.bo;

import lombok.Data;

/**
 * /api/user/password/admin
 *
 * @author hyh
 */
@Data
public class PasswordUpdateBo {
    private String oldPassword;
    private String newPassword;
}
