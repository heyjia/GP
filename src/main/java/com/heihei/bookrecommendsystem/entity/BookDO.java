package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "book")
public class BookDO {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "ISBN")
    private String ISBN;

    private String name;

    private String author;

    private String translator;

    private String category;

    private String publishHourse;

    private String provider;

    private Integer wordsCount;

    private String imgAddr;

    private String infoAddr;

    private String scoreAddr;

    private String bookInfo;

    private Double avgRatingVal;

    private Integer classifyMainId;

    private Integer rateCount;

    public BookDO() {
    }

    public BookDO(Integer id, String ISBN, String name, String author, String translator, String category, String publishHourse, String provider, Integer wordsCount, String imgAddr, String infoAddr, String scoreAddr, String bookInfo, Double avgRatingVal, Integer classifyMainId, Integer rateCount) {
        this.id = id;
        this.ISBN = ISBN;
        this.name = name;
        this.author = author;
        this.translator = translator;
        this.category = category;
        this.publishHourse = publishHourse;
        this.provider = provider;
        this.wordsCount = wordsCount;
        this.imgAddr = imgAddr;
        this.infoAddr = infoAddr;
        this.scoreAddr = scoreAddr;
        this.bookInfo = bookInfo;
        this.avgRatingVal = avgRatingVal;
        this.classifyMainId = classifyMainId;
        this.rateCount = rateCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublishHourse() {
        return publishHourse;
    }

    public void setPublishHourse(String publishHourse) {
        this.publishHourse = publishHourse;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Integer getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(Integer wordsCount) {
        this.wordsCount = wordsCount;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getInfoAddr() {
        return infoAddr;
    }

    public void setInfoAddr(String infoAddr) {
        this.infoAddr = infoAddr;
    }

    public String getScoreAddr() {
        return scoreAddr;
    }

    public void setScoreAddr(String scoreAddr) {
        this.scoreAddr = scoreAddr;
    }

    public String getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(String bookInfo) {
        this.bookInfo = bookInfo;
    }

    public Double getAvgRatingVal() {
        return avgRatingVal;
    }

    public void setAvgRatingVal(Double avgRatingVal) {
        this.avgRatingVal = avgRatingVal;
    }

    public Integer getClassifyMainId() {
        return classifyMainId;
    }

    public void setClassifyMainId(Integer classifyMainId) {
        this.classifyMainId = classifyMainId;
    }

    public Integer getRateCount() {
        return rateCount;
    }

    public void setRateCount(Integer rateCount) {
        this.rateCount = rateCount;
    }

    @Override
    public String toString() {
        return "BookDO{" +
                "id=" + id +
                ", ISBN='" + ISBN + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", translator='" + translator + '\'' +
                ", category='" + category + '\'' +
                ", publishHourse='" + publishHourse + '\'' +
                ", provider='" + provider + '\'' +
                ", wordsCount=" + wordsCount +
                ", imgAddr='" + imgAddr + '\'' +
                ", infoAddr='" + infoAddr + '\'' +
                ", scoreAddr='" + scoreAddr + '\'' +
                ", bookInfo='" + bookInfo + '\'' +
                ", avgRatingVal=" + avgRatingVal +
                ", classifyMainId=" + classifyMainId +
                ", rateCount=" + rateCount +
                '}';
    }
}
