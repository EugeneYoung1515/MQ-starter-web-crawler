package com.example.demo.model;

public class CategoryBook {
    private Integer categoryId;

    private Integer bookId;

    private Integer actualGrade;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getActualGrade() {
        return actualGrade;
    }

    public void setActualGrade(Integer actualGrade) {
        this.actualGrade = actualGrade;
    }
}