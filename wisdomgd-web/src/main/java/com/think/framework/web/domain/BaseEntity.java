package com.think.framework.web.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * Entity基类
 *
 * @author Gary
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    @ApiModelProperty(value = "当前页码")
    private Integer pageNum;
    @TableField(exist = false)
    @ApiModelProperty(value = "每页最大行数")
    private Integer pageSize;
    /**
     * 搜索值
     */
    @TableField(exist = false)
    private String searchValue;

    /**
     * 数据权限
     */
    @TableField(exist = false)
    private String dataScope;

    /**
     * 开始时间
     */
    @TableField(exist = false)
    private String beginTime;

    /**
     * 结束时间
     */
    @TableField(exist = false)
    private String endTime;

    /**
     * 请求参数
     */
    @TableField(exist = false)
    private Map<String, Object> params;

}
