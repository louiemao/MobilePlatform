package com.sws.platform.mobile.test.service;

import com.sws.platform.mobile.test.entity.Employee2;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface Employee2Service {
    Page<Employee2> searchEmployees(String name, int pageIndex, int pageSize, String sortField, String sortOrder);
    void saveEmployeeByMiniuiDataGrid(String data);
}
