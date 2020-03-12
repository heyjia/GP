package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "book_class")
public class BookClassDO {
    @Id
    private int id;

    private String name;

    private String count;

    private float avgRateValue;

    public BookClassDO() {
    }

    public BookClassDO(int id, String name, String count, float avgRateValue) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.avgRateValue = avgRateValue;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public float getAvgRateValue() {
        return avgRateValue;
    }

    public void setAvgRateValue(float avgRateValue) {
        this.avgRateValue = avgRateValue;
    }

    @Override
    public String toString() {
        return "BookClassDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count='" + count + '\'' +
                ", avgRateValue=" + avgRateValue +
                '}';
    }
}
