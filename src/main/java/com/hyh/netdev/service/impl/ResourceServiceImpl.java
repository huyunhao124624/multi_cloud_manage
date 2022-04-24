package com.hyh.netdev.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyh.netdev.dao.VmMetaMapper;
import com.hyh.netdev.dto.ApplyPrivateResourceDto;
import com.hyh.netdev.entity.Resource;
import com.hyh.netdev.entity.VmMeta;
import com.hyh.netdev.enums.CloudProviderEnum;
import com.hyh.netdev.service.ResourceService;
import com.hyh.netdev.util.TerraformUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private VmMetaMapper vmMetaMapper;

    @Value("${terraform.tempPath}")
    private String tfBasePath;

    private static String awsLocalFilePath = "terraform_template/aws/aws_local.tf";
    private static String AWS_FILE_NAME = "aws.tf";



    @Override
    public void applyPrivateResource(ApplyPrivateResourceDto requestDto,Integer userId,Integer departmentId) {
        Integer cpuNum = requestDto.getCpuNum();
        Integer memorySize = requestDto.getMemorySize();
        Integer diskSize = requestDto.getDiskSize();
        VmMeta vmMeta = vmMetaMapper.selectOne(new QueryWrapper<VmMeta>()
                .eq("cpu", cpuNum)
                .eq("memory", memorySize)
                .eq("cloud_provider", requestDto.getCloudProviderCode()));
        D

        String instanceType = vmMeta.getInstanceType();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");

        Resource resource = new Resource();
        resource.set




    }

    private boolean handleInstanceType(String uuid,String instanceType,Integer diskSize,String amiOutId,Resource resource){

        if(StringUtils.equals(CloudProviderEnum.AWS.getCode(),instanceType)){

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
    private JSONObject handleAWSApply(String uuid, String instanceType, Integer diskSize, String amiOutId,Resource resource) throws IOException {



        File file = ResourceUtils.getFile("classpath:"+awsLocalFilePath);
        String templateString = TerraformUtil.txt2String(file);

        Map<String,String> replaceMap = new HashMap<>();
        replaceMap.put("awsEipName",uuid+"_aws_eip_name");
        replaceMap.put("awsInstanceName",uuid+"_aws_instance_name");
        replaceMap.put("awsEipAssociationName",uuid+"_aws_eip_association_name");
        replaceMap.put("amiOutId",amiOutId);
        replaceMap.put("instanceType",instanceType);
        replaceMap.put("diskName",uuid+"_aws_disk");
        replaceMap.put("diskSize",diskSize+"");

        StringSubstitutor stringSubstitutor = new StringSubstitutor(replaceMap);
        String formatTemplateString = stringSubstitutor.replace(templateString);

        File aimDir = new File(tfBasePath+uuid);
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
            return null;
        }

        boolean ifApplySuccess = TerraformUtil.terraformApply(aimDir);
        if(!ifApplySuccess){
            log.error("terraform apply {} failed",uuid);
            return null;
        }

        JSONObject applyResultJSON = TerraformUtil.terraformShow(aimDir);




        return applyResultJSON;
    }
}
