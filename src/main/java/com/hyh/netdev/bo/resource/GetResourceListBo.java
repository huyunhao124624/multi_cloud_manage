package com.hyh.netdev.bo.resource;

import lombok.Data;

@Data
public class GetResourceListBo {
    private Long resourceId;
    private String resourceTypeCode;
    private String resourceTypeName;
    private String cpuNum;
    private String memorySize;
    private String diskSize;
    private String publicIp;
}
