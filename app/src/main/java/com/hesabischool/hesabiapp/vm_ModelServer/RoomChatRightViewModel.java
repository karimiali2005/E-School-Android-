package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomChatRightViewModel {
    @SerializedName("roomChatRightShowResult")
    public List<RoomChatRightShowResult> RoomChatRightShowResult ;
    @SerializedName("roomLiveShows")
    public List<RoomLiveViewModel> RoomLiveShows;
}
