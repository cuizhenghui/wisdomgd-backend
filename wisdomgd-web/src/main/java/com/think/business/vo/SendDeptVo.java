package com.think.business.vo;

import lombok.Data;

@Data
public class SendDeptVo {

    /**
     * 分发部门ID
     */
    private Long operatorDeptId;
    /**
     * 部门名称
     */
    private String operatorDeptName;
    /**
     * 指导意见
     */
    private String opinionInst;
    /**
     * 任务等级(映射字典表 opinion_task_level 1-常态任务 2-警示任务 3-紧急任务)
     */
    private Integer taskLevel;
    /**
     * 天
     */
    private Integer day;
    /**
     * 小时
     */
    private Integer time;
    /**
     * 处置时限( 以小时为单位 )
     */
    private Integer timeLimit;

}
