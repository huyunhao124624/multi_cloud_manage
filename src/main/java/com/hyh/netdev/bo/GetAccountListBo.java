package com.hyh.netdev.bo;

import lombok.Data;

@Data
public class GetAccountListBo {
    private String userId;
    private String userName;
    private String account;
    private String departmentId;
    private String departmentName;
    private String roleId;
    private String roleName;
}
