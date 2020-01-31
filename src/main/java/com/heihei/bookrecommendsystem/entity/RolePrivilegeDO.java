package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "role_privilege")
public class RolePrivilegeDO {
    @Id
    private int id;
    private int roleId;
    private int privilegeId;

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

    public RolePrivilegeDO(int id, int roleId, int privilegeId) {
        this.id = id;
        this.roleId = roleId;
        this.privilegeId = privilegeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(int privilegeId) {
        this.privilegeId = privilegeId;
    }
}
