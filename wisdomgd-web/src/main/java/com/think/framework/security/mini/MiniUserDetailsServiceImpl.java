package com.think.framework.security.mini;

import cn.hutool.core.util.ObjectUtil;
import com.think.common.enums.UserStatus;
import com.think.common.exception.BaseException;
import com.think.common.utils.StringUtils;
import com.think.datasource.system.entity.SysUser;
import com.think.datasource.system.service.ISysUserService;
import com.think.framework.security.LoginUser;
import com.think.framework.security.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MiniUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        SysUser user = userService.selectUserById(Long.parseLong(userId));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 已被删除.", user.getUserName());
            throw new UsernameNotFoundException("登录用户：" + user.getUserName() + " 不存在");
        } else if (Integer.compare(1,user.getDelFlag()) == 0) {
            log.info("登录用户：{} 已被删除.", user.getUserName());
            throw new BaseException("对不起，您的账号：" + user.getUserName() + " 已被删除");
        } else if (Integer.compare(1,user.getStatus()) == 0) {
            log.info("登录用户：{} 已被停用.", user.getUserName());
            throw new BaseException("对不起，您的账号：" + user.getUserName() + " 已停用");
        }
        return new LoginUser(user, permissionService.getMenuPermission(user));
    }
}
