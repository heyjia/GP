package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "role")
public class RoleDO {
    @Id
    private int id;
    private String name;

    @Override
    public String toString() {
        return "RoleDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public RoleDO() {
    }

    public RoleDO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
