package com.hyh.netdev.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyh.netdev.bo.privateResourceLimit.GetDepartmentPrivateResourceListBo;
import com.hyh.netdev.dao.DepartmentMapper;
import com.hyh.netdev.dao.DepartmentUserMapper;
import com.hyh.netdev.dao.PrivateResourceLimitMapper;
import com.hyh.netdev.dao.UserMapper;
import com.hyh.netdev.entity.DepartmentUser;
import com.hyh.netdev.entity.User;
import com.hyh.netdev.service.PrivateResourceLimitService;
import com.hyh.netdev.vo.MPage;
import com.hyh.netdev.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrivateResourceLimitServiceImpl implements PrivateResourceLimitService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private DepartmentUserMapper departmentUserMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<MPage<GetDepartmentPrivateResourceListBo>> getDepartmentPrivateResourceList(Long departmentId) {

        List<DepartmentUser> departmentUserList = departmentUserMapper.selectList(new QueryWrapper<DepartmentUser>().eq("department_id", departmentId));
        List<GetDepartmentPrivateResourceListBo> resultList = new ArrayList<>();

        List<Integer> userIdList = new ArrayList<>();

        departmentUserList.forEach((departmentUser)->{
            userIdList.add(departmentUser.getUserId());
        });

        List<User> userList = userMapper.selectBatchIds(userIdList);

        userList.forEach((user)->{
            GetDepartmentPrivateResourceListBo getDepartmentPrivateResourceListBo = new GetDepartmentPrivateResourceListBo();
//            getDepartmentPrivateResourceListBo.set
        });


        return null;
    }
}
