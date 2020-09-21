package com.amanullah.myapplication98.model;

import com.google.firebase.firestore.Exclude;

public class VideoItem {
    private String vid;
    private String title;
    private String time;
    private String link;
    private String datails;
    private int part;
    private int chapter;
    private int serial;
    private int type;
    private boolean isFree;

    public VideoItem(){}

    public VideoItem(String vid, String title, String time, String link, String datails, int part, int chapter, int type, boolean isFree) {
        this.vid = vid;
        this.title = title;
        this.time = time;
        this.link = link;
        this.datails = datails;
        this.part = part;
        this.chapter = chapter;
        this.type = type;
        this.isFree = isFree;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDatails() {
        return datails;
    }

    public void setDatails(String datails) {
        this.datails = datails;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    @Exclude
    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
