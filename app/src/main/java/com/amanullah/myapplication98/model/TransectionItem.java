package com.amanullah.myapplication98.model;

public class TransectionItem {
    private String from;
    private String to;
    private String amount;
    private String time;
    private String percentage_from;
    private String percentage_to;

    public TransectionItem(){}

    public TransectionItem(String from, String to, String amount, String time, String percentage_from, String percentage_to) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.time = time;
        this.percentage_from = percentage_from;
        this.percentage_to = percentage_to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPercentage_from() {
        return percentage_from;
    }

    public void setPercentage_from(String percentage_from) {
        this.percentage_from = percentage_from;
    }

    public String getPercentage_to() {
        return percentage_to;
    }

    public void setPercentage_to(String percentage_to) {
        this.percentage_to = percentage_to;
    }
}
