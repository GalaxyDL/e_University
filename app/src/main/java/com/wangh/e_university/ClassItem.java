package com.wangh.e_university;


import android.text.format.Time;

import java.io.Serializable;

/**
 * Created by wangh on 2016/8/3.
 */
public class ClassItem extends BaseScheduleItem implements Cloneable {
    private String teacher;
    private int weekStart;
    private int weekEnd;
    private int singleWeek;
    private int doubleWeek;
    private int timeStart;
    private int timeEnd;
    private int classNumber;


    public ClassItem(int date, int day ,int month, boolean isToday) {
        setDay(day);
        setDate(date);
        setMonth(month);
        setToday(isToday);
        setDate(true);
    }

    public ClassItem(String classTitle, String classLocation, String classTime, String teacher, int weekStart, int weekEnd, int singleWeek, int doubleWeek, int timeStart, int timeEnd, int classNumber, int date, int colorID) {
        super(classTitle,classTime,classLocation);
        this.teacher = teacher;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.singleWeek = singleWeek;
        this.doubleWeek = doubleWeek;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.classNumber = classNumber;
        setDate(date);
        setColorID(colorID);
        setDate(false);
    }

    public ClassItem(String classTitle, String classLocation, String classTime) {
        super(classTitle,classTime,classLocation);
        setDate(false);
    }

    public ClassItem(){

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

    @Override
    public String toString() {
        return "ClassItem{" +
                "classTitle='" + getTitle() + '\'' +
                ", classLocation='" + getLocation() + '\'' +
                ", classTime='" + getTime() + '\'' +
                ", teacher='" + teacher + '\'' +
                ", weekStart=" + weekStart +
                ", weekEnd=" + weekEnd +
                ", singleWeek=" + singleWeek +
                ", doubleWeek=" + doubleWeek +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", classNumber=" + classNumber +
                ", date=" + getDate() +
                ", day=" + getDay() +
                ", month=" + getMonth() +
                ", colorID=" + getColorID() +
                ", isDate=" + getDate() +
                ", isPassed=" + isPassed() +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
