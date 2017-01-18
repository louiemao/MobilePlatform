package com.sws.platform.mobile.test.controller;

import com.sws.platform.mobile.test.entity.Department;
import com.sws.platform.mobile.test.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/2/29.
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    @RequestMapping("/list")
    @ResponseBody
    public Iterable<Department> list() {
        return departmentService.findAll();
    }
}
