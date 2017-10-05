package com.wangh.e_university;

import cn.bmob.v3.BmobObject;

/**
 * Created by MXZ on 2017/10/4.
 */

public class User extends BmobObject {
    private String userName;
    private int points;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
