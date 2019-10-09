package com.example.demo.model;

public class Booktag {
    private Integer booktagId;

    private String booktagName;

    public Integer getBooktagId() {
        return booktagId;
    }

    public void setBooktagId(Integer booktagId) {
        this.booktagId = booktagId;
    }

    public String getBooktagName() {
        return booktagName;
    }

    public void setBooktagName(String booktagName) {
        this.booktagName = booktagName == null ? null : booktagName.trim();
    }
}