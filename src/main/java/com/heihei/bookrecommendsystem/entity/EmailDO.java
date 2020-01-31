package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "email")
public class EmailDO {
    @Id
    private Integer id;
    private String userName;
    private String emailAddress;
    private String code;
    private Date UpdtTime;

    public EmailDO() {
    }

    public EmailDO(Integer id, String userName, String emailAddress, String code, Date updtTime) {
        this.id = id;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.code = code;
        UpdtTime = updtTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getUpdtTime() {
        return UpdtTime;
    }

    public void setUpdtTime(Date updtTime) {
        UpdtTime = updtTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "EmailDO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", code='" + code + '\'' +
                ", UpdtTime=" + UpdtTime +
                '}';
    }
}
