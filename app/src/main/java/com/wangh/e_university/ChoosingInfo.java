package com.wangh.e_university;

/**
 * Created by wangh on 2017/2/7.
 */

public class ChoosingInfo {
    private String name;
    private String stdID;
    private String department;
    private String major;
    private String classes;
    private String degree;
    private String grade;
    private String startTime;
    private String endTime;
    private int maximumCredit;
    private int nowCredit;
    private int limitCredit;

    public ChoosingInfo(){

    }

    public ChoosingInfo(String name, String stdID, String department, String major, String classes, String degree, String grade, String startTime, String endTime, int maximumCredit, int nowCredit, int limitCredit) {
        this.name = name;
        this.stdID = stdID;
        this.department = department;
        this.major = major;
        this.classes = classes;
        this.degree = degree;
        this.grade = grade;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maximumCredit = maximumCredit;
        this.nowCredit = nowCredit;
        this.limitCredit = limitCredit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStdID() {
        return stdID;
    }

    public void setStdID(String stdID) {
        this.stdID = stdID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getMaximumCredit() {
        return maximumCredit;
    }

    public void setMaximumCredit(int maximumCredit) {
        this.maximumCredit = maximumCredit;
    }

    public int getNowCredit() {
        return nowCredit;
    }

    public void setNowCredit(int nowCredit) {
        this.nowCredit = nowCredit;
    }

    public int getLimitCredit() {
        return limitCredit;
    }

    public void setLimitCredit(int limitCredit) {
        this.limitCredit = limitCredit;
    }

    @Override
    public String toString() {
        return "ChoosingInfo{" +
                "name='" + name + '\'' +
                ", stdID='" + stdID + '\'' +
                ", department='" + department + '\'' +
                ", major='" + major + '\'' +
                ", classes='" + classes + '\'' +
                ", degree='" + degree + '\'' +
                ", grade='" + grade + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", maximumCredit=" + maximumCredit +
                ", nowCredit=" + nowCredit +
                ", limitCredit=" + limitCredit +
                '}';
    }
}
