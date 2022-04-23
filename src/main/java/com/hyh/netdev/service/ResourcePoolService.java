package com.hyh.netdev.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyh.netdev.bo.resourcePool.GetAllResourcePoolBo;
import com.hyh.netdev.bo.resourcePool.GetAllResourcePoolTypeBo;
import com.hyh.netdev.dto.AddResourcePoolTypeDto;
import com.hyh.netdev.dto.DeleteResourcePoolDto;
import com.hyh.netdev.dto.UpdateResourcePoolDto;
import com.hyh.netdev.entity.ResourcePool;
import com.hyh.netdev.vo.MPage;
import com.hyh.netdev.vo.PageLimit;
import com.hyh.netdev.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyh
 * @since 2022-04-22
 */
public interface ResourcePoolService extends IService<ResourcePool> {
    public Result<MPage<GetAllResourcePoolBo>> getAllResourcePoolList(PageLimit pageLimit);
    public Result<List<GetAllResourcePoolTypeBo>> getAllResourcePoolTypeList();
    public Result updateResourcePoolType(UpdateResourcePoolDto requestDto);
    public Result addResourcePoolType(AddResourcePoolTypeDto requestDto);
    public Result deleteResourcePool(DeleteResourcePoolDto requestDto);
}
