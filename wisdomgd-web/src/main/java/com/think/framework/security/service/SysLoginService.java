package com.think.framework.security.service;

import cn.hutool.core.util.StrUtil;
import com.think.common.constant.Constants;
import com.think.common.exception.CustomException;
import com.think.common.exception.user.UserPasswordNotMatchException;
import com.think.common.utils.MessageUtils;
import com.think.datasource.system.entity.SysUser;
import com.think.datasource.system.service.ISysUserService;
import com.think.framework.manager.AsyncManager;
import com.think.framework.manager.factory.AsyncFactory;
import com.think.framework.redis.RedisCache;
import com.think.framework.security.LoginUser;
import com.think.framework.security.mini.MiniAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 *
 * @author Gary
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 正常登录
     * @param userName
     * @param password
     * @return
     */
    public String login(String userName, String password) {
        return login(userName, password, null, null);
    }

    /**
     * 公众号跳转小程序登录
     * @param mini 默认为 1
     * @param userId
     * @return
     */
    public String miniLogin(String mini, String userId) {
        return login(null, null, mini, userId);
    }

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param mini
     * @param userId
     * @return 结果
     */
    private String login(String username, String password, String mini, String userId) {
        // 用户验证
        Authentication authentication = null;
        try {
            if ("1".equals(mini) && StrUtil.isNotBlank(userId)) { // 公众号跳转小程序自动登录
                authentication = authenticationManager
                        .authenticate(new MiniAuthenticationToken(username, userId));
            } else { // 正常登录
                // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
                authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            }
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
