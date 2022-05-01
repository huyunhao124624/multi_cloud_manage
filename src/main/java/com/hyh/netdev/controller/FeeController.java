package com.hyh.netdev.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyh.netdev.bo.department.GetFeeAnalyseDepartmentListBo;
import com.hyh.netdev.dao.DepartmentMapper;
import com.hyh.netdev.dao.DepartmentUserMapper;
import com.hyh.netdev.dao.UserRoleMapper;
import com.hyh.netdev.dto.fee.GetDepartmentDifferentRoundUsageDto;
import com.hyh.netdev.dto.resource.GetDepartmentDifferentRoundFeeDto;
import com.hyh.netdev.entity.Department;
import com.hyh.netdev.entity.DepartmentUser;
import com.hyh.netdev.entity.UserRole;
import com.hyh.netdev.enums.RoleEnum;
import com.hyh.netdev.service.FeeService;
import com.hyh.netdev.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",allowCredentials = "true")
public class FeeController {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private DepartmentUserMapper departmentUserMapper;

    @Autowired
    FeeService feeService;

    @PostMapping("/api/fee/getFeeAnalyseDepartmentList")
    public Result<List<GetFeeAnalyseDepartmentListBo>> getFeeAnalyseDepartmentList(UsernamePasswordAuthenticationToken token){
        Integer userId = Integer.parseInt(token.getName());
        UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("user_id", userId));
        Integer roleId = userRole.getRoleId();
        List<GetFeeAnalyseDepartmentListBo> resultList = new ArrayList<>();
        if(RoleEnum.DEVELOP_LEADER.getCode().equals(roleId)){
            DepartmentUser departmentUser = departmentUserMapper.selectOne(new QueryWrapper<DepartmentUser>().eq("user_id", userId));
            Long departmentId = departmentUser.getDepartmentId();
            Department department = departmentMapper.selectOne(new QueryWrapper<Department>().eq("department_id", departmentId));
            GetFeeAnalyseDepartmentListBo resultBo = new GetFeeAnalyseDepartmentListBo();
            resultBo.setDepartmentId(departmentId+"");
            resultBo.setDepartmentName(department.getDepartmentName());
            resultList.add(resultBo);
        } else if(RoleEnum.ADMIN.getCode().equals(roleId) || RoleEnum.DEVELOP_LEADER.getCode().equals(roleId)) {
            List<Department> departmentList = departmentMapper.selectList(new QueryWrapper<Department>());
            for(Department department:departmentList){
                GetFeeAnalyseDepartmentListBo resultBo = new GetFeeAnalyseDepartmentListBo();
                resultBo.setDepartmentId(department.getDepartmentId()+"");
                resultBo.setDepartmentName(department.getDepartmentName()+"");
                resultList.add(resultBo);
            }
        }
        return new Result<>(resultList);
    }

    @PostMapping("/api/fee/getDepartmentDifferentRoundUsage")
    public Result<List<Integer>> getDepartmentDifferentRoundUsaage(@RequestBody GetDepartmentDifferentRoundUsageDto requestDto){
        Long departmentId = requestDto.getDepartmentId();
        return feeService.getDepartmentDifferentRoundUsage(departmentId);
    }

    @PostMapping("/api/fee/getDepartmentDifferentRoundFee")
    public Result<List<Double>> getDepartmentDifferentRoundFee(@RequestBody GetDepartmentDifferentRoundFeeDto requestDto){
        Long departmentId = requestDto.getDepartmentId();
        return feeService.getDepartmentDifferentRoundFee(departmentId);

    }

}
