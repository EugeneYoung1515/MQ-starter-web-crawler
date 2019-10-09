package com.example.demo.model;

public class AlsoLike {
    private Integer alsoLikeId;

    private String alsoLikeName;

    public Integer getAlsoLikeId() {
        return alsoLikeId;
    }

    public void setAlsoLikeId(Integer alsoLikeId) {
        this.alsoLikeId = alsoLikeId;
    }

    public String getAlsoLikeName() {
        return alsoLikeName;
    }

    public void setAlsoLikeName(String alsoLikeName) {
        this.alsoLikeName = alsoLikeName == null ? null : alsoLikeName.trim();
    }
}