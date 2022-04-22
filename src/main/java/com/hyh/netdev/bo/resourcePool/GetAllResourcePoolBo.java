package com.hyh.netdev.bo.resourcePool;

import lombok.Data;

@Data
public class GetAllResourcePoolBo {
    private String resourcePoolId;
    private Integer cpuLimit;
    private Integer memoryLimit;
    private Integer diskLimit;
    private Integer cpuUsed;
    private Integer memoryUsed;
    private Integer diskUsed;
    private String departmentId;
    private String departmentName;
    private String resourcePoolTypeCode;
    private String resourcePoolTypeName;
}
