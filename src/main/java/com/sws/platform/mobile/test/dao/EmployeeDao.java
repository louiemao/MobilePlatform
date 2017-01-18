package com.sws.platform.mobile.test.dao;

import com.sws.platform.mobile.test.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

/**
 * Created by Administrator on 2016/2/18.
 */
@Repository
public interface EmployeeDao extends PagingAndSortingRepository<Employee, String> {
    //这里定义的方法名是有讲究的，Spring Data会对这些方法进行实现，不需要自己实现
    Page<Employee> findByNameContains(String name, Pageable p);
}
