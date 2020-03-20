package com.heihei.bookrecommendsystem.entity.vo;

import java.util.Date;

public class UserRatingBookDetailVO {
    private String userId;

    private Integer bookId;

    private Float score;

    private String describeInfo;

    private Date updtTime;

    private String userName;

    public UserRatingBookDetailVO() {
    }

    public UserRatingBookDetailVO(String userId, Integer bookId, Float score, String describeInfo, Date updtTime, String userName) {
        this.userId = userId;
        this.bookId = bookId;
        this.score = score;
        this.describeInfo = describeInfo;
        this.updtTime = updtTime;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getDescribeInfo() {
        return describeInfo;
    }

    public void setDescribeInfo(String describeInfo) {
        this.describeInfo = describeInfo;
    }

    public Date getUpdtTime() {
        return updtTime;
    }

    public void setUpdtTime(Date updtTime) {
        this.updtTime = updtTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserRatingBookDetailVO{" +
                "userId='" + userId + '\'' +
                ", bookId=" + bookId +
                ", score=" + score +
                ", describeInfo='" + describeInfo + '\'' +
                ", updtTime=" + updtTime +
                ", userName='" + userName + '\'' +
                '}';
    }
}
