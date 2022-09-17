package com.think.datasource.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.think.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 工单问题详情
 * </p>
 *
 * @author LF
 * @since 2022-09-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WisdomgdWorkProblem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 问题ID
     */
    @TableId(value = "wproblem_id", type = IdType.AUTO)
    private Long wproblemId;

    /**
     * 工单ID
     */
    private Long worderId;

    /**
     * 问题类型（1-整改；2-记录）
     */
    private Integer wproblemType;

    /**
     * 问题标签
     */
    private String wproblemTag;

    /**
     * 问题描述
     */
    private String wproblemIntro;

    /**
     * 检查人员ID
     */
    private Long checkUserId;

    /**
     * 有无现场图片（0-无；1-有）
     */
    private Integer checkImageFlag;

    /**
     * 现场图片（附件表ID）
     */
    private String checkImages;

    /**
     * 有无现场附件（0-无；1-有）
     */
    private Integer checkAccessoryFlag;

    /**
     * 现场附件（附件表ID）
     */
    private String checkAccessories;

    /**
     * 整改建议
     */
    private String remediationSuggest;

    /**
     * 检查时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date checkTime;

    /**
     * 整改详情描述
     */
    private String remediationIntro;

    /**
     * 复核人员ID
     */
    private Long reCheckUserId;

    /**
     * 有无整改图片（0-无；1-有）
     */
    private Integer remediationImageFlag;

    /**
     * 整改图片（附件表ID）
     */
    private String remediationImages;

    /**
     * 有无整改附件（0-无；1-有）
     */
    private Integer remediationAccessoryFlag;

    /**
     * 整改附件（附件表ID）
     */
    private String remediationAccessories;

    /**
     * 复查时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reCheckTime;

    /**
     * 问题状态（1-检查（记录）；2-复核）
     */
    private Integer wproblemStatus;


}
