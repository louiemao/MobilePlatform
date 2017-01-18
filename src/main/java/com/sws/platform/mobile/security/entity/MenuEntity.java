package com.sws.platform.mobile.security.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Louie on 2016/12/2.
 */
@Entity
@Table(name = "menu")
public class MenuEntity {
    private String id;
    private String label;
    private String link;
    private MenuEntity parent;
    private Integer sort;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @OneToOne
    @JoinColumn(name = "parent_id")
    public MenuEntity getParent() {
        return parent;
    }

    public void setParent(MenuEntity parent) {
        this.parent = parent;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
