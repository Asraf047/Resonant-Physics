package com.amanullah.myapplication98.model;

import com.google.firebase.firestore.Exclude;
import java.util.ArrayList;
import java.util.List;

public class UserItem {
    private String username;
    private String phone;
    private String email;
    private String pic_url;
    private String referral_code;
    private String referral_done;
    private String referral_purchase;
    private String referral_balance;
    private List<String> paid_video_id = new ArrayList<>();
    private String userID;

    public UserItem(){}

    public UserItem(String username, String phone, String email, String pic_url, String referral_code, String referral_done, String referral_purchase, String referral_balance, List<String> paid_video_id) {
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.pic_url = pic_url;
        this.referral_code = referral_code;
        this.referral_done = referral_done;
        this.referral_purchase = referral_purchase;
        this.referral_balance = referral_balance;
        this.paid_video_id = paid_video_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getReferral_code() {
        return referral_code;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    public String getReferral_done() {
        return referral_done;
    }

    public void setReferral_done(String referral_done) {
        this.referral_done = referral_done;
    }

    public String getReferral_purchase() {
        return referral_purchase;
    }

    public void setReferral_purchase(String referral_purchase) {
        this.referral_purchase = referral_purchase;
    }

    public String getReferral_balance() {
        return referral_balance;
    }

    public void setReferral_balance(String referral_balance) {
        this.referral_balance = referral_balance;
    }

    public List<String> getPaid_video_id() {
        return paid_video_id;
    }

    public void setPaid_video_id(List<String> paid_video_id) {
        this.paid_video_id = paid_video_id;
    }

    @Exclude
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}


