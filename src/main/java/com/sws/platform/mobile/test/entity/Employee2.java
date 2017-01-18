package com.sws.platform.mobile.test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sws.platform.mobile.common.util.ConvertUtil;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/2/18.
 */
@JsonIgnoreProperties(value = {"department.employee2s"})
@Entity
@Table(name = "t_employee")
public class Employee2 {
    @Id
    private String id;
    private String loginname;
    private String name;
    private int age;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp birthday;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id")
    private Department department;
    private String position;
    private int gender;
    private int married;
    private String salary;
    private String educational;
    private String country;
    private String city;
    private String remarks;
    private String school;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createtime;
    private String email;


    public Employee2() {
    }

    public Employee2(HashMap employee) {
        this.id = ConvertUtil.ToString(employee.get("id"));
        this.loginname = ConvertUtil.ToString(employee.get("loginname"));
        this.name = ConvertUtil.ToString(employee.get("name"));
        this.age = ConvertUtil.ToInt(employee.get("age"));
        this.birthday = ConvertUtil.ToDate(employee.get("birthday"));
        if (this.department == null) {
            this.department = new Department();
        }
        this.department.setId(ConvertUtil.ToString(employee.get("dept_id")));
        this.position = ConvertUtil.ToString(employee.get("position"));
        this.gender = ConvertUtil.ToInt(employee.get("gender"));
        this.married = ConvertUtil.ToInt(employee.get("married"));
        this.salary = ConvertUtil.ToString(employee.get("salary"));
        this.educational = ConvertUtil.ToString(employee.get("educational"));
        this.country = ConvertUtil.ToString(employee.get("country"));
        this.city = ConvertUtil.ToString(employee.get("city"));
        this.remarks = ConvertUtil.ToString(employee.get("remarks"));
        this.school = ConvertUtil.ToString(employee.get("school"));
        this.createtime = ConvertUtil.ToDate(employee.get("createtime"));
        this.email = ConvertUtil.ToString(employee.get("email"));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getMarried() {
        return married;
    }

    public void setMarried(int married) {
        this.married = married;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getEducational() {
        return educational;
    }

    public void setEducational(String educational) {
        this.educational = educational;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee2) {
            return ((Employee2) obj).id.equals(this.id);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
