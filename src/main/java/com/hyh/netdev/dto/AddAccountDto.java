package com.hyh.netdev.dto;

import lombok.Data;

@Data
public class AddAccountDto {
    private String account;
    private String password;
    private Integer roleId;
    private Long departmentId;
    private String userName;
    private Integer userId;
}