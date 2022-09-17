package com.think.datasource.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.think.common.constant.UserConstants;
import com.think.common.exception.CustomException;
import com.think.datasource.system.entity.SysRole;
import com.think.datasource.system.entity.SysRoleMenu;
import com.think.datasource.system.entity.SysUserRole;
import com.think.datasource.system.mapper.SysRoleMapper;
import com.think.datasource.system.service.ISysRoleMenuService;
import com.think.datasource.system.service.ISysRoleService;
import com.think.datasource.system.service.ISysUserRoleService;
import com.think.framework.aspectj.lang.annotation.DataScope;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色 业务层处理
 *
 * @author Gary
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    ISysRoleMenuService roleMenuService;
    @Autowired
    ISysUserRoleService userRoleService;

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public IPage<SysRole> selectRoleList(SysRole role) {
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        qw.eq("del_flag", "0");
        if (StrUtil.isNotBlank(role.getRoleName())) {
            qw.like("role_name", role.getRoleName());
        }
        if (ObjectUtil.isNotNull(role.getStatus())) {
            qw.eq("status", role.getStatus());
        }
        if (StrUtil.isNotBlank(role.getRoleKey())) {
            qw.like("role_key", role.getRoleKey());
        }
        if (StrUtil.isNotBlank(role.getBeginTime())) {
            qw.ge("create_time", DateUtil.parseDate(role.getBeginTime()));
        }
        if (StrUtil.isNotBlank(role.getEndTime())) {
            qw.le("create_time", DateUtil.parseDate(role.getEndTime()));
        }
        IPage<SysRole> page = null;
        if (role.getPageNum() > 0 && role.getPageSize() > 0) {
            page = new Page<>(role.getPageNum(), role.getPageSize());
        } else {
            page = new Page<>(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
        }
        return baseMapper.selectPage(page, qw);
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = baseMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (ObjectUtil.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    public List<Integer> selectRoleListByUserId(Long userId) {
        return baseMapper.selectRoleListByUserId(userId);
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    public SysRole selectRoleById(Long roleId) {
        return baseMapper.selectRoleById(roleId);
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(SysRole role) {
        Long roleId = ObjectUtil.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        qw.eq("role_name", role.getRoleName());
        SysRole info = baseMapper.selectOne(qw);
        if (ObjectUtil.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(SysRole role) {
        Long roleId = ObjectUtil.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        qw.eq("role_key", role.getRoleKey());
        SysRole info = baseMapper.selectOne(qw);
        if (ObjectUtil.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    public void checkRoleAllowed(SysRole role) {
        if (ObjectUtil.isNotNull(role.getRoleId()) && role.isAdmin()) {
            throw new CustomException("不允许操作超级管理员角色");
        }
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId) {
        QueryWrapper<SysUserRole> qw = new QueryWrapper<>();
        qw.eq("role_id", roleId);
        return userRoleService.count(qw);
    }

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(SysRole role) {
        // 新增角色信息
        baseMapper.insert(role);
        return insertRoleMenu(role);
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(SysRole role) {
        // 修改角色信息
        baseMapper.updateById(role);
        // 删除角色与菜单关联
        QueryWrapper<SysRoleMenu> qw = new QueryWrapper<>();
        qw.eq("role_id", role.getRoleId());
        roleMenuService.remove(qw);
        return insertRoleMenu(role);
    }

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public boolean authDataScope(SysRole role) {
        // 修改角色信息
        baseMapper.updateById(role);
        // 新增角色和部门信息（数据权限）
        return true;
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(SysRole role) {
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        boolean flag = false;
        if (list.size() > 0) {
            flag = roleMenuService.saveBatch(list);
        }
        return flag ? list.size() : 0;
    }



    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    public int deleteRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            checkRoleAllowed(new SysRole(roleId));
            SysRole role = baseMapper.selectById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new CustomException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        return baseMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 查询用户所属角色组
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public List<SysRole> selectRolesByUserName(String userName) {
        return baseMapper.selectRolesByUserName(userName);
    }
}
