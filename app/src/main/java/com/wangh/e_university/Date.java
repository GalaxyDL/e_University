package com.wangh.e_university;

import android.text.format.Time;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by wangh on 2016/8/15.
 */
public class Date {
    private final static String OBJECT_ID="xvDEcccw";

    private int year;
    private int month;
    private int week;
    private int day;
    private int data;
    private int hour;
    private int minute;
    private Time time;
    private final Time startTime=new Time();
    private StartDate startDate;

    public Date(){
        Log.d("Date","date");
//        BmobQuery<StartDate> bmobQuery = new BmobQuery<StartDate>();
//        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
//        bmobQuery.setMaxCacheAge(TimeUnit.DAYS.toMillis(30));
//        bmobQuery.findObjects(new FindListener<StartDate>() {
//            @Override
//            public void done(List<StartDate> list, BmobException e) {
//                if(e==null){
//                    startDate = list.get(0);
//                    setStartDate(list.get(0));
//                }
//            }
//        });
        startDate = new StartDate();
        setStartDate(startDate);
        time=new Time();
        time.setToNow();
        year=time.year;
        month=time.month;
        week=time.getWeekNumber();
        day=time.monthDay;
        data=time.weekDay;
        hour=time.hour;
        minute=time.minute;
        Log.d("Date","date e");
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public void setStartDate(StartDate startDate) {
        startTime.set(startDate.getDay(),startDate.getMonth(),startDate.getYear());
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

        Log.d("start week",""+startTime.getWeekNumber());
        Log.d("start day",""+startTime.monthDay);
        Log.d("start month",""+startTime.month);
        Log.d("start year",""+startTime.year);

        Log.d("now week",""+week);
        Log.d("now day",""+day);
        Log.d("now month",""+month);
        Log.d("now year",""+year);

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
