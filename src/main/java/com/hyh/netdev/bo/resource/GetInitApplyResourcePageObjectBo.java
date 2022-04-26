package com.hyh.netdev.bo.resource;

import lombok.Data;

import java.util.List;

@Data
public class GetInitApplyResourcePageObjectBo {

    String cloudProviderCode;
    String cloudProviderName;
    List<AmiOutBo> imageList;
    List<ResourceSpecsBo> resourceSpecsList;
    List<ResourceTypeBo> resourceTypeList;



    @Data
    public static class AmiOutBo{
        private String amiOutId;
        private String amiName;
    }

    @Data
    public static class ResourceSpecsBo{
        private String instanceType;
        private Integer cpuNum;
        private Integer memorySize;
    }

    @Data
    public static class ResourceTypeBo{
        private String resourceTypeCode;
        private String resourceTypeName;
    }
}
