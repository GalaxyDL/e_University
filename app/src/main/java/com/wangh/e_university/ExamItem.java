package com.wangh.e_university;

/**
 * Created by wangh on 2016/8/11.
 */
public class ExamItem {
    private String title;
    private String teacher;
    private String time;
    private String location;
    private String modus;
    private int credit;

    public ExamItem(){

    }

    public ExamItem(String title, String teacher, String time, String location, String modus, int credit) {
        this.title = title;
        this.teacher = teacher;
        this.time = time;
        this.location = location;
        this.modus = modus;
        this.credit = credit;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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

    public String getModus() {
        return modus;
    }

    public void setModus(String modus) {
        this.modus = modus;
    }

    @Override
    public String toString() {
        return "ExamItem{" +
                "title='" + title + '\'' +
                ", teacher='" + teacher + '\'' +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", modus='" + modus + '\'' +
                ", credit=" + credit +
                '}';
    }
}
