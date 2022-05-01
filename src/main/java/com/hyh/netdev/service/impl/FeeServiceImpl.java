package com.hyh.netdev.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyh.netdev.dao.ResourceMapper;
import com.hyh.netdev.dao.VmMetaMapper;
import com.hyh.netdev.entity.Resource;
import com.hyh.netdev.service.FeeService;
import com.hyh.netdev.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FeeServiceImpl implements FeeService {

    @Autowired
    VmMetaMapper vmMetaMapper;

    @Autowired
    ResourceMapper resourceMapper;

    private static long DAY_LONG = 24*60*60*1000l;
    private static long WEEK_LONG = 7*24*60*60*1000l;
    private static long HALF_MONTH_LONG = 15*24*60*60*1000l;
    private static long MONTH_LONG = 30*24*60*60*1000l;
    private static long TWO_MONTH_LONG = 2*30*24*60*60*1000l;
    private static long THREE_MONTH_LONG = 3*30*24*60*60*1000l;
    private static long HALF_YEAR_LONG = 6*30*24*60*60*1000l;
    private static long YEAR_LONG = 12*30*24*60*60*1000l;


    @Override
    public Result<List<Double>> getDepartmentDifferentRoundFee(Long departmentId) {
        return null;
    }

    @Override
    public Result<List<Integer>> getDepartmentDifferentRoundUsage(Long departmentId) {
        List<Resource> resourceList = resourceMapper.selectList(new QueryWrapper<Resource>().eq("department_id", departmentId));
        List<Integer> timeRoundResultList = new ArrayList();
        for(int i=0;i<8;i++){
            timeRoundResultList.add(0);
        }
        for (Resource resource : resourceList) {
            Date startTime = resource.getCreateTime();
            Date endTime = resource.getReleaseTime();
            if(endTime == null){
                endTime = new Date();
            }
            long time = endTime.getTime()-startTime.getTime();
            if(time < DAY_LONG){
                timeRoundResultList.set(0,timeRoundResultList.get(0)+1);
            }else if(time < WEEK_LONG){
                timeRoundResultList.set(1,timeRoundResultList.get(1)+1);
            }else if(time < HALF_MONTH_LONG){
                timeRoundResultList.set(2,timeRoundResultList.get(2)+1);
            }else if(time < MONTH_LONG){
                timeRoundResultList.set(3,timeRoundResultList.get(3)+1);
            }else if(time < TWO_MONTH_LONG){
                timeRoundResultList.set(4,timeRoundResultList.get(4)+1);
            }else if(time < THREE_MONTH_LONG){
                timeRoundResultList.set(5,timeRoundResultList.get(5)+1);
            }else if(time < HALF_YEAR_LONG){
                timeRoundResultList.set(6,timeRoundResultList.get(6)+1);
            }else if(time < YEAR_LONG){
                timeRoundResultList.set(7,timeRoundResultList.get(7)+1);
            }
        }

        return new Result<>(timeRoundResultList);
    }


}
