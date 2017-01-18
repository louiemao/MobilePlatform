package com.sws.platform.mobile.security.realm;

import com.sws.platform.mobile.security.service.RestService;
import com.sws.platform.mobile.security.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Louie on 2016/3/8.
 */
public class MyCasRealm extends CasRealm {
    private Logger logger = LogManager.getLogger(MyCasRealm.class.getName());

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
        String id = (String) super.getAvailablePrincipal(principals);
        logger.info("认证用户权限：" + id);
        return userService.getAuthorizationInfo(id);
    }
}
