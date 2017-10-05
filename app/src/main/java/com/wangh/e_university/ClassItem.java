package com.wangh.e_university;


import android.text.format.Time;

import java.io.Serializable;

/**
 * Created by wangh on 2016/8/3.
 */
public class ClassItem implements Cloneable,Serializable{
    private String classTitle;
    private String classLocation;
    private String classTime;
    private String teacher;
    private int weekStart;
    private int weekEnd;
    private int singleWeek;
    private int doubleWeek;
    private int timeStart;
    private int timeEnd;
    private int classNumber;
    private int date;
    private int day;
    private int month;
    private int colorID;
    private boolean isDate=false;
    private boolean isPassed;
    private boolean isToday;

    public ClassItem(int date, int day ,int month, boolean isToday) {
        this.day = day;
        this.date = date;
        this.month = month;
        this.isToday = isToday;
        isDate=true;
    }

    public ClassItem(String classTitle, String classLocation, String classTime, String teacher, int weekStart, int weekEnd, int singleWeek, int doubleWeek, int timeStart, int timeEnd, int classNumber, int date, int colorID) {
        this.classTitle = classTitle;
        this.classLocation = classLocation;
        this.classTime = classTime;
        this.teacher = teacher;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.singleWeek = singleWeek;
        this.doubleWeek = doubleWeek;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.classNumber = classNumber;
        this.date = date;
        this.colorID = colorID;
        isDate=false;
    }

    public ClassItem(String classTitle, String classLocation, String classTime) {
        this.classTitle = classTitle;
        this.classLocation = classLocation;
        this.classTime = classTime;
        isDate=false;
    }

    public ClassItem(){

    }

    public boolean isDate() {
        return isDate;
    }

    public void setDate(boolean date) {
        isDate = date;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public void setClassLocation(String classLocation) {
        this.classLocation = classLocation;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setWeekStart(int weekStart) {
        this.weekStart = weekStart;
    }

    public void setWeekEnd(int weekEnd) {
        this.weekEnd = weekEnd;
    }

    public void setSingleWeek(int singleWeek) {
        this.singleWeek = singleWeek;
    }

    public void setDoubleWeek(int doubleWeek) {
        this.doubleWeek = doubleWeek;
    }

    public void setTimeStart(int timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(int timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setClassNumber(int classNumber) { this.classNumber = classNumber; }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    public void setDate(int date) { this.date = date; }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public String getClassTime() {
        return classTime;
    }

    public String getClassLocation() {
        return classLocation;
    }

    public int getWeekStart() {
        return weekStart;
    }

    public int getWeekEnd() {
        return weekEnd;
    }

    public int getSingleWeek() {
        return singleWeek;
    }

    public int getDoubleWeek() {
        return doubleWeek;
    }

    public int getTimeStart() {
        return timeStart;
    }

    public int getTimeEnd() {
        return timeEnd;
    }

    public String getTeacher() {
        return teacher;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public int getDate() {return date;}

    public int getColorID() {
        return colorID;
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

    @Override
    public String toString() {
        return "ClassItem{" +
                "classTitle='" + classTitle + '\'' +
                ", classLocation='" + classLocation + '\'' +
                ", classTime='" + classTime + '\'' +
                ", teacher='" + teacher + '\'' +
                ", weekStart=" + weekStart +
                ", weekEnd=" + weekEnd +
                ", singleWeek=" + singleWeek +
                ", doubleWeek=" + doubleWeek +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", classNumber=" + classNumber +
                ", date=" + date +
                ", day=" + day +
                ", month=" + month +
                ", colorID=" + colorID +
                ", isDate=" + isDate +
                ", isPassed=" + isPassed +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
