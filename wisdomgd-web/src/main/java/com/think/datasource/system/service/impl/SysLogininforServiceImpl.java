package com.think.datasource.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.think.datasource.system.entity.SysLogininfor;
import com.think.datasource.system.mapper.SysLogininforMapper;
import com.think.datasource.system.service.ISysLogininforService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author Gary
 */
@Service
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforMapper, SysLogininfor> implements ISysLogininforService {

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLogininfor logininfor) {
        baseMapper.insertLogininfor(logininfor);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public IPage<SysLogininfor> selectLogininforList(SysLogininfor logininfor) {
        QueryWrapper<SysLogininfor> qw = new QueryWrapper<>();
        if (StrUtil.isNotBlank(logininfor.getIpaddr())) {
            qw.like("ipaddr", logininfor.getIpaddr());
        }
        if (ObjectUtil.isNotNull(logininfor.getStatus())) {
            qw.eq("status", logininfor.getStatus());
        }
        if (StrUtil.isNotBlank(logininfor.getUserName())) {
            qw.like("user_name", logininfor.getUserName());
        }
        if (StrUtil.isNotBlank(logininfor.getBeginTime())) {
            qw.ge("login_time", DateUtil.parseDate(logininfor.getBeginTime()));
        }
        if (StrUtil.isNotBlank(logininfor.getEndTime())) {
            qw.le("login_time", DateUtil.parseDate(logininfor.getEndTime()));
        }
        IPage<SysLogininfor> page = null;
        if (logininfor.getPageNum() > 0 && logininfor.getPageSize() > 0) {
            page = new Page<>(logininfor.getPageNum(), logininfor.getPageSize());
        } else {
            page = new Page<>(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
        }
        return baseMapper.selectPage(page, qw);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor() {
        baseMapper.cleanLogininfor();
    }
}
