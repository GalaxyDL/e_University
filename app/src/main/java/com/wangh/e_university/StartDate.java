package com.wangh.e_university;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by wangh on 2017/2/7.
 */

public class StartDate extends BmobObject {
    private Integer year=2017;
    private Integer month=8;
    private Integer day=15;

    public StartDate(){
    }

    public Integer getDay() {
        return day;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }
}