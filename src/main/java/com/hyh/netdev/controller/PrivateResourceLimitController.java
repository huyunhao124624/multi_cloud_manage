package com.hyh.netdev.controller;


import com.hyh.netdev.bo.privateResourceLimit.GetDepartmentPrivateResourceListBo;
import com.hyh.netdev.vo.MPage;
import com.hyh.netdev.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",allowCredentials = "true")
public class PrivateResourceLimitController {


//    public Result<MPage<GetDepartmentPrivateResourceListBo>> getDepartmentPrivateResourceList(UsernamePasswordAuthenticationToken token){
//
//    }
}
