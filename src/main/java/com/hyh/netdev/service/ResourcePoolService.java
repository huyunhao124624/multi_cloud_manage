package com.hyh.netdev.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyh.netdev.bo.GetAllResourcePoolBo;
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
}
