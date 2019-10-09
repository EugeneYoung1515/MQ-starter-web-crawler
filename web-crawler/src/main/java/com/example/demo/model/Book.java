package com.example.demo.model;

import java.math.BigDecimal;
import java.util.Date;

public class Book {
    private Integer bookId;

    private String bookTitle;

    private Integer seriesId;

    private String bookIntro;

    private String feature;

    private Date bookDate;

    private Integer categoryId;

    private Integer alsoLikeId;

    private String imageLink;

    private String label;

    private Integer bookVote;

    private Integer bookEnterNum;

    private Integer isEbook;

    private BigDecimal ebookPrice;

    private BigDecimal paperOriginalPrice;

    private BigDecimal paperSalePrice;

    private String amazonLink;

    private String dangdangLink;

    private String jdLink;

    private Integer enableGift;

    private String sampleDownload;

    private String withBookDownload;

    private String bigImageLink;

    private Integer ninetyBookEnterNum;

    private Integer firstGradeId;

    private Integer secondGradeId;

    private Integer thirdGradeId;

    private Date modifiedDate;

    private BigDecimal ebookSalePrice;

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

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro == null ? null : bookIntro.trim();
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature == null ? null : feature.trim();
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getAlsoLikeId() {
        return alsoLikeId;
    }

    public void setAlsoLikeId(Integer alsoLikeId) {
        this.alsoLikeId = alsoLikeId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink == null ? null : imageLink.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public Integer getBookVote() {
        return bookVote;
    }

    public void setBookVote(Integer bookVote) {
        this.bookVote = bookVote;
    }

    public Integer getBookEnterNum() {
        return bookEnterNum;
    }

    public void setBookEnterNum(Integer bookEnterNum) {
        this.bookEnterNum = bookEnterNum;
    }

    public Integer getIsEbook() {
        return isEbook;
    }

    public void setIsEbook(Integer isEbook) {
        this.isEbook = isEbook;
    }

    public BigDecimal getEbookPrice() {
        return ebookPrice;
    }

    public void setEbookPrice(BigDecimal ebookPrice) {
        this.ebookPrice = ebookPrice;
    }

    public BigDecimal getPaperOriginalPrice() {
        return paperOriginalPrice;
    }

    public void setPaperOriginalPrice(BigDecimal paperOriginalPrice) {
        this.paperOriginalPrice = paperOriginalPrice;
    }

    public BigDecimal getPaperSalePrice() {
        return paperSalePrice;
    }

    public void setPaperSalePrice(BigDecimal paperSalePrice) {
        this.paperSalePrice = paperSalePrice;
    }

    public String getAmazonLink() {
        return amazonLink;
    }

    public void setAmazonLink(String amazonLink) {
        this.amazonLink = amazonLink == null ? null : amazonLink.trim();
    }

    public String getDangdangLink() {
        return dangdangLink;
    }

    public void setDangdangLink(String dangdangLink) {
        this.dangdangLink = dangdangLink == null ? null : dangdangLink.trim();
    }

    public String getJdLink() {
        return jdLink;
    }

    public void setJdLink(String jdLink) {
        this.jdLink = jdLink == null ? null : jdLink.trim();
    }

    public Integer getEnableGift() {
        return enableGift;
    }

    public void setEnableGift(Integer enableGift) {
        this.enableGift = enableGift;
    }

    public String getSampleDownload() {
        return sampleDownload;
    }

    public void setSampleDownload(String sampleDownload) {
        this.sampleDownload = sampleDownload == null ? null : sampleDownload.trim();
    }

    public String getWithBookDownload() {
        return withBookDownload;
    }

    public void setWithBookDownload(String withBookDownload) {
        this.withBookDownload = withBookDownload == null ? null : withBookDownload.trim();
    }

    public String getBigImageLink() {
        return bigImageLink;
    }

    public void setBigImageLink(String bigImageLink) {
        this.bigImageLink = bigImageLink == null ? null : bigImageLink.trim();
    }

    public Integer getNinetyBookEnterNum() {
        return ninetyBookEnterNum;
    }

    public void setNinetyBookEnterNum(Integer ninetyBookEnterNum) {
        this.ninetyBookEnterNum = ninetyBookEnterNum;
    }

    public Integer getFirstGradeId() {
        return firstGradeId;
    }

    public void setFirstGradeId(Integer firstGradeId) {
        this.firstGradeId = firstGradeId;
    }

    public Integer getSecondGradeId() {
        return secondGradeId;
    }

    public void setSecondGradeId(Integer secondGradeId) {
        this.secondGradeId = secondGradeId;
    }

    public Integer getThirdGradeId() {
        return thirdGradeId;
    }

    public void setThirdGradeId(Integer thirdGradeId) {
        this.thirdGradeId = thirdGradeId;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public BigDecimal getEbookSalePrice() {
        return ebookSalePrice;
    }

    public void setEbookSalePrice(BigDecimal ebookSalePrice) {
        this.ebookSalePrice = ebookSalePrice;
    }
}