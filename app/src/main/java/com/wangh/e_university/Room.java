package com.wangh.e_university;

import cn.bmob.v3.BmobObject;

/**
 * Created by mxz on 2017/11/6.
 */

public class Room extends BmobObject {
    private int floorNumber;
    private int roomNumber;

    private String seatInfo;

    public Room(int floorNumber, int roomNumber, String seatInfo) {
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.seatInfo = seatInfo;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getSeatInfo() {
        return seatInfo;
    }

    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }
}
