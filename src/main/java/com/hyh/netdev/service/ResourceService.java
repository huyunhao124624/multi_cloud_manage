package com.hyh.netdev.service;

import com.hyh.netdev.dto.ApplyPrivateResourceDto;
import org.springframework.stereotype.Service;

@Service
public interface ResourceService {

    public void applyPrivateResource(ApplyPrivateResourceDto requestDto);


}
