package com.think.business.vo;

import lombok.Data;

import java.util.List;

@Data
public class SendVo {

    /**
     * 数据ID
     */
    private Long orderId;
    /**
     * 流程ID
     */
    private Long processId;
    /**
     * 舆情类别(映射字典表 opinion_type 1-管理维权类 2-非正常伤亡类 3-突发舆情事件类 4-社会热点类 5-教育改革类 6-敏感人群或时间类 7-意识形态类 8-其他类)
     */
    private String opinionType;
    /**
     * 附件列表
     */
    //private List<OpinionResource> resourceList;
    /**
     * 分发单位列表
     */
    private List<SendDeptVo> sendDepts;

}
