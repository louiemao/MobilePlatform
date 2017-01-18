package com.sws.platform.mobile.test.dao;

import com.sws.platform.mobile.test.entity.Employee2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/2/29.
 */
@Repository
public interface Employee2Dao extends PagingAndSortingRepository<Employee2, String> {
    Page<Employee2> findByNameContains(String name, Pageable p);
}