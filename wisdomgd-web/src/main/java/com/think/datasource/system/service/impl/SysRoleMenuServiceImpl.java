package com.think.datasource.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.think.datasource.system.entity.SysRoleMenu;
import com.think.datasource.system.mapper.SysRoleMenuMapper;
import com.think.datasource.system.service.ISysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author Gary
 * @since 2019-12-17
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

}
