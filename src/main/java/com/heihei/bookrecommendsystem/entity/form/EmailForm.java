package com.heihei.bookrecommendsystem.entity.form;

public class EmailForm {
    private String userName;
    private String email;

    public EmailForm() {
    }

    public EmailForm(String userName, String email) {
        this.userName = userName;
        this.email = email;
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

    @Override
    public String toString() {
        return "EmailForm{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
