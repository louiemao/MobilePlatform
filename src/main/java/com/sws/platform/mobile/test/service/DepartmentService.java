package com.sws.platform.mobile.test.service;

import com.sws.platform.mobile.test.entity.Department;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface DepartmentService {
    Iterable<Department> findAll();
}
