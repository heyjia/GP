package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "email")
public class EmailDO {
    @Id
    private Integer id;
    private String userId;
    private String emailAddress;
    private String code;
    private Date UpdtTime;

    public EmailDO() {
    }

    public EmailDO(Integer id, String userId, String emailAddress, String code, Date updtTime) {
        this.id = id;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
                ", userId='" + userId + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", code='" + code + '\'' +
                ", UpdtTime=" + UpdtTime +
                '}';
    }
}
