package com.hyh.netdev.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Albumen
 */
@Data
public class UserInformation {
    private Integer userId;
    private Integer role;
    private List<String> permission;
}
