package com.wangh.e_university;

import java.io.Serializable;

/**
 * Created by Galaxy on 2017/10/2.
 */

public abstract class BaseScheduleItem implements Serializable{
    private String title;
    private String time;
    private String location;
    private int day;
    private int date;
    private int month;
    private boolean isPassed;
    private boolean isDate=false;
    private boolean exam = false;
    private boolean isToday;
    private int colorID;

    public BaseScheduleItem() {

    }

    public BaseScheduleItem(String title, String time, String location) {
        this.title = title;
        this.time = time;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isExam() {
        return exam;
    }

    public void setExam(boolean exam) {
        this.exam = exam;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public boolean isDate() {
        return isDate;
    }

    public void setDate(boolean date) {
        isDate = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }
}
