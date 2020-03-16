package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user")
public class UserDO {
    @Id
    private Integer id;

    private String userId;
    private String name;
    private String password;
    private Integer sex;
    private Integer age;
    private String email;
    private Date birthday;
    private String address;
    private Date crtTime;
    private Date updtTime;

    public UserDO() {
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Date getUpdtTime() {
        return updtTime;
    }

    public void setUpdtTime(Date updtTime) {
        this.updtTime = updtTime;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public UserDO(Integer id, String userId, String name, String password, Integer sex, Integer age, String email, Date birthday, String address, Date crtTime, Date updtTime) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
        this.crtTime = crtTime;
        this.updtTime = updtTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", crtTime=" + crtTime +
                ", updtTime=" + updtTime +
                '}';
    }
}
