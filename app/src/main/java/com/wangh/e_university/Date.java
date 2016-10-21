package com.wangh.e_university;

import android.text.format.Time;
import android.util.Log;

/**
 * Created by wangh on 2016/8/15.
 */
public class Date {
    private int year;
    private int month;
    private int week;
    private int day;
    private int data;
    private int hour;
    private int minute;
    private Time time;
    private final Time startTime=new Time();

    public Date(){
        startTime.set(29,7,2016);
        time=new Time();
        time.setToNow();
        year=time.year;
        month=time.month;
        week=time.getWeekNumber();
        day=time.monthDay;
        data=time.weekDay;
        hour=time.hour;
        minute=time.minute;
    }

    public int getMonth() {
        return month+1;
    }

    public int getDay() {
        return day;
    }

    public int getData() {
        return data;
    }

    public int getYear() {
        return year;
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    public int getWeekOfTerm(){
        Boolean isHoliday=false;

//        Log.d("start week",""+startTime.getWeekNumber());
//        Log.d("start day",""+startTime.monthDay);
//        Log.d("start month",""+startTime.month);
//        Log.d("start year",""+startTime.year);
//
//        Log.d("now week",""+week);
//        Log.d("now day",""+day);
//        Log.d("now month",""+month);
//        Log.d("now year",""+year);

        int result=week-startTime.getWeekNumber()+1;
        while(result<0)result+=52;
        Log.d("result",""+result);
        while(result>20){
            result-=20;
            if(result<=5)isHoliday=true;
            else result-=5;
            if(result>20) {
                result-=20;
                if(result<=8){
                    isHoliday=true;
                }
            }
        }

        return isHoliday?0:result;
    }
}
