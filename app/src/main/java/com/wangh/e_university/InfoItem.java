package com.wangh.e_university;

/**
 * Created by wangh on 2016/8/14.
 */
public class InfoItem {
    private String text;
    private String title;

    public InfoItem(){

    }

    public InfoItem(String text, String title) {
        this.text = text;
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
