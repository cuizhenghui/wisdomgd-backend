package com.think.framework.security.service;

import com.think.common.enums.UserStatus;
import com.think.common.exception.BaseException;
import com.think.common.utils.StringUtils;
import com.think.datasource.system.entity.SysUser;
import com.think.datasource.system.service.ISysUserService;
import com.think.framework.security.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证处理
 *
 * @author Gary
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        } else if (Integer.compare(1,user.getDelFlag()) == 0) {
            log.info("登录用户：{} 已被删除.", user.getUserName());
            throw new BaseException("对不起，您的账号：" + user.getUserName() + " 已被删除");
        } else if (Integer.compare(1,user.getStatus()) == 0) {
            log.info("登录用户：{} 已被停用.", user.getUserName());
            throw new BaseException("对不起，您的账号：" + user.getUserName() + " 已停用");
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user, permissionService.getMenuPermission(user));
    }
}
