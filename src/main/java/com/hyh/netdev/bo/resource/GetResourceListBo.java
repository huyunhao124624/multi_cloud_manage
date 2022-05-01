package com.hyh.netdev.bo.resource;

import lombok.Data;

import java.util.List;

@Data
public class GetResourceListBo {
    private Long resourceId;
    private String resourceTypeCode;
    private String resourceTypeName;
    private Integer cpuNum;
    private Integer memorySize;
    private Integer diskSize;
    private String publicIp;
    private String resourceStatusCode;
    private String resourceStatusName;

    private String loginName;
    private String password;

    List<String> buttonList;
}
