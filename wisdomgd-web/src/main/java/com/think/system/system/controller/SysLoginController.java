package com.think.system.system.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.think.common.constant.Constants;
import com.think.common.exception.CustomException;
import com.think.common.exception.user.UserPasswordNotMatchException;
import com.think.common.utils.ServletUtils;
import com.think.datasource.system.entity.SysMenu;
import com.think.datasource.system.entity.SysUser;
import com.think.datasource.system.service.ISysMenuService;
import com.think.framework.redis.RedisCache;
import com.think.framework.security.LoginUser;
import com.think.framework.security.service.SysLoginService;
import com.think.framework.security.service.SysPermissionService;
import com.think.framework.security.service.TokenService;
import com.think.framework.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 登录验证
 *
 * @author Gary
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录方法
     *
     * @param param 参数
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody SysUser param) {
        if(ObjectUtil.isNull(param))
            throw new CustomException("参数为空");
        if(StrUtil.isBlank(param.getUserName()))
            throw new CustomException("账号为空");
        if(StrUtil.isBlank(param.getPassword()))
            throw new CustomException("密码为空");
        if(StrUtil.isBlank(param.getUuid()))
            throw new CustomException("uuid为空");
        if(StrUtil.isBlank(param.getCaptcha()))
            throw new CustomException("验证码为空");
        String verifyKey = Constants.CAPTCHA_CODE_KEY + param.getUuid();
        String cacheCaptcha = redisCache.getCacheObject(verifyKey);
        if(StrUtil.isBlank(cacheCaptcha))
            throw new CustomException("验证码失效");
        if(!StrUtil.equals(param.getCaptcha().toLowerCase(), cacheCaptcha.toLowerCase()))
            throw new CustomException("验证码不正确");
        //防止登录表单暴力破解
        StringBuilder failKey = new StringBuilder(Constants.NOW_LOGIN_FAIL)
                .append(":").append(param.getUserName())
                .append(":").append(DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN));
        Integer num = redisCache.getCacheObject(failKey.toString());
        if(ObjectUtil.isNotNull(num) && Integer.compare(5, num) <= 0)
            throw new CustomException("你今日已经登录错误5次，请过1小时后再登录。");
        AjaxResult ajax = AjaxResult.success();
        try{
            // 生成令牌
            String token = loginService.login(param.getUserName(), param.getPassword());
            ajax.put(Constants.TOKEN, token);
        }catch (UserPasswordNotMatchException upnme){
            num = ObjectUtil.isNull(num) ? 1 : num + 1;
            redisCache.setCacheObject(failKey.toString(), num, 60, TimeUnit.MINUTES);
            throw upnme;
        }

        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return AjaxResult.success(menuService.buildMenus(menus));
    }

    /**
     * 公号跳转小程序自动登录
     * @param mini
     * @param userId
     * @return
     */
    @GetMapping("/mini/login/{mini}/{programId}")
    public AjaxResult miniLogin(@PathVariable("mini") String mini, @PathVariable("programId") String userId) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.miniLogin(mini, userId);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

}
