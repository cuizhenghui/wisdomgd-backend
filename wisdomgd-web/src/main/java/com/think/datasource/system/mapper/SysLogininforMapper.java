package com.think.datasource.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.think.datasource.system.entity.SysLogininfor;

/**
 * 系统访问日志情况信息 数据层
 *
 * @author Gary
 */
public interface SysLogininforMapper extends BaseMapper<SysLogininfor> {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    public void insertLogininfor(SysLogininfor logininfor);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    public int cleanLogininfor();
}
