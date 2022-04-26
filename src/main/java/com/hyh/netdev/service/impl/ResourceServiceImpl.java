package com.hyh.netdev.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyh.netdev.bo.resource.GetInitApplyResourcePageObjectBo;
import com.hyh.netdev.constant.ResultConstant;
import com.hyh.netdev.dao.*;
import com.hyh.netdev.dto.ApplyResourceDto;
import com.hyh.netdev.entity.*;
import com.hyh.netdev.enums.BooleanEnum;
import com.hyh.netdev.enums.CloudProviderEnum;
import com.hyh.netdev.enums.ResourceStatusEnum;
import com.hyh.netdev.enums.ResourceTypeEnum;
import com.hyh.netdev.service.ResourceService;
import com.hyh.netdev.util.EnumUtils;
import com.hyh.netdev.util.TerraformUtil;
import com.hyh.netdev.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private VmMetaMapper vmMetaMapper;

    @Autowired
    private DiskMetaMapper diskMetaMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ResourcePoolMapper resourcePoolMapper;

    @Autowired
    AmiMetaMapper amiMetaMapper;

    @Value("${terraform.tempPath}")
    private String tfBasePath;

    private static String awsLocalFilePath = "terraform_template/aws/aws_local.tf";
    private static String AWS_FILE_NAME = "aws.tf";



    @Override
    public Result<GetInitApplyResourcePageObjectBo> getInitApplyResourcePageObject(Integer userId, Long departmentId) {
        GetInitApplyResourcePageObjectBo resultBo = new GetInitApplyResourcePageObjectBo();
        ResourcePool resourcePool = resourcePoolMapper.selectOne(new QueryWrapper<ResourcePool>().eq("department_id", departmentId));
        String cloudProvider = resourcePool.getResourcePoolType();
        resultBo.setCloudProviderCode(cloudProvider);
        CloudProviderEnum currentCloudProviderEnum = EnumUtils.getEnumByCode(CloudProviderEnum.class, cloudProvider);
        resultBo.setCloudProviderName(currentCloudProviderEnum.getTypeName());

        List<VmMeta> vmMetaList = vmMetaMapper.selectList(new QueryWrapper<VmMeta>().eq("cloud_provider", cloudProvider));

        List<GetInitApplyResourcePageObjectBo.ResourceSpecsBo> resourceSpecsBoList = new ArrayList<>();
        vmMetaList.forEach((vmMeta)->{
            GetInitApplyResourcePageObjectBo.ResourceSpecsBo resourceSpecsBo = new GetInitApplyResourcePageObjectBo.ResourceSpecsBo();
            resourceSpecsBo.setCpuNum(vmMeta.getCpu());
            resourceSpecsBo.setMemorySize(vmMeta.getMemory());
            resourceSpecsBo.setInstanceType(vmMeta.getInstanceType());
            resourceSpecsBoList.add(resourceSpecsBo);
        });
        resultBo.setResourceSpecsList(resourceSpecsBoList);


        List<GetInitApplyResourcePageObjectBo.AmiOutBo> imageList = new ArrayList<>();
        List<AmiMeta> amiMetaList = amiMetaMapper.selectList(new QueryWrapper<AmiMeta>().eq("cloud_provider", cloudProvider));
        amiMetaList.forEach((amiMeta)->{
            GetInitApplyResourcePageObjectBo.AmiOutBo image = new GetInitApplyResourcePageObjectBo.AmiOutBo();
            image.setAmiName(amiMeta.getAmiName());
            image.setAmiOutId(amiMeta.getAmiOutId());
            imageList.add(image);
        });
        resultBo.setImageList(imageList);

        List<GetInitApplyResourcePageObjectBo.ResourceTypeBo> resourceTypeBoList = new ArrayList<>();
        List<ResourceTypeEnum> enumList = org.apache.commons.lang3.EnumUtils.getEnumList(ResourceTypeEnum.class);
        for (ResourceTypeEnum resourceTypeEnum : enumList) {
            GetInitApplyResourcePageObjectBo.ResourceTypeBo resourceTypeBo = new GetInitApplyResourcePageObjectBo.ResourceTypeBo();
            resourceTypeBo.setResourceTypeName(resourceTypeEnum.getName());
            resourceTypeBo.setResourceTypeCode(resourceTypeEnum.getCode());
            resourceTypeBoList.add(resourceTypeBo);
        }

        resultBo.setResourceTypeList(resourceTypeBoList);

        return new Result<>(resultBo);
    }


    @Override
    public Result applyPrivateResource(ApplyResourceDto requestDto, Integer userId, Long departmentId) throws IOException {
        Integer cpuNum = requestDto.getCpuNum();
        Integer memorySize = requestDto.getMemorySize();
        Integer diskSize = requestDto.getDiskSize();
        VmMeta vmMeta = vmMetaMapper.selectOne(new QueryWrapper<VmMeta>()
                .eq("cpu", cpuNum)
                .eq("memory", memorySize)
                .eq("cloud_provider", requestDto.getCloudProviderCode()));
        DiskMeta diskMeta = diskMetaMapper.selectOne(new QueryWrapper<DiskMeta>().eq("cloud_provider",requestDto.getCloudProviderCode()));





        String instanceType = vmMeta.getInstanceType();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");

        Resource resource = new Resource();
        resource.setPathUuid(uuid);
        resource.setUserId(userId);
        resource.setDepartmentId(departmentId);
        resource.setCloudProvider(requestDto.getCloudProviderCode());
        resource.setIsDelete(BooleanEnum.NO.getCode());

        resource.setVmMetaId(vmMeta.getVmMetaId());
        resource.setDiskMetaId(diskMeta.getDiskMetaId());

        resource.setResourceStatus(ResourceStatusEnum.CREATING.getCode());

        this.handleInstanceType(uuid,instanceType,requestDto.getDiskSize(),requestDto.getAmiOutId(),resource,requestDto.getCloudProviderCode());

        return ResultConstant.SUCCESS;


    }



    private void handleInstanceType(String uuid,String instanceType,Integer diskSize,String amiOutId,Resource resource,String cloudProvider) throws IOException {

        if(StringUtils.equals(CloudProviderEnum.AWS.getCode(),cloudProvider)){
            this.handleAWSApply(uuid,instanceType,diskSize,amiOutId,resource);
        }
    }

    /**
     *
     * @param uuid
     * @param instanceType
     * @param diskSize
     * @param amiOutId
     * @return 返回
     * @throws IOException
     */
    private void handleAWSApply(String uuid, String instanceType, Integer diskSize, String amiOutId,Resource resource) throws IOException {

        File file = ResourceUtils.getFile("classpath:"+awsLocalFilePath);
        String templateString = TerraformUtil.txt2String(file);

        Map<String,String> replaceMap = new HashMap<>();
        replaceMap.put("awsEipName","aws_eip_name_"+uuid);
        replaceMap.put("awsInstanceName","aws_instance_name_"+uuid);
        replaceMap.put("awsEipAssociationName","aws_eip_association_name_"+uuid);
        replaceMap.put("amiOutId",amiOutId);
        replaceMap.put("instanceType",instanceType);
        replaceMap.put("diskName","aws_disk_"+uuid);
        replaceMap.put("diskSize",diskSize+"");

        StringSubstitutor stringSubstitutor = new StringSubstitutor(replaceMap);
        String formatTemplateString = stringSubstitutor.replace(templateString);

        File aimDir = new File(tfBasePath+"\\"+uuid);
        aimDir.mkdir();
        File f = new File(aimDir.getAbsoluteFile() + "\\"+AWS_FILE_NAME);
        f.createNewFile();
        if(f.exists()){
            FileWriter fw = new FileWriter(f);
            fw.append(formatTemplateString);
            fw.close();
        }

        boolean ifInitSuccess = TerraformUtil.terraformInit(aimDir);
        if(!ifInitSuccess){
            log.error("terraform init {} failed",uuid);
            return;
        }

        boolean ifApplySuccess = TerraformUtil.terraformApply(aimDir);
        if(!ifApplySuccess){
            log.error("terraform apply {} failed",uuid);
            return;
        }


        JSONObject applyResultJSON = TerraformUtil.terraformShow(aimDir);
        JSONObject values = applyResultJSON.getJSONObject("values");
        JSONObject outputs = values.getJSONObject("outputs");
        JSONObject awsPublicIpObj = outputs.getJSONObject("aws_public_ip");
        String publicIp = awsPublicIpObj.getString("value");
        resource.setPublicIp(publicIp);

        Date currentDate = new Date();
        resource.setCreateTime(currentDate);

        resourceMapper.insert(resource);

    }
}
