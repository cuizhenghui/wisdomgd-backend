package com.think.datasource.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.think.datasource.system.entity.SysOperLog;
import com.think.datasource.system.mapper.SysOperLogMapper;
import com.think.datasource.system.service.ISysOperLogService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

/**
 * 操作日志 服务层处理
 *
 * @author Gary
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public IPage<SysOperLog> selectOperLogList(SysOperLog operLog) {
        QueryWrapper<SysOperLog> qw = new QueryWrapper<>();
        if (StrUtil.isNotBlank(operLog.getTitle())) {
            qw.like("title", operLog.getTitle());
        }
        if (ObjectUtil.isNotNull(operLog.getBusinessType())) {
            qw.eq("business_type", operLog.getBusinessType());
        }
        if (ArrayUtil.isNotEmpty(operLog.getBusinessTypes())) {
            qw.inSql("business_type", ArrayUtil.join(operLog.getBusinessTypes(), ","));
        }
        if (ObjectUtil.isNotNull(operLog.getStatus())) {
            qw.eq("status", operLog.getStatus());
        }
        if (StrUtil.isNotBlank(operLog.getOperName())) {
            qw.like("operName", operLog.getOperName());
        }
        if (StrUtil.isNotBlank(operLog.getBeginTime())) {
            qw.ge("oper_time", DateUtil.parseDate(operLog.getBeginTime()));
        }
        if (StrUtil.isNotBlank(operLog.getEndTime())) {
            qw.le("oper_time", DateUtil.parseDate(operLog.getEndTime()));
        }
        IPage<SysOperLog> page = null;
        if (operLog.getPageNum() > 0 && operLog.getPageSize() > 0) {
            page = new Page<>(operLog.getPageNum(), operLog.getPageSize());
        } else {
            page = new Page<>(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
        }
        return baseMapper.selectPage(page, qw);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        baseMapper.cleanOperLog();
    }
}
