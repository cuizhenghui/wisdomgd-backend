package com.think.datasource.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.think.datasource.system.entity.SysOperLog;

/**
 * 操作日志 服务层
 *
 * @author Gary
 */
public interface ISysOperLogService  extends IService<SysOperLog> {

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    public IPage<SysOperLog> selectOperLogList(SysOperLog operLog);

    /**
     * 清空操作日志
     */
    public void cleanOperLog();
}
