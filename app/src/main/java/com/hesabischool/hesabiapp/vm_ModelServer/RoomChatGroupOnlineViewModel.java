package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

public class RoomChatGroupOnlineViewModel {
    @SerializedName("userID")
    public int UserID ;
    @SerializedName("fullName")
    public String FullName ;
    @SerializedName("isOnline")
    public boolean IsOnline ;
    @SerializedName("picName")
    public String PicName ;
   
}
