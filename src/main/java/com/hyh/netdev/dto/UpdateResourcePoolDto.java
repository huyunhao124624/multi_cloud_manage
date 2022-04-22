package com.hyh.netdev.dto;

import lombok.Data;

@Data
public class UpdateResourcePoolDto {
    private Long resourcePoolId;
    private Integer cpuLimit;
    private Integer memoryLimit;
    private Integer diskLimit;
}
