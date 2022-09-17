package com.think.datasource.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.think.business.request.WorkOrderDailyRequest;
import com.think.business.vo.WorkOrderDailyVo;
import com.think.datasource.business.entity.WisdomgdWorkOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 工单信息 服务类
 * </p>
 *
 * @author LF
 * @since 2022-09-17
 */
public interface IWisdomgdWorkOrderService extends IService<WisdomgdWorkOrder> {

    /**
     * 工单管理-日常巡检-查询
     *
     * @param param WorkOrderDailyRequest
     * @return List<WorkOrderDailyVo>
     */
    List<WorkOrderDailyVo> queryWorkOrderDaily(WorkOrderDailyRequest param);
}
