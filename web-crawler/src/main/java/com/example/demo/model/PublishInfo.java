package com.example.demo.model;

import java.math.BigDecimal;
import java.util.Date;

public class PublishInfo {
    private Integer publishId;

    private Integer bookId;

    private String bookTitle;

    private String seriesName;

    private Date bookDate;

    private String bookNumber;

    private BigDecimal price;

    private Integer pageNum;

    private String print;

    private String bookSize;

    private String bookStatus;

    private String originalTitle;

    private String originalBookNumber;

    private String amazonLink;

    private String questionContact;

    public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle == null ? null : bookTitle.trim();
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName == null ? null : seriesName.trim();
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber == null ? null : bookNumber.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print == null ? null : print.trim();
    }

    public String getBookSize() {
        return bookSize;
    }

    public void setBookSize(String bookSize) {
        this.bookSize = bookSize == null ? null : bookSize.trim();
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus == null ? null : bookStatus.trim();
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle == null ? null : originalTitle.trim();
    }

    public String getOriginalBookNumber() {
        return originalBookNumber;
    }

    public void setOriginalBookNumber(String originalBookNumber) {
        this.originalBookNumber = originalBookNumber == null ? null : originalBookNumber.trim();
    }

    public String getAmazonLink() {
        return amazonLink;
    }

    public void setAmazonLink(String amazonLink) {
        this.amazonLink = amazonLink == null ? null : amazonLink.trim();
    }

    public String getQuestionContact() {
        return questionContact;
    }

    public void setQuestionContact(String questionContact) {
        this.questionContact = questionContact == null ? null : questionContact.trim();
    }
}