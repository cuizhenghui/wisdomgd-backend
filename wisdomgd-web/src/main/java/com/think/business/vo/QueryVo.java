package com.think.business.vo;

import com.think.framework.web.domain.BaseEntity;
import lombok.Data;

@Data
public class QueryVo extends BaseEntity {

    /**
     * 筛选类别(1-待处理 2-处理中 3-已完结 0-全部 )
     */
    public Integer queryType;
    /**
     * 查询类别(1-待处理 2-处理中 3-已完结 4-检索)
     */
    public Integer searchType;
    /**
     * 个人部门ID
     */
    public Long deptId;

    /**
     * 分发类型(1-全部 2-已分发 3-未分发)
     */
    public Integer distributeType;

    /**
     * 类型  2-新增 3-上报
     */
    private Integer saveType;

}
