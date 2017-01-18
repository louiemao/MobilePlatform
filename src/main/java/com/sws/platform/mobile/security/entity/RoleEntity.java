package com.sws.platform.mobile.security.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Louie on 2016/12/2.
 */
@Entity
@Table(name = "role")
public class RoleEntity {
    //角色Id
    private String id;
    //角色名称
    private String name;
    //角色描述
    private String description;
    private List<PermissionEntity> permissions;
    private List<UserEntity> users;
    private List<MenuEntity> menus;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name", unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @ManyToMany(mappedBy = "roles")
    public List<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionEntity> permissions) {
        this.permissions = permissions;
    }

    @ManyToMany(mappedBy = "roles")
    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    @ManyToMany
    @JoinTable(
            name = "menu_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = {@JoinColumn(name = "menu_id")}
    )
    public List<MenuEntity> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuEntity> menus) {
        this.menus = menus;
    }
}
