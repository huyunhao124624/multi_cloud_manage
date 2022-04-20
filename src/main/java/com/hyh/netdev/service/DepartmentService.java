package com.hyh.netdev.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyh.netdev.bo.department.GetAllDepartmentBo;
import com.hyh.netdev.entity.Department;
import com.hyh.netdev.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyh
 * @since 2022-04-21
 */
public interface DepartmentService extends IService<Department> {
    public Result<List<GetAllDepartmentBo>> getAllDepartmentList();
}
