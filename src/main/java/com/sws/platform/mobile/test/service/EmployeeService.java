package com.sws.platform.mobile.test.service;

import com.sws.platform.mobile.test.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * Created by Administrator on 2016/2/19.
 */
public interface EmployeeService {
    Page<Employee> searchEmployees(String name, int pageIndex, int pageSize, String sortField, String sortOrder);
    void saveEmployeeByMiniuiDataGrid(String data);

    void save(Map map);
}
