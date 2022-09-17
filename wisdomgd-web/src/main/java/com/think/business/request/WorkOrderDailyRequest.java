package com.think.business.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Description: 工单管理-日常巡检-查询
 * @Author: cuizhenghui
 * @Date: 2022/9/17
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "工单管理-日常巡检-查询", description = "请求")
public class WorkOrderDailyRequest {

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime creatTime;
}
