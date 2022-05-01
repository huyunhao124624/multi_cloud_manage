package com.hyh.netdev.service;

import com.hyh.netdev.vo.Result;

import java.util.List;

public interface FeeService {
    public Result<List<Double>> getDepartmentDifferentRoundFee(Long departmentId);

    public Result<List<Integer>> getDepartmentDifferentRoundUsage(Long departmentId);


}
