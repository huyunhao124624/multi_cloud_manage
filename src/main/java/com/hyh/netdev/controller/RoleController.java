package com.hyh.netdev.controller;

import com.hyh.netdev.bo.role.GetAllRoleListBo;
import com.hyh.netdev.service.RoleService;
import com.hyh.netdev.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hyh
 */
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",allowCredentials = "true")
public class RoleController {

    RoleService roleService;

    @PostMapping("/api/role/getAllRoleList")
    public Result<List<GetAllRoleListBo>> getAllRoleList(){
        return roleService.getAllRoleList();
    }

}
