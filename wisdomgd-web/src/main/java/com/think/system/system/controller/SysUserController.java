package com.think.system.system.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.think.common.constant.UserConstants;
import com.think.common.exception.CustomException;
import com.think.common.utils.CheckPassword;
import com.think.common.utils.SecurityUtils;
import com.think.common.utils.StringUtils;
import com.think.common.utils.poi.ExcelUtil;
import com.think.datasource.system.entity.SysUser;
import com.think.datasource.system.service.ISysPostService;
import com.think.datasource.system.service.ISysRoleService;
import com.think.datasource.system.service.ISysUserService;
import com.think.framework.aspectj.lang.annotation.Log;
import com.think.framework.aspectj.lang.enums.BusinessType;
import com.think.framework.web.controller.BaseController;
import com.think.framework.web.domain.AjaxResult;
import com.think.framework.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 用户信息
 *
 * @author Gary
 */
@RestController
@RequestMapping("/system/user")
@Api(value = "/system/user", description = "用户相关接口", tags = "用户")
public class SysUserController extends BaseController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取用户列表")
    public TableDataInfo list(SysUser user) {
        IPage<SysUser> page = userService.selectUserList(user);
        return getDataTable(page);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/export")
    public AjaxResult export(SysUser user) {
        user.setPageNum(RowBounds.NO_ROW_OFFSET);
        user.setPageSize(RowBounds.NO_ROW_LIMIT);
        IPage<SysUser> page = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(page.getRecords(), "用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("roles", roleService.list());
        ajax.put("posts", postService.list());
        if (StringUtils.isNotNull(userId)) {
            ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (StrUtil.isBlank(user.getPhone())) {
            user.setPhone("");
        }
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
//        else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
//            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
//        }
//        else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
//            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
//        }
        user.setCreateBy(SecurityUtils.getUsername());
        user.setCreateTime(new Date());
        //密码强度校验
        boolean flagPwd = CheckPassword.checkPasswordRule(user.getPassword(), user.getUserName());
        if(!flagPwd)
            throw new CustomException("密码较弱，请重新设置");
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        if (StrUtil.isBlank(user.getPhone())) {
            user.setPhone("");
        }
//        if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
//            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
//        }
//        else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
//            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
//        }
        user.setPassword(null);
        user.setUpdateBy(SecurityUtils.getUsername());
        user.setUpdateTime(new Date());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        SysUser sql = userService.getById(user.getUserId());
        if(ObjectUtil.isNull(sql))
            throw new CustomException("该账号不存在");
        //密码强度校验
        boolean flagPwd = CheckPassword.checkPasswordRule(user.getPassword(), sql.getUserName());
        if(!flagPwd)
            throw new CustomException("密码较弱，请重新设置");
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));

        user.setUpdateBy(SecurityUtils.getUsername());
        user.setUpdateTime(new Date());
        return toAjax(userService.updateById(user));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setUpdateBy(SecurityUtils.getUsername());
        user.setUpdateTime(new Date());
        return toAjax(userService.updateById(user));
    }
}