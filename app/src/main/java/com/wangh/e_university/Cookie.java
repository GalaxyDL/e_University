package com.wangh.e_university;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by wangh on 2016/7/28.
 */
public class Cookie {
    private ArrayList<String> name=new ArrayList<String>();
    private ArrayList<String> value=new ArrayList<String>();
    private int count=0;

    public void getCookie(String cookie){
        String[] temp=new String[2];
        temp=cookie.split("\\;")[0].split("=",2);
        boolean found=false;
        Log.d("get cookie",temp[0]+":"+temp[1]);
        for(int i=0;i<count;i++){
            if(name.get(i).equals(temp[0])){
                value.set(i,temp[1]);
                found=true;
                break;
            }
        }
        if(!found){
            count++;
            name.add(temp[0]);
            value.add(temp[1]);
        }
    }

    public String toString(int pos) {
        return name.get(pos)+"="+value.get(pos);
    }

    public String toString(){
        String cookie=toString(0);
        for(int i=1;i<getCount();i++){
            cookie=cookie+"; "+toString(i);
        }
        return cookie;
    }

    public int getCount() {
        return count;
    }

    public String getName(int pos) {
        return name.get(pos);
    }

    public String getValue(int pos) {
        return value.get(pos);
    }

}
