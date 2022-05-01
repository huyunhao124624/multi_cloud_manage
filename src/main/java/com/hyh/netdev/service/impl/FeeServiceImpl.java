package com.hyh.netdev.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyh.netdev.dao.DiskMetaMapper;
import com.hyh.netdev.dao.ResourceMapper;
import com.hyh.netdev.dao.VmMetaMapper;
import com.hyh.netdev.entity.DiskMeta;
import com.hyh.netdev.entity.Resource;
import com.hyh.netdev.entity.VmMeta;
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

    @Autowired
    DiskMetaMapper diskMetaMapper;



    private static long HOUR_LONG = 60*60*1000l;
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
        List<Resource> resourceList = resourceMapper.selectList(new QueryWrapper<Resource>().eq("department_id", departmentId));
        List<Double> timeRoundResultList = new ArrayList();

        for(int i=0;i<8;i++){
            timeRoundResultList.add(0d);
        }



        for (Resource resource : resourceList) {
            Date startTime = resource.getCreateTime();
            Date endTime = resource.getReleaseTime();
            Long resourceId = resource.getResourceId();
            Long vmMetaId = resource.getVmMetaId();
            Long diskVmId = resource.getDiskMetaId();
            Integer diskSize = resource.getDiskSize();

            VmMeta vmMeta = vmMetaMapper.selectOne(new QueryWrapper<VmMeta>().eq("vm_meta_id", vmMetaId));
            DiskMeta diskMeta = diskMetaMapper.selectOne(new QueryWrapper<DiskMeta>().eq("disk_meta_id",diskVmId));

            if(endTime == null){
                endTime = new Date();
            }
            long minusValue = endTime.getTime()-startTime.getTime();

            Double hourDiskPrice = diskMeta.getGbpricePerHour() * (resource.getDiskSize()) * (Math.floor((double)minusValue/(double)HOUR_LONG)+1);

            Double hourRoundVmPrice =  vmMeta.getHourPricing() * (Math.floor(((double)minusValue/HOUR_LONG))+1);
            timeRoundResultList.set(0,timeRoundResultList.get(0)+hourDiskPrice+hourRoundVmPrice);

            Double weekDiskPrice = diskMeta.getGbpricePerHour() * (resource.getDiskSize()) * (7*24d);
            Double weekRoundVmPrice = vmMeta.getWeekPricing() * (Math.floor((double)minusValue/WEEK_LONG)+1);
            timeRoundResultList.set(1,timeRoundResultList.get(1) + weekDiskPrice+weekRoundVmPrice);

            Double halfMonthDiskPrice = diskMeta.getGbpricePerHour() * (resource.getDiskSize()) * (15*24d);
            Double halfMonthRoundVmPrice = vmMeta.getHalfMonthPricing() * (Math.floor((double)minusValue/HALF_MONTH_LONG)+1);
            timeRoundResultList.set(2,timeRoundResultList.get(2)+halfMonthDiskPrice+halfMonthRoundVmPrice);

            Double monthDiskPrice = diskMeta.getGbpricePerHour() * (resource.getDiskSize()) * (30*24d);
            Double monthRoundVmPrice = vmMeta.getMonthPricing() * (Math.floor((double)minusValue/MONTH_LONG)+1);
            timeRoundResultList.set(3,timeRoundResultList.get(3)+monthDiskPrice+monthRoundVmPrice);

            Double twoMonthDiskPrice = diskMeta.getGbpricePerHour() * (resource.getDiskSize()) * (60*24d);
            Double twoMonthRoundVmPrice = vmMeta.getTwoMonthPricing() * (Math.floor((double)minusValue/TWO_MONTH_LONG)+1);
            timeRoundResultList.set(4,timeRoundResultList.get(4)+twoMonthRoundVmPrice+twoMonthDiskPrice);

            Double threeMonthDiskPrice = diskMeta.getGbpricePerHour() * (resource.getDiskSize()) * (90*24d);
            Double threeMonthRoundVmPrice = vmMeta.getThreeMonthPricing() * (Math.floor((double)minusValue/THREE_MONTH_LONG)+1);
            timeRoundResultList.set(5,timeRoundResultList.get(5)+threeMonthRoundVmPrice+threeMonthDiskPrice);

            Double halfYearDiskPrice = diskMeta.getGbpricePerHour() * (resource.getDiskSize()) * (180*24d);
            Double halfYearRoundVmPrice = vmMeta.getHalfYearPricing() * (Math.floor((double)minusValue/HALF_YEAR_LONG)+1);
            timeRoundResultList.set(6,timeRoundResultList.get(6)+halfYearRoundVmPrice+halfYearDiskPrice);

            Double yearDiskPrice = diskMeta.getGbpricePerHour() * (resource.getDiskSize()) * (360*24d);
            Double yearRoundVmPrice = vmMeta.getYearPricing() * (Math.floor((double)minusValue/YEAR_LONG)+1);
            timeRoundResultList.set(7,timeRoundResultList.get(7)+yearRoundVmPrice+yearDiskPrice);


        }

        return new Result<>(timeRoundResultList);
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
