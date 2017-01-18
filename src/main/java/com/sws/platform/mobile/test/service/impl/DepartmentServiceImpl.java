package com.sws.platform.mobile.test.service.impl;

import com.sws.platform.mobile.test.dao.DepartmentDao;
import com.sws.platform.mobile.test.entity.Department;
import com.sws.platform.mobile.test.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/2/29.
 */
@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentDao departmentDao;

    @Override
    public Iterable<Department> findAll() {
        return departmentDao.findAll();
    }
}
