package com.hyh.netdev.dto;

import lombok.Data;

@Data
public class AddResourcePoolTypeDto {
    private Long departmentId;
    private String resourcePoolTypeCode;
    private Integer cpuLimit;
    private Integer memoryLimit;
    private Integer diskLimit;

}
