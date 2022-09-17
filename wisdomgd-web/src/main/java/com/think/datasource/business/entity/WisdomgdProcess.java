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
 * 审核流程
 * </p>
 *
 * @author LF
 * @since 2022-09-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WisdomgdProcess extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 流程ID
     */
    @TableId(value = "process_id", type = IdType.AUTO)
    private Long processId;

    /**
     * 业务类型：（work_order-工单信息；）
     */
    private String businessType;

    /**
     * 业务id
     */
    private Long businessId;

    /**
     * 父流程ID
     */
    private Long parentId;

    /**
     * 流程链路
     */
    private String ancestors;

    /**
     * 流程操作备用信息JSON
     */
    private String operatorJson;

    /**
     * 阅读标记(0-未读 1-已读)
     */
    private Integer readFlag;

    /**
     * 流程状态(1-待审核；2-已通过；3-不通过)
     */
    private Integer processStatus;

    /**
     * 流程发起用户id
     */
    private Long fromUserId;

    /**
     * 流程发起部门id
     */
    private Long fromDeptId;

    /**
     * 流程发起角色id
     */
    private Long fromRoleId;

    /**
     * 流程指向角色id
     */
    private Long toRoleId;

    /**
     * 流程指向部门id
     */
    private Long toDeptId;

    /**
     * 备注（建议）
     */
    private String remark;

    /**
     * 创建用户ID
     */
    private Long createUserId;

    /**
     * 流程开关（on-开启；off-关闭）
     */
    private String processSwitch;

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
