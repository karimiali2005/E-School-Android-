package com.hesabischool.hesabiapp.Interfasces;

import androidx.recyclerview.widget.RecyclerView;

import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftShowResult;

import java.io.File;

public interface callForCheange {
    public void updateMessage(int oldroomChatd,RoomChatLeftShowResult newr);
    public void ReplayMessage(RoomChatLeftShowResult rchatleft,boolean isedite);
    public void DeleteMessage(int idroomchat);
    public void DialogDeleteMessage(RoomChatLeftShowResult rchatleft);
    public void gotoPostionItem(int idRoomChat);
    public void getFileVoice(File file);
    public void gotodown();
    public void AddMessage(RoomChatLeftShowResult rchatleft);

}
