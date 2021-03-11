package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

public class RoomLiveViewModel {
    @SerializedName("liveType")
    public int LiveType ;
    @SerializedName("liveAddress")
    public String LiveAddress ;
    @SerializedName("liveUsername")
    public String LiveUsername ;
    @SerializedName("livePassword")
    public String LivePassword ;
    @SerializedName("roomChatGroupTitle")
    public String RoomChatGroupTitle ;
}
