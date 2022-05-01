package com.hyh.netdev.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyh.netdev.bo.department.GetFeeAnalyseDepartmentListBo;
import com.hyh.netdev.bo.privateResourceLimit.GetDepartmentPrivateResourceListBo;
import com.hyh.netdev.dao.DepartmentMapper;
import com.hyh.netdev.dao.DepartmentUserMapper;
import com.hyh.netdev.dao.UserRoleMapper;
import com.hyh.netdev.entity.Department;
import com.hyh.netdev.entity.DepartmentUser;
import com.hyh.netdev.entity.UserRole;
import com.hyh.netdev.enums.RoleEnum;
import com.hyh.netdev.vo.MPage;
import com.hyh.netdev.vo.Result;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",allowCredentials = "true")
public class PrivateResourceLimitController {



//    public Result<MPage<GetDepartmentPrivateResourceListBo>> getDepartmentPrivateResourceList(UsernamePasswordAuthenticationToken token){
//
//    }
}
