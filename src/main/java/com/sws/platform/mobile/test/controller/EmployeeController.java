package com.sws.platform.mobile.test.controller;

import com.sws.platform.mobile.test.entity.Employee;
import com.sws.platform.mobile.test.entity.Employee2;
import com.sws.platform.mobile.test.service.Employee2Service;
import com.sws.platform.mobile.test.service.EmployeeJdbcService;
import com.sws.platform.mobile.test.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/2/18.
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;
    @Resource
    private EmployeeJdbcService employeeJdbcService;
    @Resource
    private Employee2Service employee2Service;

    private static Logger logger = LogManager.getLogger(EmployeeController.class.getName());

    @RequestMapping("/SearchEmployees")
    @ResponseBody
    public Page<Employee> SearchEmployees(String key, String pageIndex, String pageSize, String sortField, String sortOrder) {
        //查询条件
        //String key = request.getParameter("key");
        //分页
        int index = Integer.parseInt(pageIndex);
        int size = Integer.parseInt(pageSize);
        //字段排序
        //String sortField = request.getParameter("sortField");
        //String sortOrder = request.getParameter("sortOrder");

        Page<Employee> page = employeeService.searchEmployees(key, index, size, sortField, sortOrder);
        return page;
    }

    @RequestMapping("/SaveEmployees")
    @ResponseBody
    public void SaveEmployees(String data) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (data == null || "".equals(data)) {
            return;
        }
        employeeService.saveEmployeeByMiniuiDataGrid(data);
    }


    @RequestMapping("/SearchEmployeesByJdbc")
    @ResponseBody
    public HashMap SearchEmployeesByJdbc(String key, String pageIndex, String pageSize, String sortField, String sortOrder) {
        //分页
        int index = Integer.parseInt(pageIndex);
        int size = Integer.parseInt(pageSize);

        return employeeJdbcService.searchEmployeesByJdbc(key, index, size, sortField, sortOrder);
    }

    @RequestMapping("/SaveEmployeesByJdbc")
    @ResponseBody
    public void SaveEmployeesByJdbc(String data) {
        if (data == null || "".equals(data)) {
            return;
        }
        employeeJdbcService.saveEmployeeByMiniuiDataGridByJdbc(data);
    }


    @RequestMapping("/SearchEmployee2s")
    @ResponseBody
    public Page<Employee2> SearchEmployee2s(String key, String pageIndex, String pageSize, String sortField, String sortOrder) {
        //分页
        int index = Integer.parseInt(pageIndex);
        int size = Integer.parseInt(pageSize);

        Page<Employee2> page = employee2Service.searchEmployees(key, index, size, sortField, sortOrder);
        return page;
    }

    @RequestMapping("/SaveEmployee2s")
    @ResponseBody
    public void SaveEmployee2s(String data) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (data == null || "".equals(data)) {
            return;
        }
        employee2Service.saveEmployeeByMiniuiDataGrid(data);
    }

}
