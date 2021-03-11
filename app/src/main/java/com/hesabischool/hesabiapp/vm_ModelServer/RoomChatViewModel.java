package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

public class RoomChatViewModel {
    @SerializedName("roomChatRightViewModel")
    public RoomChatRightViewModel RoomChatRightViewModel ;
    @SerializedName("roomChatLeftViewModel")
    public RoomChatLeftViewModel RoomChatLeftViewModel  ;
}
