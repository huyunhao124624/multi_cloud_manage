package com.hyh.netdev.service;

import com.hyh.netdev.bo.resource.GetInitApplyResourcePageObjectBo;
import com.hyh.netdev.dto.ApplyResourceDto;

import com.hyh.netdev.vo.Result;

import java.io.IOException;

public interface ResourceService {

    public Result applyPrivateResource(ApplyResourceDto requestDto, Integer userId, Long departmentId) throws IOException;

    public Result<GetInitApplyResourcePageObjectBo> getInitApplyResourcePageObject(Integer userId,Long departmentId);


}
