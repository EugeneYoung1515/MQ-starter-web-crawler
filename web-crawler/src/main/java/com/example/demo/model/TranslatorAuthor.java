package com.example.demo.model;

public class TranslatorAuthor {
    private Integer translatorAuthorId;

    private String authorName;

    private String translatorAuthorIntro;

    public Integer getTranslatorAuthorId() {
        return translatorAuthorId;
    }

    public void setTranslatorAuthorId(Integer translatorAuthorId) {
        this.translatorAuthorId = translatorAuthorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public String getTranslatorAuthorIntro() {
        return translatorAuthorIntro;
    }

    public void setTranslatorAuthorIntro(String translatorAuthorIntro) {
        this.translatorAuthorIntro = translatorAuthorIntro == null ? null : translatorAuthorIntro.trim();
    }
}