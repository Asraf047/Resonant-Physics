package com.amanullah.myapplication98.model;

public class ChapterItem {
    private int id;
    private String title;
    private String subtitle;
    private int imgUrl;

    public ChapterItem(){}

    public ChapterItem(int id, String title, String subtitle, int imgUrl) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
