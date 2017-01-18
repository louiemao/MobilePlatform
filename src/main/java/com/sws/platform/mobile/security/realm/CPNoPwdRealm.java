package com.sws.platform.mobile.security.realm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;

/**
 * Created by MaoLiang on 2016/5/3.
 */
public class CPNoPwdRealm extends CPRealm {
    protected static Logger logger = LogManager.getLogger(CPNoPwdRealm.class.getName());

    public CPNoPwdRealm() {
        setName("CPNoPwdRealm");
        //设置无需凭证，因为从sso认证后才会有用户名
        setCredentialsMatcher(new AllowAllCredentialsMatcher());
        //设置token的类型
        setAuthenticationTokenClass(CPNoPwdToken.class);
    }

    /**
     * 登录认证;
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        if (!(authcToken instanceof CPNoPwdToken)) {
            return null;
        }
        CPNoPwdToken token = (CPNoPwdToken) authcToken;
        logger.info("登录认证：" + token.getPrincipal());
//        if (!"oa".equals(token.getCredentials())
//                && !"bpm".equals(token.getCredentials())) {
//            return null;
//        }
//        CPUser user = cpService.getUserInfo(String.valueOf(token.getPrincipal()));
//        if (user == null || !StringUtils.hasText(user.getUserId())) {
//            return null;
//        } else {
//            //这里放的信息，就是以后SecurityUtils.getSubject().getPrincipal()获取的信息，这里放的是用户编号
//            return new SimpleAuthenticationInfo(user, token.getCredentials(), getName());
//        }
        return null;
    }
}
