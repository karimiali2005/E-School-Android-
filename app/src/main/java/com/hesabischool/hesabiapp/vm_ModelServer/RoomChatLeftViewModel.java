package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomChatLeftViewModel {
    @SerializedName("roomChatLeftShowResult")
    public List<RoomChatLeftShowResult> RoomChatLeftShowResult ;
    @SerializedName("roomChatLeftPropertyResult")
    public RoomChatLeftPropertyResult RoomChatLeftPropertyResult ;
}
