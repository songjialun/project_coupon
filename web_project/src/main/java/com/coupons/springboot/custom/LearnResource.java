package com.coupons.springboot.custom;

public class LearnResource {

    private String author;
    private String title;
    private String url;



    public LearnResource(String n, String t, String u){
        this.author = n;
        this.title = t;
        this.url = u;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
