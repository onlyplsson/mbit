package com.example.mbit;

public class Room {
    private String sRoomTitle;
    private  int nCurrCount;

    public void setsCurrCount(int sCurrCount) {
        this.nCurrCount = sCurrCount;
    }

    public int getsCurrCount() {
        return nCurrCount;
    }

    public void setsRoomTitle(String sRoomTitle) {
        this.sRoomTitle = sRoomTitle;
    }

    public String getsRoomTitle() {
        return sRoomTitle;
    }
}
