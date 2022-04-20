package com.hyh.netdev.controller;


import com.hyh.netdev.bo.department.GetAllDepartmentBo;
import com.hyh.netdev.service.DepartmentService;
import com.hyh.netdev.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-04-21
 */
@RestController
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/api/department/getAllDepartmentList")
    public Result<List<GetAllDepartmentBo>> getAllDepartmentList(){
        return departmentService.getAllDepartmentList();
    }
}
