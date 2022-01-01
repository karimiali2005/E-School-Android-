package com.hesabischool.hesabiapp.Clases;

import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftShowResult;

public class SpliterAdaptor {
    private RoomChatLeftShowResult roomChatLeftShowResult=null;
    private String date=null;
    private boolean isDate;

    public RoomChatLeftShowResult getRoomChatLeftShowResult() {
        return roomChatLeftShowResult;
    }

    public void setRoomChatLeftShowResult(RoomChatLeftShowResult roomChatLeftShowResult) {
        this.roomChatLeftShowResult = roomChatLeftShowResult;
        isDate=false;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        isDate=true;
    }
    public boolean getIsDate()
    {
        return isDate;
    }




}
