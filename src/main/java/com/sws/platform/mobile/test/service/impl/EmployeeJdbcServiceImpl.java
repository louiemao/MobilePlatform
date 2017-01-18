package com.sws.platform.mobile.test.service.impl;

import com.sws.platform.mobile.common.util.StringUtil;
import com.sws.platform.mobile.common.util.ConvertUtil;
import com.sws.platform.mobile.test.service.EmployeeJdbcService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/25.
 */
@Service("employeeJdbcService")
@Transactional("jdbcTransactionManager")
public class EmployeeJdbcServiceImpl implements EmployeeJdbcService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public HashMap searchEmployeesByJdbc(String name, int pageIndex, int pageSize, String sortField, String sortOrder) {
        if (name == null) name = "";

        String sql =
                "select a.*, b.name as dept_name, c.name as position_name, d.name as educational_name \n"
                        + "from t_employee a \n"
                        + "left join t_department b \n"
                        + "on a.dept_id = b.id \n"
                        + "left join t_position c \n"
                        + "on a.position = c.id \n"
                        + "left join t_educational d \n"
                        + "on a.educational = d.id \n"
                        + "where a.name like '%" + name + "%' \n";

        if (StringUtil.isNullOrEmpty(sortField) == false) {
            if ("desc".equals(sortOrder) == false) sortOrder = "asc";
            sql += " order by " + sortField + " " + sortOrder;
        } else {
            sql += " order by createtime desc";
        }


//        List dataAll = jdbcTemplate.query(sql, new MiniUIRowMapper());


        //本来直接使用这个方法是最简单的，但是有两个日期，序列号Json时会变为毫秒值
        List dataAll = jdbcTemplate.queryForList(sql);

        //再一般的方式，自己一个一个的映射赋值
//        List dataAll = jdbcTemplate.query(sql, new RowMapper<Map>() {
//            @Override
//            public Map mapRow(ResultSet resultSet, int i) throws SQLException {
//                ResultSetMetaData md = resultSet.getMetaData();
//                int columnCount = md.getColumnCount();
//                Map rowData = new HashMap(columnCount);
//                rowData.put("id",resultSet.getString("id"));
//                return rowData;
//            }
//        });

        ArrayList data = new ArrayList();
        int start = pageIndex * pageSize, end = start + pageSize;

        for (int i = 0, l = dataAll.size(); i < l; i++) {
            HashMap record = (HashMap) dataAll.get(i);
            if (record == null) continue;
            if (start <= i && i < end) {
                data.add(record);
            }
        }

        HashMap result = new HashMap();
        result.put("data", data);
        result.put("total", dataAll.size());

        return result;
    }

    @Override
    public void saveEmployeeByMiniuiDataGridByJdbc(String data) {
        ArrayList rows = (ArrayList) JSON.Decode(data);

        for (int i = 0, l = rows.size(); i < l; i++) {
            HashMap row = (HashMap) rows.get(i);

            String id = row.get("id") != null ? row.get("id").toString() : "";
            String state = row.get("_state") != null ? row.get("_state").toString() : "";
            if (state.equals("added") || id.equals(""))    //新增：id为空，或_state为added
            {
                row.put("createtime", new Date());
                InsertEmployee(row);
            } else if (state.equals("removed") || state.equals("deleted")) {
                DeleteEmployee(id);
            } else if (state.equals("modified") || state.equals(""))    //更新：_state为空，或modified
            {
                UpdateEmployee(row);
            }
        }
    }

    private String InsertEmployee(final Map user) {
        String id = (user.get("id") == null || user.get("id").toString().equals("")) ? UUID.randomUUID().toString() : user.get("id").toString();
        user.put("id", id);

        if (user.get("name") == null) user.put("name", "");
        if (StringUtil.isNullOrEmpty(user.get("gender"))) user.put("gender", 0);

        String sql = "insert into t_employee (id, loginname, name, age, married, gender, birthday, country, city, dept_id, position, createtime, salary, educational, school, email, remarks)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, ConvertUtil.ToString(user.get("id")));
                preparedStatement.setString(2, ConvertUtil.ToString(user.get("loginname")));
                preparedStatement.setString(3, ConvertUtil.ToString(user.get("name")));
                preparedStatement.setInt(4, ConvertUtil.ToInt(user.get("age")));
                preparedStatement.setInt(5, ConvertUtil.ToInt(user.get("married")));
                preparedStatement.setInt(6, ConvertUtil.ToInt(user.get("gender")));
                preparedStatement.setTimestamp(7, ConvertUtil.ToDate(user.get("birthday")));
                preparedStatement.setString(8, ConvertUtil.ToString(user.get("country")));
                preparedStatement.setString(9, ConvertUtil.ToString(user.get("city")));
                preparedStatement.setString(10, ConvertUtil.ToString(user.get("dept_id")));
                preparedStatement.setString(11, ConvertUtil.ToString(user.get("position")));
                preparedStatement.setTimestamp(12, ConvertUtil.ToDate(user.get("createtime")));
                preparedStatement.setString(13, ConvertUtil.ToString(user.get("salary")));
                preparedStatement.setString(14, ConvertUtil.ToString(user.get("educational")));
                preparedStatement.setString(15, ConvertUtil.ToString(user.get("school")));
                preparedStatement.setString(16, ConvertUtil.ToString(user.get("email")));
                preparedStatement.setString(17, ConvertUtil.ToString(user.get("remarks")));
            }
        });
        return id;
    }

    private void DeleteEmployee(String userId) {
        String sql = "delete from t_employee where id = \"" + userId + "\"";
        jdbcTemplate.update(sql);
    }

    private void UpdateEmployee(HashMap user) {
        Map db_user = GetEmployee(user.get("id").toString());

        Iterator iter = user.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();

            db_user.put(key, val);
        }

        DeleteEmployee(user.get("id").toString());
        InsertEmployee(db_user);
    }

    private Map GetEmployee(String id) {
        String sql = "select * from t_employee where id = '" + id + "'";
        return jdbcTemplate.queryForMap(sql);
    }

    private class MiniUIRowMapper implements RowMapper<Map> {
        private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public Map mapRow(ResultSet resultSet, int rowNom) throws SQLException {
            ResultSetMetaData md = resultSet.getMetaData();
            int columnCount = md.getColumnCount();
            Map rowData = new HashMap(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                Object v = resultSet.getObject(i);
                if (v != null && (v.getClass() == Date.class || v.getClass() == java.sql.Date.class || v.getClass() == Timestamp.class)) {
                    //把时间类型改为字符串，不然Json转换时会返回毫秒值
                    Timestamp ts = resultSet.getTimestamp(i);
                    v = simpleDateFormat.format(new Date(ts.getTime()));
                } else if (v != null && v.getClass() == Clob.class) {
                    v = ((Clob) v).getSubString(1, (int) ((Clob) v).length());
                }
                rowData.put(md.getColumnLabel(i), v);
            }
            return rowData;
        }
    }
}
