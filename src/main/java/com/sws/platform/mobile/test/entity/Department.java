package com.sws.platform.mobile.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2016/2/29.
 */
@JsonIgnoreProperties(value = "employee2s")
@Entity
@Table(name = "t_department")
public class Department {
    @Id
    private String id;
    private String name;
    @OneToMany(mappedBy = "department", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Employee2> employee2s;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee2> getEmployee2s() {
        return employee2s;
    }

    public void setEmployee2s(List<Employee2> employee2s) {
        this.employee2s = employee2s;
    }
}
