package com.example.demo.model;

import java.util.Date;
import java.util.List;

public class Condition {

    private Integer sortNew;
    private Integer sortHot;
    private Integer sortVote;

    private Integer tabBook;
    private Integer tabEbook;
    private Integer tabFree;
    private Integer tabTranslator;
    private Integer tabSoon;
    private Integer ninetyVote;
    private Date date;
    private Integer tabFav;
    private List<Integer> favList;

    private Integer grade1;
    private Integer grade2;
    private Integer grade3;

    public Integer getSortNew() {
        return sortNew;
    }

    public void setSortNew(Integer sortNew) {
        this.sortNew = sortNew;
    }

    public Integer getSortHot() {
        return sortHot;
    }

    public void setSortHot(Integer sortHot) {
        this.sortHot = sortHot;
    }

    public Integer getSortVote() {
        return sortVote;
    }

    public void setSortVote(Integer sortVote) {
        this.sortVote = sortVote;
    }

    public Integer getTabBook() {
        return tabBook;
    }

    public void setTabBook(Integer tabBook) {
        this.tabBook = tabBook;
    }

    public Integer getTabEbook() {
        return tabEbook;
    }

    public void setTabEbook(Integer tabEbook) {
        this.tabEbook = tabEbook;
    }

    public Integer getTabFree() {
        return tabFree;
    }

    public void setTabFree(Integer tabFree) {
        this.tabFree = tabFree;
    }

    public Integer getTabTranslator() {
        return tabTranslator;
    }

    public void setTabTranslator(Integer tabTranslator) {
        this.tabTranslator = tabTranslator;
    }

    public Integer getTabSoon() {
        return tabSoon;
    }

    public void setTabSoon(Integer tabSoon) {
        this.tabSoon = tabSoon;
    }

    public Integer getNinetyVote() {
        return ninetyVote;
    }

    public void setNinetyVote(Integer ninetyVote) {
        this.ninetyVote = ninetyVote;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getGrade1() {
        return grade1;
    }

    public void setGrade1(Integer grade1) {
        this.grade1 = grade1;
    }

    public Integer getGrade2() {
        return grade2;
    }

    public void setGrade2(Integer grade2) {
        this.grade2 = grade2;
    }

    public Integer getGrade3() {
        return grade3;
    }

    public void setGrade3(Integer grade3) {
        this.grade3 = grade3;
    }

    public Integer getTabFav() {
        return tabFav;
    }

    public void setTabFav(Integer tabFav) {
        this.tabFav = tabFav;
    }

    public List<Integer> getFavList() {
        return favList;
    }

    public void setFavList(List<Integer> favList) {
        this.favList = favList;
    }
}