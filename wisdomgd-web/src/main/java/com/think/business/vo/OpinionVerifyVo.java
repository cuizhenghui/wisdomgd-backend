package com.think.business.vo;

import lombok.Data;

import java.util.List;

@Data
public class OpinionVerifyVo {

    /**
     * 流程ID
     */
    private Long processId;

    /**
     * 操作类别(字典表 opinion_operator_type 1-提交 2-上报 3-分发 4-重新分发 5-办结 6-驳回下级 7-撤销 8-提交上级 9-处置 10-退回上级 11-强制撤销)
     */
    private String operatorType;

    /**
     * 操作内容，包括驳回理由，退回理由，撤销理由
     */
    private String operatorContent;

    /**
     * 附件列表
     */
    //private List<OpinionResource> resourceList;
}
