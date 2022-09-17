package com.think.system.system.controller;

import com.think.common.exception.CustomException;
import com.think.common.utils.CheckPassword;
import com.think.common.utils.SecurityUtils;
import com.think.common.utils.ServletUtils;
import com.think.common.utils.file.FileUploadUtils;
import com.think.datasource.system.entity.SysUser;
import com.think.datasource.system.service.ISysUserService;
import com.think.framework.aspectj.lang.annotation.Log;
import com.think.framework.aspectj.lang.enums.BusinessType;
import com.think.framework.config.ThinkConfig;
import com.think.framework.security.LoginUser;
import com.think.framework.security.service.TokenService;
import com.think.framework.web.controller.BaseController;
import com.think.framework.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * 个人信息 业务处理
 *
 * @author Gary
 */
@Slf4j
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        AjaxResult ajax = AjaxResult.success(user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUsername()));
        ajax.put("postGroup", userService.selectUserPostGroup(loginUser.getUsername()));
        return ajax;
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody SysUser user) {
        user.setUpdateBy(SecurityUtils.getUsername());
        user.setUpdateTime(new Date());
        return toAjax(userService.updateById(user));
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        log.info("重置密码:{}",loginUser.toString());
        String userName = loginUser.getUsername();
        long userId = loginUser.getUser().getUserId();
        if(userId > 0){
            SysUser sql = userService.getById(userId);
            String password = sql.getPassword();
            if (!SecurityUtils.matchesPassword(oldPassword, password)) {
                return AjaxResult.error("修改密码失败，旧密码错误");
            }
            if (SecurityUtils.matchesPassword(newPassword, password)) {
                return AjaxResult.error("新密码不能与旧密码相同");
            }
            //密码强度校验
            boolean flagPwd = CheckPassword.checkPasswordRule(newPassword, sql.getUserName());
            if(!flagPwd)
                throw new CustomException("密码较弱，请重新设置");
            return toAjax(userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)));
        }
        return AjaxResult.error("该用户不存在");
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String avatar = FileUploadUtils.upload(ThinkConfig.getAvatarPath(), file);
            if (userService.updateUserAvatar(loginUser.getUsername(), avatar)) {
                AjaxResult ajax = AjaxResult.success();
                ajax.put("imgUrl", avatar);
                loginUser.getUser().setAvatar(avatar);
                tokenService.setLoginUser(loginUser);
                return ajax;
            }
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }
}
