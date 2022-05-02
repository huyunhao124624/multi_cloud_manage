package com.hyh.netdev.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyh.netdev.bo.resource.GetInitApplyResourcePageObjectBo;
import com.hyh.netdev.bo.resource.GetResourceListBo;
import com.hyh.netdev.dao.DepartmentUserMapper;
import com.hyh.netdev.dao.UserRoleMapper;
import com.hyh.netdev.dto.ApplyResourceDto;
import com.hyh.netdev.dto.resource.GetResourceListDto;
import com.hyh.netdev.dto.resource.ReleaseResourceDto;
import com.hyh.netdev.entity.DepartmentUser;
import com.hyh.netdev.entity.UserRole;
import com.hyh.netdev.service.ResourceService;
import com.hyh.netdev.vo.MPage;
import com.hyh.netdev.vo.PageLimit;
import com.hyh.netdev.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",allowCredentials = "true")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @Autowired
    private DepartmentUserMapper departmentUserMapper;

    private UserRoleMapper userRoleMapper;


    @PostMapping("/api/resource/applyResource")
    public Result applyResource(@RequestBody ApplyResourceDto requestDto, UsernamePasswordAuthenticationToken token) throws IOException {
        Integer userId = Integer.parseInt(token.getName());
        DepartmentUser departmentUser = departmentUserMapper.selectOne(new QueryWrapper<DepartmentUser>().eq("user_id",userId));
        Long departmentId = departmentUser.getDepartmentId();
        return resourceService.applyResource(requestDto,userId,departmentId);
    }

    @PostMapping("/api/resource/getInitApplyResourcePageObject")
    public Result<GetInitApplyResourcePageObjectBo> getInitApplyResourcePageObject(UsernamePasswordAuthenticationToken token){
        Integer userId = Integer.parseInt(token.getName());
        DepartmentUser departmentUser = departmentUserMapper.selectOne(new QueryWrapper<DepartmentUser>().eq("user_id",userId));
        Long departmentId = departmentUser.getDepartmentId();
        UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("user_id",userId));
        return resourceService.getInitApplyResourcePageObject(userId,departmentId, userRole.getRoleId());
    }

    @PostMapping("/api/resource/getResourceList")
    public Result<MPage<GetResourceListBo>> getResourceList(UsernamePasswordAuthenticationToken token, @RequestBody GetResourceListDto requestDto){
        Integer userId = Integer.parseInt(token.getName());
        DepartmentUser departmentUser = departmentUserMapper.selectOne(new QueryWrapper<DepartmentUser>().eq("user_id",userId));
        Long departmentId = departmentUser.getDepartmentId();
        UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("user_id", userId));
        Integer roleId = userRole.getRoleId();

        PageLimit pageLimit = new PageLimit(requestDto.getPage(),requestDto.getLimit());
        return resourceService.getResourceList(userId,departmentId,roleId,pageLimit);
    }

    @PostMapping("/api/resource/releaseResource")
    public Result releaseResource(@RequestBody ReleaseResourceDto requestDto) throws IOException {
        Long resourceId = requestDto.getResourceId();
        return resourceService.releaseResource(resourceId);
    }


}
