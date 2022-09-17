package com.think.business.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StatisVo {

    /**
     * 总数
     */
    private Integer totalNum;
    /**
     * 待办数
     */
    private Integer pendNum;
    /**
     * 处理中
     */
    private Integer processNum;
    /**
     * 草稿数
     */
    private Integer draftNum;
}
