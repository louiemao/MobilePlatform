package com.sws.platform.mobile.security.realm;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by MaoLiang on 2016/5/3.
 */
public class CPNoPwdToken implements RememberMeAuthenticationToken {
    private String userCd;

    public CPNoPwdToken() {
    }

    public CPNoPwdToken(String userCd) {
        this.userCd = userCd;
    }

    @Override
    public Object getPrincipal() {
        return userCd;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }
}
