package com.think.datasource.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.think.common.constant.UserConstants;
import com.think.common.exception.CustomException;
import com.think.common.utils.StringUtils;
import com.think.datasource.system.entity.SysDept;
import com.think.datasource.system.entity.SysUser;
import com.think.datasource.system.mapper.SysDeptMapper;
import com.think.datasource.system.service.ISysDeptService;
import com.think.datasource.system.service.ISysUserService;
import com.think.framework.aspectj.lang.annotation.DataScope;
import com.think.framework.web.domain.TreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理 服务实现
 *
 * @author Gary
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Autowired
    ISysUserService userService;

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept) {
        QueryWrapper<SysDept> qw = new QueryWrapper<>();
        qw.eq("del_flag", "0");
        if (ObjectUtil.isNotNull(dept.getParentId()) && dept.getParentId() != 0) {
            qw.apply("FIND_IN_SET({0}, ancestors)", dept.getParentId());
        }
        if (StrUtil.isNotBlank(dept.getDeptName())) {
            qw.like("dept_name", dept.getDeptName());
        }
        if (ObjectUtil.isNotNull(dept.getStatus())) {
            qw.eq("status", dept.getStatus());
        }
        /*if (StrUtil.isNotBlank(dept.getDataScope())) {
            qw.apply(StrUtil.sub(dept.getDataScope(), 4, dept.getDataScope().length()));
        }*/
        qw.orderByAsc("parent_id", "order_num");
        return baseMapper.selectList(qw);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts, Long parentId) {
        List<SysDept> returnList = new ArrayList<SysDept>();
        Long id = ObjectUtil.isNotNull(parentId) ? parentId : 0;
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext(); ) {
            SysDept t = (SysDept) iterator.next();
            t.setVirtualShow(t.getVirtualFlag() == 1 ? true : false);
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == id) {
                recursionFn(depts, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts, Long parentId) {
        List<SysDept> deptTrees = buildDeptTree(depts, parentId);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据部门ID 获取上级部门树
     * @param deptId
     * @return
     */
    @Override
    public List<TreeSelect> getReportDepts(Long deptId) {
        List<SysDept> allDeptList = this.lambdaQuery().eq(SysDept::getDelFlag, 0)
                .orderByAsc(SysDept::getParentId, SysDept::getOrderNum)
                .list();
        SysDept curDept = this.getById(deptId);
        String ancestors = curDept.getAncestors();
        List<String> pathList = CollUtil.toList(ancestors.split(","));
        List<SysDept> reList = new ArrayList<>();
        boolean isSchool = false;
        Long schoolDeptId = null;
        int size = allDeptList.size();
        for (int i = 0; i < size; i++) {
            SysDept dept = allDeptList.get(i);
            if (pathList.contains(String.valueOf(dept.getDeptId()))) {
                if (dept.getDeptName().equals("高校")) {
                    isSchool = true;
                    schoolDeptId = dept.getDeptId();
                } else {
                    reList.add(dept);
                }
            }
        }
        Long parentId = null;
        if (isSchool) { // 高校
            SysDept rootDept = reList.get(1);
            parentId = rootDept.getDeptId();
            List<SysDept> selectList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                SysDept dept = allDeptList.get(i);
                if (dept.getParentId() == schoolDeptId || dept.getDeptId() == schoolDeptId) {
                    continue;
                }
                selectList.add(dept);
            }
            reList = selectList;
        } else { // 教育局
            if(reList.size() > 0){
                int lastIndex = reList.size() - 1;
                SysDept lastDept = reList.get(lastIndex);
                if (lastDept.getVirtualFlag() == 1) {
                    reList.remove(lastIndex);
                }
            }
        }
        List<TreeSelect> treeSelects = buildDeptTreeSelect(reList, parentId);
        if (isSchool) {
            List<TreeSelect> tempList = new ArrayList<>();
            SysDept rootDept = allDeptList.get(0);
            TreeSelect root = new TreeSelect()
                    .setId(rootDept.getDeptId())
                    .setLabel(rootDept.getDeptName())
                    .setChildren(treeSelects)
                    .setVirtualFlag(0)
                    .setVirtualShow(false);
            tempList.add(root);
            treeSelects = tempList;
        }
        return treeSelects;
    }

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Integer> selectDeptListByRoleId(Long roleId) {
        return baseMapper.selectDeptListByRoleId(roleId);
    }

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        QueryWrapper<SysDept> qw = new QueryWrapper<>();
        qw.eq("del_flag", "0").eq("parent_id", deptId);
        int result = baseMapper.selectCount(qw);
        return result > 0 ? true : false;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("dept_id", deptId).eq("del_flag", "0");
        int result = userService.count(qw);
        return result > 0 ? true : false;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDept dept) {
        Long deptId = ObjectUtil.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        QueryWrapper<SysDept> qw = new QueryWrapper<>();
        qw.eq("dept_name", dept.getDeptName()).eq("parent_id", dept.getParentId());
        SysDept info = baseMapper.selectOne(qw);
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDept dept) {
        SysDept info = baseMapper.selectById(dept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new CustomException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return baseMapper.insert(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(SysDept dept) {
        SysDept newParentDept = baseMapper.selectById(dept.getParentId());
        SysDept oldDept = baseMapper.selectById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = baseMapper.updateById(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(SysDept dept) {
        dept = baseMapper.selectById(dept.getDeptId());
        SysDept updateDept = new SysDept();
        if (ObjectUtil.isNotNull(dept.getStatus())) {
            updateDept.setStatus(dept.getStatus());
        }
        if (StrUtil.isNotBlank(dept.getUpdateBy())) {
            updateDept.setUpdateBy(dept.getUpdateBy());
        }
        updateDept.setUpdateTime(new Date());
        UpdateWrapper<SysDept> uw = new UpdateWrapper<>();
        uw.inSql("dept_id", dept.getAncestors());
        baseMapper.update(updateDept, uw);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = baseMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            baseMapper.updateDeptChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId) {
        SysDept dept = new SysDept();
        dept.setDeptId(deptId);
        dept.setDelFlag(1);
        return baseMapper.updateById(dept);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t) {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        //list.removeAll(childList);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<SysDept> it = childList.iterator();
                while (it.hasNext()) {
                    SysDept n = (SysDept) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext()) {
            SysDept n = (SysDept) it.next();
            if (n.getParentId().longValue() == t.getDeptId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 获取父级业务部门ID
     * @param parentDeptId
     * @return
     */
    @Override
    public SysDept getParentDept(Long parentDeptId) {
        SysDept dept = this.getOne(new QueryWrapper<SysDept>().lambda().eq(SysDept::getDeptId, parentDeptId));
        if (dept.getVirtualFlag() == 1) { // 虚拟节点
            return getParentDept(dept.getParentId());
        } else { // 业务节点
            return dept;
        }
    }
}
