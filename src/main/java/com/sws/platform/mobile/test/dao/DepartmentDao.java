package com.sws.platform.mobile.test.dao;

import com.sws.platform.mobile.test.entity.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/2/29.
 */
@Repository
public interface DepartmentDao extends CrudRepository<Department, String> {

}