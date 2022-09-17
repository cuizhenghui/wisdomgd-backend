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
 * 企业名称库
 * </p>
 *
 * @author LF
 * @since 2022-09-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WisdomgdEnterpriseInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 企业ID
     */
    @TableId(value = "enterprise_id", type = IdType.AUTO)
    private Long enterpriseId;

    /**
     * 企业页签ID
     */
    private Long entagId;

    /**
     * 企业名称
     */
    private String enterpriseName;

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
     * 其他字段
     */
    private String otherJson;

    /**
     * 是否删除（0-否；1-是）
     */
    private String delFlag;

    /**
     * 创建用户ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改用户ID
     */
    private Long updateUserId;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}
