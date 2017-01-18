package com.sws.platform.mobile.security.dao;

import com.sws.platform.mobile.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Louie on 2016/12/2.
 */
public interface UserDao extends JpaRepository<UserEntity, String> {
}
