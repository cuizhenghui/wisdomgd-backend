package com.think.datasource.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.think.datasource.system.entity.SysLogininfor;

/**
 * 系统访问日志情况信息 服务层
 *
 * @author Gary
 */
public interface ISysLogininforService  extends IService<SysLogininfor> {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    public void insertLogininfor(SysLogininfor logininfor);

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    public IPage<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

    /**
     * 清空系统登录日志
     */
    public void cleanLogininfor();
}
