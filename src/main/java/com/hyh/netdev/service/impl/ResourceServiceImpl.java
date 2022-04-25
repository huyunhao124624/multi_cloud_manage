package com.hyh.netdev.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyh.netdev.constant.ResultConstant;
import com.hyh.netdev.dao.DiskMetaMapper;
import com.hyh.netdev.dao.ResourceMapper;
import com.hyh.netdev.dao.VmMetaMapper;
import com.hyh.netdev.dto.ApplyResourceDto;
import com.hyh.netdev.entity.DiskMeta;
import com.hyh.netdev.entity.Resource;
import com.hyh.netdev.entity.VmMeta;
import com.hyh.netdev.enums.BooleanEnum;
import com.hyh.netdev.enums.CloudProviderEnum;
import com.hyh.netdev.enums.ResourceStatusEnum;
import com.hyh.netdev.service.ResourceService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private VmMetaMapper vmMetaMapper;

    @Autowired
    private DiskMetaMapper diskMetaMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Value("${terraform.tempPath}")
    private String tfBasePath;

    private static String awsLocalFilePath = "terraform_template/aws/aws_local.tf";
    private static String AWS_FILE_NAME = "aws.tf";



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
