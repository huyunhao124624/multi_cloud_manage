package com.hyh.netdev.service;

import com.hyh.netdev.bo.privateResourceLimit.GetDepartmentPrivateResourceListBo;
import com.hyh.netdev.vo.MPage;
import com.hyh.netdev.vo.Result;

public interface PrivateResourceLimitService {

    public Result<MPage<GetDepartmentPrivateResourceListBo>> getDepartmentPrivateResourceList(Long departmentId);

}
