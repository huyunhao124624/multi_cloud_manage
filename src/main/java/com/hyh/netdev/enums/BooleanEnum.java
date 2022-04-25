package com.hyh.netdev.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BooleanEnum {
    YES("1"),
    NO("0");

    private String code;

}
