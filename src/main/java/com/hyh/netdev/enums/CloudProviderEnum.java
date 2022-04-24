package com.hyh.netdev.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CloudProviderEnum {

    ALICLOUD("aliCloud","阿里云"),
    HUAWEICLOUD("huaweiCloud","华为云"),
    AWS("awsCloud","亚马逊云");

    private String code;
    private String typeName;

}
