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
 * 排班记录
 * </p>
 *
 * @author LF
 * @since 2022-09-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WisdomgdWorkingScheduleLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 排班记录ID
     */
    @TableId(value = "wslog_id", type = IdType.AUTO)
    private Long wslogId;

    /**
     * 日历ID
     */
    private String wscalendarId;

    /**
     * 值班人员名称
     */
    private String wsUserName;

    /**
     * 值班人员手机号码
     */
    private String wsUserPhone;

    /**
     * 角色
     */
    private String wsRole;

    /**
     * 备注
     */
    private String remark;

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
