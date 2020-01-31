package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "privilege")
public class PrivilegeDO {
    @Id
    private int id;
    private String name;
    private String url;

    public PrivilegeDO() {
    }

    public PrivilegeDO(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PrivilegeDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
