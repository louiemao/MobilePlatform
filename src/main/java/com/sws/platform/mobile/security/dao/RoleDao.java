package com.sws.platform.mobile.security.dao;

import com.sws.platform.mobile.security.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Louie on 2016/12/2.
 */
public interface RoleDao extends JpaRepository<RoleEntity, String> {
}
