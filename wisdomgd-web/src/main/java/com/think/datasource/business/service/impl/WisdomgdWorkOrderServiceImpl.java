package com.think.datasource.business.service.impl;

import com.think.business.request.WorkOrderDailyRequest;
import com.think.business.vo.WorkOrderDailyVo;
import com.think.datasource.business.entity.WisdomgdWorkOrder;
import com.think.datasource.business.mapper.WisdomgdWorkOrderMapper;
import com.think.datasource.business.service.IWisdomgdWorkOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 工单信息 服务实现类
 * </p>
 *
 * @author LF
 * @since 2022-09-17
 */
@Service
public class WisdomgdWorkOrderServiceImpl extends ServiceImpl<WisdomgdWorkOrderMapper, WisdomgdWorkOrder> implements IWisdomgdWorkOrderService {

    @Override
    public List<WorkOrderDailyVo> queryWorkOrderDaily(WorkOrderDailyRequest param) {

        return null;
    }
}
