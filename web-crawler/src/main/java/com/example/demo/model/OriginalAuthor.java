package com.example.demo.model;

public class OriginalAuthor {
    private Integer originalAuthorId;

    private String authorName;

    private String originalAuthorIntro;

    public Integer getOriginalAuthorId() {
        return originalAuthorId;
    }

    public void setOriginalAuthorId(Integer originalAuthorId) {
        this.originalAuthorId = originalAuthorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public String getOriginalAuthorIntro() {
        return originalAuthorIntro;
    }

    public void setOriginalAuthorIntro(String originalAuthorIntro) {
        this.originalAuthorIntro = originalAuthorIntro == null ? null : originalAuthorIntro.trim();
    }
}