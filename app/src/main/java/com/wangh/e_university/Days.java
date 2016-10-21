package com.wangh.e_university;

import cn.bmob.v3.BmobObject;

/**
 * Created by wangh on 2016/10/6.
 */

public class Days extends BmobObject {
    private Integer week;
    private String days;

    public static Days getDefaultDays(int week){
        Days days = new Days();
        days.setWeek(week);
        days.setDays("1234567");
        return days;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Integer getWeek() {
        return week;
    }

    public String getDays() {
        return days;
    }
}
