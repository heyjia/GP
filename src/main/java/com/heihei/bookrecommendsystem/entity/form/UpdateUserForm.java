package com.heihei.bookrecommendsystem.entity.form;

import java.util.Date;

public class UpdateUserForm {
    private String userId;
    private String userName;
    private String email;
    private Integer sex;
    private String birthday;
    private Integer age;
    private String address;

    public UpdateUserForm() {
    }

    public UpdateUserForm(String userId, String userName, String email, Integer sex, String birthday, Integer age, String address) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
        this.age = age;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "updateUserForm{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
