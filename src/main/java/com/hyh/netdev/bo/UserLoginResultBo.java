package com.hyh.netdev.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * /api/user/login
 * /api/user/refresh
 *
 * @author hyh
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginResultBo {
    private String token;
    private Integer role;
}
