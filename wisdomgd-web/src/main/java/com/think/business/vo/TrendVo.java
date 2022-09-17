package com.think.business.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TrendVo {

    /**
     * 待办数量
     */
    private Integer pendNum;
    /**
     * 时间
     */
    private String createTime;
}
