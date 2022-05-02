package com.hyh.netdev.dto;

import lombok.Data;

@Data
public class ApplyResourceDto {
    private String amiOutId;
    private String cloudProviderCode;
    private String resourceTypeCode;
    private String resourceType;
    private Integer cpuNum;
    private Integer memorySize;
    private String instanceType;
    private Integer diskSize;
    private String password;
}
