package com.hyh.netdev.service;

import com.hyh.netdev.dto.ApplyPrivateResourceDto;

import com.hyh.netdev.vo.Result;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface ResourceService {

    public Result applyPrivateResource(ApplyPrivateResourceDto requestDto, Integer userId, Long departmentId) throws IOException;


}
