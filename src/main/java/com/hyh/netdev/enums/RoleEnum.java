package com.hyh.netdev.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    DEVELOP_LEADER(1,"开发组组长"),
    DEVELOP_FOLLOWER(2,"开发者组组员"),
    ADMIN(3,"管理员"),
    TESTER(4,"测试人员"),
    OPERATOR(5,"运维人员")
    ;
    Integer code;
    String name;
}
