package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user_role")
public class UserRoleDO {
    @Id
    private Integer id;
    private Integer userId;
    private Integer roleId;

    public UserRoleDO() {
    }

    public UserRoleDO(Integer id, Integer userId, Integer roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRoleDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
