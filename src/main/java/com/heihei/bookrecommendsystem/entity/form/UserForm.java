package com.heihei.bookrecommendsystem.entity.form;

public class UserForm {
    private String userId;
    private String userName;
    private String password;
    private String checkCode;
    private String email;

    public UserForm() {
    }

    public UserForm(String userId, String userName, String password, String checkCode, String email) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.checkCode = checkCode;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", checkCode='" + checkCode + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
