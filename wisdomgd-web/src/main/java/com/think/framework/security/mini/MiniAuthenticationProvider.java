package com.think.framework.security.mini;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class MiniAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MiniUserDetailsServiceImpl miniUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        MiniAuthenticationToken authenticationToken = (MiniAuthenticationToken) authentication;
        String userId = authenticationToken.getUserId();
        UserDetails userDetails = miniUserDetailsService.loadUserByUsername(userId);

        MiniAuthenticationToken authenticationResult = new MiniAuthenticationToken(userDetails, userId, userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 判断 authentication 是不是 MiniAuthenticationToken 的子类或子接口
        return MiniAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
