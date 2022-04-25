package com.hyh.netdev.bo.privateResourceLimit;

import lombok.Data;

@Data
public class GetDepartmentPrivateResourceListBo {
    private Integer userId;
    private String userName;
    private String cpuLimit;
    private String memoryLimit;
    private String diskLimit;
}
