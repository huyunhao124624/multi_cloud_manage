package com.hyh.netdev.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyh.netdev.bo.department.GetAllDepartmentBo;
import com.hyh.netdev.entity.Department;
import com.hyh.netdev.dao.DepartmentMapper;
import com.hyh.netdev.service.DepartmentService;
import com.hyh.netdev.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fish
 * @since 2022-04-21
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public Result<List<GetAllDepartmentBo>> getAllDepartmentList() {
        List<Department> departmentList = departmentMapper.selectList(new QueryWrapper<>());
        List<GetAllDepartmentBo> resultList = new ArrayList<>();
        departmentList.forEach(department -> {
            GetAllDepartmentBo getAllDepartmentBo = new GetAllDepartmentBo();
            getAllDepartmentBo.setDepartmentId(department.getDepartmentId()+"");
            getAllDepartmentBo.setDepartmentName(department.getDepartmentName());
            resultList.add(getAllDepartmentBo);
        });

        return new Result<>(resultList);
    }
}
