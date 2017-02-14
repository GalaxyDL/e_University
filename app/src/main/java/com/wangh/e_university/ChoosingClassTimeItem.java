package com.wangh.e_university;

/**
 * Created by wangh on 2017/2/14.
 */

public class ChoosingClassTimeItem {
    private String time;
    private String location;

    public ChoosingClassTimeItem(){

    }

    public ChoosingClassTimeItem(String time, String location) {
        this.time = time;
        this.location = location;
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

    @Override
    public String toString() {
        return "ChoosingClassTimeItem{" +
                "time='" + time + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
