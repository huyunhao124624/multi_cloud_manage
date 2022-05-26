package com.hyh.netdev.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyh.netdev.bo.resource.GetInitApplyResourcePageObjectBo;
import com.hyh.netdev.bo.resource.GetResourceListBo;
import com.hyh.netdev.constant.ResultConstant;
import com.hyh.netdev.dao.*;
import com.hyh.netdev.dto.ApplyResourceDto;
import com.hyh.netdev.entity.*;
import com.hyh.netdev.enums.*;
import com.hyh.netdev.service.ResourceService;
import com.hyh.netdev.util.EnumUtils;
import com.hyh.netdev.util.TerraformUtil;
import com.hyh.netdev.vo.MPage;
import com.hyh.netdev.vo.PageLimit;
import com.hyh.netdev.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
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
    private static String hwFilePath = "terraform_template/hw/hw_cloud.tf";
    private static String AWS_FILE_NAME = "aws.tf";
    private static String HW_FILE_NAME = "hw_cloud.tf";



    @Override
    public Result<GetInitApplyResourcePageObjectBo> getInitApplyResourcePageObject(Integer userId, Long departmentId, Integer roleId) {
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
            if(resourceTypeEnum.getCode().equals(ResourceTypeEnum.DEPARTMENT_PUBLIC.getCode()) && RoleEnum.DEVELOP_FOLLOWER.getCode().equals(roleId)){
                continue;
            }
            GetInitApplyResourcePageObjectBo.ResourceTypeBo resourceTypeBo = new GetInitApplyResourcePageObjectBo.ResourceTypeBo();
            resourceTypeBo.setResourceTypeName(resourceTypeEnum.getName());
            resourceTypeBo.setResourceTypeCode(resourceTypeEnum.getCode());
            resourceTypeBoList.add(resourceTypeBo);
        }

        resultBo.setResourceTypeList(resourceTypeBoList);

        return new Result<>(resultBo);
    }

    @Override
    public Result<MPage<GetResourceListBo>> getResourceList(Integer userId, Long departmentId, Integer roleId, PageLimit pageLimit) {
        IPage page = new Page<Resource>();
        page.setPages(pageLimit.getPage());
        page.setSize(pageLimit.getLimit());
        IPage resourcePageList = null;
        Integer count = 0;

        if(RoleEnum.DEVELOP_LEADER.getCode().equals(roleId)) {
            resourcePageList = resourceMapper.selectPage(page, new QueryWrapper<Resource>().eq("department_id",departmentId));
            count = resourceMapper.selectCount(new QueryWrapper<Resource>().eq("department_id",departmentId));
        }else if(RoleEnum.DEVELOP_FOLLOWER.getCode().equals(roleId)){
            resourcePageList = resourceMapper.selectPage(page,new QueryWrapper<Resource>().eq("user_id",userId)
                    .or(Wrapper->Wrapper.eq("department_id",departmentId).eq("resource_type",ResourceTypeEnum.DEPARTMENT_PUBLIC.getCode())));
            count = resourceMapper.selectCount(new QueryWrapper<Resource>().eq("user_id",userId)
                    .and(Wrapper->Wrapper.eq("department_id",departmentId).eq("resource_type",ResourceTypeEnum.DEPARTMENT_PUBLIC.getCode())));
        }else{
            resourcePageList = resourceMapper.selectPage(page, new QueryWrapper<Resource>().eq("department_id",departmentId).eq("resource_type",ResourceTypeEnum.DEPARTMENT_PUBLIC.getCode()));
            count = resourceMapper.selectCount(new QueryWrapper<Resource>().eq("department_id",departmentId).eq("resource_type",ResourceTypeEnum.DEPARTMENT_PUBLIC.getCode()));
        }
        List<Resource> recordList = resourcePageList.getRecords();

        List<GetResourceListBo> resultList = new ArrayList<>();
        for (Resource resource : recordList) {

            boolean releaseButton = true;

            GetResourceListBo getResourceListBo = new GetResourceListBo();
            getResourceListBo.setResourceId(resource.getResourceId());


            getResourceListBo.setResourceTypeCode(EnumUtils.getEnumByCode(ResourceTypeEnum.class, resource.getResourceType()).getCode());
            getResourceListBo.setResourceTypeName(EnumUtils.getEnumByCode(ResourceTypeEnum.class, resource.getResourceType()).getName());

            VmMeta vmMeta = vmMetaMapper.selectById(resource.getVmMetaId());
            getResourceListBo.setCpuNum(vmMeta.getCpu());
            getResourceListBo.setMemorySize(vmMeta.getMemory());
            getResourceListBo.setDiskSize(resource.getDiskSize());

            getResourceListBo.setLoginName(resource.getLoginName());
            getResourceListBo.setPassword(resource.getPassword());

            getResourceListBo.setPublicIp(resource.getPublicIp());

            String resourceStatus = resource.getResourceStatus();


            if (!resource.getDepartmentId().equals(departmentId) && !resource.getUserId().equals(userId)) {
                releaseButton = false;
            }

            if (!StringUtils.equals(resourceStatus, ResourceStatusEnum.RUNNING.getCode())) {
                releaseButton = false;
            }

            if(StringUtils.equals(ResourceTypeEnum.DEPARTMENT_PUBLIC.getCode(), resource.getResourceType()) && !RoleEnum.DEVELOP_LEADER.getCode().equals(roleId)){
                releaseButton = false;
            }

            getResourceListBo.setResourceStatusCode(resource.getResourceStatus());
            getResourceListBo.setResourceStatusName(EnumUtils.getEnumByCode(ResourceStatusEnum.class,resource.getResourceStatus()).getName());

            List<String> buttonList = new ArrayList<>();
            if (releaseButton) {
                buttonList.add(ButtonEnum.RELEASE.getCode());
            }
            getResourceListBo.setButtonList(buttonList);
            resultList.add(getResourceListBo);
        }

        MPage<GetResourceListBo> mpage = new MPage<GetResourceListBo>(count,resultList);
        return new Result<>(mpage);
    }

    @Override
    public Result releaseResource(Long resourceId) throws IOException {
        Resource resource = resourceMapper.selectById(resourceId);
        String pathUuid = resource.getPathUuid();
        File destroyPathFile = new File(tfBasePath+"\\"+ pathUuid);

        TerraformUtil.terraformDestroy(destroyPathFile);
        resource.setResourceStatus(ResourceStatusEnum.RELEASED.getCode());
        resourceMapper.updateById(resource);
        return ResultConstant.SUCCESS;
    }


    @Override
    public Result applyResource(ApplyResourceDto requestDto, Integer userId, Long departmentId) throws IOException {
        Integer cpuNum = requestDto.getCpuNum();
        Integer memorySize = requestDto.getMemorySize();
        String instanceType = requestDto.getInstanceType();
        Integer diskSize = requestDto.getDiskSize();
        VmMeta vmMeta = vmMetaMapper.selectOne(new QueryWrapper<VmMeta>()
                        .eq("instance_type",instanceType)
                        .eq("cloud_provider", requestDto.getCloudProviderCode()));
        DiskMeta diskMeta = diskMetaMapper.selectOne(new QueryWrapper<DiskMeta>().eq("cloud_provider",requestDto.getCloudProviderCode()));


        instanceType = vmMeta.getInstanceType();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");

        Resource resource = new Resource();
        resource.setPathUuid(uuid);
        resource.setUserId(userId);
        resource.setDepartmentId(departmentId);
        resource.setCloudProvider(requestDto.getCloudProviderCode());
        resource.setIsDelete(BooleanEnum.NO.getCode());

        resource.setResourceType(requestDto.getResourceTypeCode());

        resource.setVmMetaId(vmMeta.getVmMetaId());
        resource.setDiskMetaId(diskMeta.getDiskMetaId());

        resource.setResourceStatus(ResourceStatusEnum.CREATING.getCode());

        resource.setDiskSize(diskSize);


        this.handleInstanceType(uuid,instanceType,requestDto.getDiskSize(),requestDto.getAmiOutId(),resource,requestDto.getCloudProviderCode());

        return ResultConstant.SUCCESS;


    }



    private void handleInstanceType(String uuid,String instanceType,Integer diskSize,String amiOutId,Resource resource,String cloudProvider) throws IOException {

        if(StringUtils.equals(CloudProviderEnum.AWS.getCode(),cloudProvider)){
            this.handleAWSApply(uuid,instanceType,diskSize,amiOutId,resource);
        }else if(StringUtils.equals(CloudProviderEnum.HUAWEICLOUD.getCode(),cloudProvider)){
            this.handleHWApply(uuid,instanceType,diskSize,amiOutId,resource);
        }
    }

    private void handleHWApply(String uuid,String instanceType,Integer diskSize,String amiOutId,Resource resource) throws IOException {
        String loginName = "root";
        String password = "PW"+System.currentTimeMillis()+"pw";
        resource.setLoginName(loginName);
        resource.setPassword(password);

        File file = ResourceUtils.getFile("classpath:"+hwFilePath);
        String templateString = TerraformUtil.txt2String(file);

        Map<String,String> replaceMap = new HashMap<>();
        replaceMap.put("amiOutId",amiOutId);
        replaceMap.put("instanceType",instanceType);
        replaceMap.put("diskName", "multi_cloud_manage_hw_disk_"+uuid);
        replaceMap.put("diskSize",diskSize+"");

        replaceMap.put("hwEipAssociationName","multi_cloud_manage_hw_eip_associate_"+uuid);

        replaceMap.put("hwDiskAttachName","multi_cloud_manage_disk_attached_"+uuid);

        replaceMap.put("hwEipName","multicloud_manage_hw_eip_"+uuid);

        replaceMap.put("hwInstanceName","multicloud_manage_hw_instance_"+uuid);

        replaceMap.put("rootPassword",password);

        replaceMap.put("hwVpcName","multicloud_manage_hw_vpc_"+uuid);

        StringSubstitutor stringSubstitutor = new StringSubstitutor(replaceMap);
        String formatTemplateString = stringSubstitutor.replace(templateString);

        File aimDir = new File(tfBasePath+"\\"+uuid);
        aimDir.mkdir();
        File f = new File(aimDir.getAbsoluteFile() + "\\"+HW_FILE_NAME);
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
        JSONObject awsPublicIpObj = outputs.getJSONObject("public_ip");
        String publicIp = awsPublicIpObj.getString("value");
        resource.setPublicIp(publicIp);
        resource.setResourceStatus(ResourceStatusEnum.RUNNING.getCode());

        Date currentDate = new Date();
        resource.setCreateTime(currentDate);

        resourceMapper.insert(resource);

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

        String loginName = "root";
        String password = UUID.randomUUID().toString().replaceAll("-","");
        resource.setLoginName(loginName);
        resource.setPassword(password);

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
        resource.setResourceStatus(ResourceStatusEnum.RUNNING.getCode());

        Date currentDate = new Date();
        resource.setCreateTime(currentDate);

        resourceMapper.insert(resource);

    }
}
