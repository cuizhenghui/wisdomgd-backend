package com.think.datasource.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.think.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 工单信息
 * </p>
 *
 * @author LF
 * @since 2022-09-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WisdomgdWorkOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 工单ID
     */
    @TableId(value = "worder_id", type = IdType.AUTO)
    private Long worderId;

    /**
     * 工单类型（1-日常巡检；2-专项工单）
     */
    private Integer worderType;

    /**
     * 日常巡检日期（yyyy-MM-dd）
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dailyDate;

    /**
     * 专项工单名称
     */
    private String specialName;

    /**
     * 专项工单问题标签
     */
    private String specialProblemTag;

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 法人姓名
     */
    private String legalPersonName;

    /**
     * 企业社会信用代码
     */
    private String enterpriseCode;

    /**
     * 环保企业负责人名称
     */
    private String environmentalProtectionPersonName;

    /**
     * 环保联系方式
     */
    private String environmentalProtectionPhone;

    /**
     * 地址
     */
    private String address;

    /**
     * 整改截止日期（yyyy-MM-dd）
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date remediationEndDate;

    /**
     * 工单状态（0-草稿；1-审核中；2-审核通过；3-审核不通过）
     */
    private Integer infoStatus;

    /**
     * 是否删除（0-否；1-是）
     */
    private String delFlag;

    /**
     * 创建工单用户ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改工单用户ID
     */
    private Long updateUserId;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}
