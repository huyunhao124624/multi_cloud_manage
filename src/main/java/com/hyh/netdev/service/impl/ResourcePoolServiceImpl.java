package com.hyh.netdev.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyh.netdev.bo.GetAllResourcePoolBo;
import com.hyh.netdev.dao.ResourcePoolMapper;
import com.hyh.netdev.entity.ResourcePool;
import com.hyh.netdev.enums.ResourcePoolTypeEnum;
import com.hyh.netdev.service.ResourcePoolService;
import com.hyh.netdev.util.EnumUtils;
import com.hyh.netdev.vo.MPage;
import com.hyh.netdev.vo.PageLimit;
import com.hyh.netdev.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-04-22
 */
@Service
public class ResourcePoolServiceImpl extends ServiceImpl<ResourcePoolMapper, ResourcePool> implements ResourcePoolService {

    @Autowired
    ResourcePoolMapper resourcePoolMapper;

    @Override
    public Result<MPage<GetAllResourcePoolBo>> getAllResourcePoolList(PageLimit pageLimit) {
        Page page = new Page(pageLimit.getPage(),pageLimit.getLimit());
        IPage<ResourcePool> iPage = resourcePoolMapper.selectPage(page, new QueryWrapper<ResourcePool>());
        List<ResourcePool> resourcePoolList = iPage.getRecords();

        List<GetAllResourcePoolBo> resultList = new ArrayList<>();

        Integer count = resourcePoolMapper.selectCount(new QueryWrapper<>());

        resourcePoolList.forEach((resourcePool)->{
            GetAllResourcePoolBo getAllResourcePoolBo = new GetAllResourcePoolBo();
            getAllResourcePoolBo.setResourcePoolId(resourcePool.getResourcePoolId()+"");
            getAllResourcePoolBo.setDepartmentId(resourcePool.getDepartmentId()+"");
            getAllResourcePoolBo.setCpuLimit(resourcePool.getCpuLimit());
            getAllResourcePoolBo.setCpuUsed(resourcePool.getCpuUsed());
            getAllResourcePoolBo.setMemoryLimit(resourcePool.getMemoryLimit());
            getAllResourcePoolBo.setMemoryUsed(resourcePool.getMemoryUsed());
            getAllResourcePoolBo.setDiskLimit(resourcePool.getDiskLimit());
            getAllResourcePoolBo.setDiskUsed(resourcePool.getDiskUsed());

            ResourcePoolTypeEnum resourcePoolTypeEnum = EnumUtils.getEnumByCode(ResourcePoolTypeEnum.class,resourcePool.getResourcePoolType());

            getAllResourcePoolBo.setResourcePoolTypeCode(resourcePoolTypeEnum.getCode());
            getAllResourcePoolBo.setResourcePoolTypeName(resourcePoolTypeEnum.getTypeName());

            resultList.add(getAllResourcePoolBo);
        });

        MPage<GetAllResourcePoolBo> getAllResourcePoolBoMPage = new MPage<>(count, resultList);

        return new Result(getAllResourcePoolBoMPage);
    }
}
