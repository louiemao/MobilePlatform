package com.sws.platform.mobile.security.service;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Louie on 2016/12/2.
 */
@Service
@Transactional
public class UserService {
    public SimpleAuthorizationInfo getAuthorizationInfo(String username) {
        return null;
    }
}
