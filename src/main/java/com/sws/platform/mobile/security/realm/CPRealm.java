//package com.sws.platform.mobile.security.realm;
//
//import com.sws.platform.mobile.external.dto.CPUser;
//import com.sws.platform.mobile.security.service.RestService;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * Created by Louie on 2015/7/2.
// */
//public class CPRealm extends AuthorizingRealm {
//    private Logger logger = LogManager.getLogger(CPRealm.class.getName());
//
//    @Autowired
//    private RestService restService;
//
//    public CPRealm() {
//        setName("CPRealm");
//    }
//
//    /**
//     * 权限认证
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        //获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
//        String id = (String) super.getAvailablePrincipal(principals);
//        logger.info("认证用户权限：" + id);
//        return restService.getAuthorizationInfo(id);
//    }
//
//    /**
//     * 登录认证;
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(
//            AuthenticationToken authcToken) throws AuthenticationException {
//        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//        logger.info("登录认证：" + token.getUsername());
//        CPUser user = restService.login(token.getUsername(), String.valueOf(token.getPassword()));
//        if (user != null) {
//            //这里放的信息，就是以后SecurityUtils.getSubject().getPrincipal()获取的信息，这里放的是用户编号
//            return new SimpleAuthenticationInfo(user.userCd, token.getPassword(), getName());
//        } else {
//            return null;
//        }
//    }
//
//}
