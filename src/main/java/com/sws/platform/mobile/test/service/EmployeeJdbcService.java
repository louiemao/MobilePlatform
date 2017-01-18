package com.sws.platform.mobile.test.service;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/2/25.
 */
public interface EmployeeJdbcService {
    //JdbcTemplate
    HashMap searchEmployeesByJdbc(String name, int pageIndex, int pageSize, String sortField, String sortOrder);
    void saveEmployeeByMiniuiDataGridByJdbc(String data);
}
