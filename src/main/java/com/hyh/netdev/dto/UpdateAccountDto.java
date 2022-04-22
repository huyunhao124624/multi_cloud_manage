package com.hyh.netdev.dto;

import lombok.Data;

@Data
public class UpdateAccountDto {
    private String account;
    private String password;
    private Integer roleId;
    private String departmentId;
    private String userName;
    private Integer userId;
}
