package com.think.common.constant;

/**
 * 业务常量信息
 *
 * @author Gary
 */
public class BizConstants {
    /**
     * 数据状态 字典表 opinion_order_status 0-上报待处理 1-新增待处理 2-处理中 3-办结 4-撤销 5-强制回收)
     */
    public static final String OPINION_ORDER_STATUS_REPORT = "0";
    public static final String OPINION_ORDER_STATUS_PENDING = "1";
    public static final String OPINION_ORDER_STATUS_PROCESSING = "2";
    public static final String OPINION_ORDER_STATUS_OVER = "3";
    public static final String OPINION_ORDER_STATUS_REVOKE = "4";
    public static final String OPINION_ORDER_STATUS_REVOKE_FORCE = "5";

    /**
     * 数据来源 1-预警推送 2-手动添加
     */
    public static final int OPINION_DATA_FROM_WARN = 1;
    public static final int OPINION_DATA_FROM_HAND = 2;

    /**
     * 阅读标记(字典表 opinion_read_flag 0-未读 1-已读)
     */
    public static final String OPINION_READ_FLAG_YES = "1";
    public static final String OPINION_READ_FLAG_NO = "0";

    /**
     * 字典表的key
     */
    public static final String DICT_OPINION_SOURCE = "opinion_source";
    public static final String DICT_OPINION_TYPE = "opinion_type";

    /**
     * 流程状态 (1-未完成 2-已完成 3-强制撤销)
     */
    public static final String PROCESS_TYPE_INCOMPLETE = "1";
    public static final String PROCESS_TYPE_COMPLETE = "2";
    public static final String PROCESS_TYPE_REVOKE = "3";

    /**
     * 操作类别 操作类别(字典表 opinion_operator_type 1-提交 2-上报 3-分发 4-重新分发 5-办结 6-驳回下级 7-撤销 8-提交上级 9-处置 10-退回上级 11-强制撤销)
     */
    public static final String OPERATOR_TYPE_SUBMIT = "1";
    public static final String OPERATOR_TYPE_REPORT = "2";
    public static final String OPERATOR_TYPE_SEND = "3";
    public static final String OPERATOR_TYPE_RESEND = "4";
    public static final String OPERATOR_TYPE_OVER = "5";
    public static final String OPERATOR_TYPE_REJECT = "6";
    public static final String OPERATOR_TYPE_REVOKE = "7";
    public static final String OPERATOR_TYPE_HIGHER = "8";
    public static final String OPERATOR_TYPE_PROCESS = "9";
    public static final String OPERATOR_TYPE_BACK = "10";
    public static final String OPERATOR_TYPE_REVOKE_FORCE = "11";

    public static final String WECHAT_MSG_PENDING = "您收到一条待办事项";
    public static final String WECHAT_MSG_URGE = "您收到一条催办信息";
    public static final String WECHAT_MSG_SUPERVISE = "您收到一条督办信息";
    public static final String WECHAT_MSG_CONTENT = "请登录系统进行事项处理";
    public static final String WECHAT_MSG_REMARK = "谢谢！";
    public static final String MINI_FORWORD_BASE = "pages/login/login?mini=1&programId=";
    public static final String MINI_FORWORD_FLOW = "/pages/pending/pedingDetail?orderId=";
    public static final String MINI_FORWORD_LEADER = "/pages/leader/leaderDetail?orderId=";
}
