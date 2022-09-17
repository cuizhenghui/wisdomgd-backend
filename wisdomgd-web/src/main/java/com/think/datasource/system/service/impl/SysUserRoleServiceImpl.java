package com.think.datasource.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.think.datasource.system.entity.SysUserRole;
import com.think.datasource.system.mapper.SysUserRoleMapper;
import com.think.datasource.system.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author Gary
 * @since 2019-12-17
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public List<Long> getUserIdsByRoleKey(String roleKey) {
        return baseMapper.getUserIdsByRoleKey(roleKey);
    }
}
