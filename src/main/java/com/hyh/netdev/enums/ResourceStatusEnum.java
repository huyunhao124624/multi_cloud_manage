package com.hyh.netdev.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResourceStatusEnum {

    CREATING("CREATING","创建中"),
    RUNNING("RUNNING","运行中"),
    RELEASED("RELEASED","已经释放了");

    private String code;
    private String name;

}
