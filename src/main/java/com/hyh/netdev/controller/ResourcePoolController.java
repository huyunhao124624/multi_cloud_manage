package com.hyh.netdev.controller;

import com.hyh.netdev.bo.resourcePool.GetAllResourcePoolBo;
import com.hyh.netdev.bo.resourcePool.GetAllResourcePoolTypeBo;
import com.hyh.netdev.dto.GetAllResourcePoolListDto;
import com.hyh.netdev.dto.UpdateResourcePoolDto;
import com.hyh.netdev.service.ResourcePoolService;
import com.hyh.netdev.vo.MPage;
import com.hyh.netdev.vo.PageLimit;
import com.hyh.netdev.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",allowCredentials = "true")
public class ResourcePoolController {

    @Autowired
    ResourcePoolService resourcePoolService;

    @PostMapping("/api/resourcePool/getAllResourcePoolList")
    public Result<MPage<GetAllResourcePoolBo>> getAllResourcePoolList(@RequestBody GetAllResourcePoolListDto requestDto){
        PageLimit pageLimit = new PageLimit(requestDto.getPage(),requestDto.getLimit());
        return resourcePoolService.getAllResourcePoolList(pageLimit);
    }

    @PostMapping("/api/resourcePool/getAllResourcePoolTypeList")
    public Result<List<GetAllResourcePoolTypeBo>> getAllResourcePoolType(){
        return resourcePoolService.getAllResourcePoolTypeList();
    }

    @PostMapping("/api/resourcePool/updateResourcePool")
    public Result updateResourcePool(@RequestBody UpdateResourcePoolDto requestDto){
        return resourcePoolService.updateResourcePoolType(requestDto);
    }

}
