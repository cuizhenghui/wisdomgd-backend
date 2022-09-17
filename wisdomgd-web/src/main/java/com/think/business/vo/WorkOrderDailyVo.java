package com.think.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description: 工单管理-日常巡检-查询,返回实体
 * @Author: cuizhenghui
 * @Date: 2022/9/17
 */
@ApiModel(value = "工单管理-日常巡检-查询", description = "返回")
@Data
@Accessors(chain = true)
public class WorkOrderDailyVo {
    /**
     * 企业ID
     */
    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;

    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    /**
     * 法人姓名
     */
    @ApiModelProperty(value = "法人姓名")
    private String legalPersonName;

    /**
     * 企业社会信用代码
     */
    @ApiModelProperty(value = "企业社会信用代码")
    private String enterpriseCode;


    /**
     * 环保联系方式
     */
    @ApiModelProperty(value = "环保联系方式")
    private String environmentalProtectionPhone;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    /**
     * 整改截止日期（yyyy-MM-dd）
     */
    @ApiModelProperty(value = "整改截止日期（yyyy-MM-dd）")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date remediationEndDate;
}
