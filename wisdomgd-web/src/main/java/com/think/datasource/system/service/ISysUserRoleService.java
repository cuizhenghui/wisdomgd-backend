package com.think.datasource.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.think.datasource.system.entity.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务类
 * </p>
 *
 * @author Gary
 * @since 2019-12-17
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    List<Long> getUserIdsByRoleKey(String roleKey);
}
