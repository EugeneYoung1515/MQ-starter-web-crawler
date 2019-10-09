package com.example.demo.model;

public class Catalog {
    private Integer chapterId;

    private Integer bookId;

    private String chapterIntro;

    private String chapterLink;

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getChapterIntro() {
        return chapterIntro;
    }

    public void setChapterIntro(String chapterIntro) {
        this.chapterIntro = chapterIntro == null ? null : chapterIntro.trim();
    }

    public String getChapterLink() {
        return chapterLink;
    }

    public void setChapterLink(String chapterLink) {
        this.chapterLink = chapterLink == null ? null : chapterLink.trim();
    }
}