package com.hyh.netdev.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceTypeEnum {
    DEPARTMENT_PUBLIC("DEPARTMENT_PUBLIC","部门公有资源"),
    PERSONAL_PRIVATE("PERSONAL_PRIVATE","个人私有资源");
    private String code;
    private String name;
}
