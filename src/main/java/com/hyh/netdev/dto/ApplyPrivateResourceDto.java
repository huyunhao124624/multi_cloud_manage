package com.hyh.netdev.dto;

import lombok.Data;

@Data
public class ApplyPrivateResourceDto {
    private String amiOutId;
    private String cloudProviderCode;
    private Integer cpuNum;
    private Integer memorySize;
    private Integer diskSize;
}
