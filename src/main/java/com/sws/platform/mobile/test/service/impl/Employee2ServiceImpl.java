package com.sws.platform.mobile.test.service.impl;

import com.sws.platform.mobile.common.util.StringUtil;
import com.sws.platform.mobile.test.dao.Employee2Dao;
import com.sws.platform.mobile.test.entity.Employee2;
import com.sws.platform.mobile.test.service.Employee2Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Administrator on 2016/2/18.
 */
@Service("employee2Service")
@Transactional
public class Employee2ServiceImpl implements Employee2Service {

    @Resource
    private Employee2Dao employee2Dao;

    public Page<Employee2> searchEmployees(String name, int pageIndex, int pageSize, String sortField, String sortOrder) {
        if (name == null) {
            name = "";
        }

        Sort sort;
        if (StringUtil.isNullOrEmpty(sortField) == false) {
            if ("desc".equals(sortOrder) == false) {
                sort = new Sort(Sort.Direction.ASC, sortField);
            } else {
                sort = new Sort(Sort.Direction.DESC, sortField);
            }
        } else {
            sort = new Sort(Sort.Direction.DESC, "createtime");
        }
        PageRequest p = new PageRequest(pageIndex, pageSize, sort);

        return employee2Dao.findByNameContains(name, p);
    }

    public void saveEmployeeByMiniuiDataGrid(String data) {
        ArrayList rows = (ArrayList) JSON.Decode(data);

        for (int i = 0, l = rows.size(); i < l; i++) {
            HashMap row = (HashMap) rows.get(i);

            String id = row.get("id") != null ? row.get("id").toString() : "";
            String state = row.get("_state") != null ? row.get("_state").toString() : "";
            if (state.equals("removed") || state.equals("deleted")) {//删除
                employee2Dao.delete(id);
            } else {
                if (state.equals("added") || id.equals(""))    //新增：id为空，或_state为added
                {
                    row.put("id", UUID.randomUUID().toString());
                    row.put("createtime", new Date());
                } else if (state.equals("modified") || state.equals(""))    //更新：_state为空，或modified
                {
                    //修改状态做些什么事情，比如记录修改时间
                }
                Employee2 employee = new Employee2(row);
//                Employee employee = ConvertUtil.mapToObject(row, Employee.class);
                employee2Dao.save(employee);
            }
        }
    }

}
