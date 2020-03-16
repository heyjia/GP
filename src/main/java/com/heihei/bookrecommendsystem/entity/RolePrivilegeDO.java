package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "role_privilege")
public class RolePrivilegeDO {
    @Id
    private Integer id;
    private Integer roleId;
    private Integer privilegeId;

    @Override
    public String toString() {
        return "RolePrivilegeDO{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", privilegeId=" + privilegeId +
                '}';
    }

    public RolePrivilegeDO() {
    }

    public RolePrivilegeDO(Integer id, Integer roleId, Integer privilegeId) {
        this.id = id;
        this.roleId = roleId;
        this.privilegeId = privilegeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }
}
