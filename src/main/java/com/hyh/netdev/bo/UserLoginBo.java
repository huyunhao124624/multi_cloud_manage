package com.hyh.netdev.bo;

import lombok.Data;

/**
 * /api/user/login
 *
 * @author hyh
 */
@Data
public class UserLoginBo {
    private String username;
    private String password;
}
