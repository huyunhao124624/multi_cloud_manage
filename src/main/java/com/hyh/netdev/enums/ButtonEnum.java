package com.hyh.netdev.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ButtonEnum {
    EDIT("EDIT","编辑"),
    RELEASE("RELEASE_RESOURCE","释放资源");

    private String code;
    private String buttonName;
}
