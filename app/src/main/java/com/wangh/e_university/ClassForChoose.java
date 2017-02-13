package com.wangh.e_university;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wangh on 2016/8/22.
 */
public class ClassForChoose {
    private String title;
    private ArrayList<String> location=new ArrayList<String>();
    private ArrayList<Integer> timeStart=new ArrayList<Integer>();
    private ArrayList<Integer> timeEnd=new ArrayList<Integer>();
    private ArrayList<Integer> data=new ArrayList<Integer>();
    private ArrayList<String> week=new ArrayList<String>();
    private String teacher;
    private int credit;
    private String nature;//课程性质
    private String category;//课程分类
    private String sort;//课程类别
    private String department;//开课单位
    private String hours;//课时数
    private int maximumNumber;
    private int currentNumber;
    private String idForChoose;//选课号
    private String id;
    private String[] targetClasses;
    private String targetSex;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getLocation() {
        return location;
    }

    public void setLocation(ArrayList<String> location) {
        this.location = location;
    }

    public ArrayList<Integer> getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(ArrayList<Integer> timeStart) {
        this.timeStart = timeStart;
    }

    public ArrayList<Integer> getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(ArrayList<Integer> timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public int getMaximumNumber() {
        return maximumNumber;
    }

    public void setMaximumNumber(int maximumNumber) {
        this.maximumNumber = maximumNumber;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String getIdForChoose() {
        return idForChoose;
    }

    public void setIdForChoose(String idForChoose) {
        this.idForChoose = idForChoose;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Integer> getData() {
        return data;
    }

    public void setData(ArrayList<Integer> data) {
        this.data = data;
    }

    public ArrayList<String> getWeek() {
        return week;
    }

    public void setWeek(ArrayList<String> week) {
        this.week = week;
    }

    public void parseClass(String[] lines){
        idForChoose=lines[3];
        id=lines[5];
        title=lines[7];
        hours=lines[13];
        sort=ClassInfoConst.CLASS_SORT.get(lines[21]);
        nature=ClassInfoConst.CLASS_NATURE.get(lines[23]);
        department=ClassInfoConst.CLASS_DEPARTMENT.get(lines[27]);
        try {
            credit=Integer.parseInt(lines[29]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        teacher=lines[31];
        try {
            maximumNumber=Integer.parseInt(lines[33]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        category=ClassInfoConst.CLASS_CATEGORY.get(lines[45]);
        if(lines.length==53){
            targetClasses=lines[49].split(",");
            targetSex=lines[51];
        }
    }

    public void addTime(String[] lines){
        week.add(lines[5]);
        try {
            data.add(Integer.parseInt(lines[7]));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            timeStart.add(Integer.parseInt(lines[9]));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            timeEnd.add(Integer.parseInt(lines[11]));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        location.add(lines[13]);
    }

    @Override
    public String toString() {
        return "ClassForChoose{" +
                "title='" + title + '\'' +
                ", location=" + location +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", data=" + data +
                ", week=" + week +
                ", teacher='" + teacher + '\'' +
                ", credit=" + credit +
                ", nature='" + nature + '\'' +
                ", category='" + category + '\'' +
                ", sort='" + sort + '\'' +
                ", department='" + department + '\'' +
                ", hours='" + hours + '\'' +
                ", maximumNumber=" + maximumNumber +
                ", currentNumber=" + currentNumber +
                ", idForChoose='" + idForChoose + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
