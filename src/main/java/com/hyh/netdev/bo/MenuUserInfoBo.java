package com.hyh.netdev.bo;

import lombok.Data;

import java.util.List;

@Data
public class MenuUserInfoBo {
   private List<String> roles;
   private String avatar;
   private String name;
}
