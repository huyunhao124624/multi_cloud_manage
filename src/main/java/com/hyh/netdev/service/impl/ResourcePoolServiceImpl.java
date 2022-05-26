package com.hyh.netdev.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyh.netdev.bo.resourcePool.GetAllResourcePoolBo;
import com.hyh.netdev.bo.resourcePool.GetAllResourcePoolTypeBo;
import com.hyh.netdev.constant.ResultConstant;
import com.hyh.netdev.dao.DepartmentMapper;
import com.hyh.netdev.dao.ResourcePoolMapper;
import com.hyh.netdev.dto.AddResourcePoolTypeDto;
import com.hyh.netdev.dto.DeleteResourcePoolDto;
import com.hyh.netdev.dto.UpdateResourcePoolDto;
import com.hyh.netdev.entity.Department;
import com.hyh.netdev.entity.ResourcePool;
import com.hyh.netdev.enums.ResourcePoolTypeEnum;
import com.hyh.netdev.service.ResourcePoolService;
import com.hyh.netdev.util.EnumUtils;
import com.hyh.netdev.vo.MPage;
import com.hyh.netdev.vo.PageLimit;
import com.hyh.netdev.vo.Result;
import org.apache.commons.collections4.Get;
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

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public Result<MPage<GetAllResourcePoolBo>> getAllResourcePoolList(PageLimit pageLimit) {
        Page page = new Page(pageLimit.getPage(),pageLimit.getLimit());
        IPage<ResourcePool> iPage = resourcePoolMapper.selectPage(page, new QueryWrapper<ResourcePool>());
        List<ResourcePool> resourcePoolList = iPage.getRecords();

        List<GetAllResourcePoolBo> resultList = new ArrayList<>();

        Integer count = resourcePoolMapper.selectCount(new QueryWrapper<>());

        resourcePoolList.forEach((resourcePool)->{

            Long departmentId = resourcePool.getDepartmentId();
            Department department = departmentMapper.selectById(departmentId);
            String departmentName = department.getDepartmentName();


            GetAllResourcePoolBo getAllResourcePoolBo = new GetAllResourcePoolBo();
            getAllResourcePoolBo.setResourcePoolId(resourcePool.getResourcePoolId()+"");
            getAllResourcePoolBo.setDepartmentId(departmentId+"");
            getAllResourcePoolBo.setDepartmentName(departmentName);
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

    @Override
    public Result<List<GetAllResourcePoolTypeBo>> getAllResourcePoolTypeList() {
        List<GetAllResourcePoolTypeBo> resultList = new ArrayList<>();
        for(ResourcePoolTypeEnum iEnum:ResourcePoolTypeEnum.values()){
            GetAllResourcePoolTypeBo getAllResourcePoolTypeBo = new GetAllResourcePoolTypeBo();
            getAllResourcePoolTypeBo.setResourcePoolTypeCode(iEnum.getCode());
            getAllResourcePoolTypeBo.setResourcePoolTypeName(iEnum.getTypeName());
            resultList.add(getAllResourcePoolTypeBo);
        }
        return new Result(resultList);
    }

    @Override
    public Result updateResourcePoolType(UpdateResourcePoolDto requestDto) {
        ResourcePool resourcePool = new ResourcePool();
        resourcePool.setCpuLimit(requestDto.getCpuLimit());
        resourcePool.setMemoryLimit(requestDto.getMemoryLimit());
        resourcePool.setDiskLimit(requestDto.getDiskLimit());
        resourcePool.setResourcePoolId(resourcePool.getResourcePoolId());
        resourcePoolMapper.updateById(resourcePool);
        return ResultConstant.SUCCESS;
    }

    @Override
    public Result addResourcePoolType(AddResourcePoolTypeDto requestDto) {
        ResourcePool resourcePool = new ResourcePool();
        resourcePool.setResourcePoolType(resourcePool.getResourcePoolType());
        Long departmentId = resourcePool.getDepartmentId();
        ResourcePool resExist = resourcePoolMapper.selectOne(new QueryWrapper<ResourcePool>().eq("department_id", departmentId));
        if(resExist != null){
            return ResultConstant.DEPARTMENT_EXIST_RESOURCE_POOL;
        }
        resourcePool.setResourcePoolType(requestDto.getResourcePoolTypeCode());
        resourcePool.setCpuLimit(requestDto.getCpuLimit());
        resourcePool.setMemoryLimit(requestDto.getMemoryLimit());
        resourcePool.setDiskLimit(requestDto.getDiskLimit());
        resourcePool.setDepartmentId(requestDto.getDepartmentId());
        resourcePool.setCpuUsed(0);
        resourcePool.setMemoryUsed(0);
        resourcePool.setDiskUsed(0);
        resourcePoolMapper.insert(resourcePool);

        return ResultConstant.SUCCESS;
    }

    @Override
    public Result deleteResourcePool(DeleteResourcePoolDto requestDto) {
        resourcePoolMapper.deleteById(requestDto.getResourcePoolId());
        return ResultConstant.SUCCESS;
    }
}
