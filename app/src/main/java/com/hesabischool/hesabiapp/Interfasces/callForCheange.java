package com.hesabischool.hesabiapp.Interfasces;

import androidx.recyclerview.widget.RecyclerView;

import com.hesabischool.hesabiapp.vm_ModelServer.ChatMessage;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftPropertyResult;
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
    public RoomChatLeftPropertyResult getPeroperty();
    //set message pin in ui
    public void pinMessage(ChatMessage chatMessage);
    //set message pin in ui
    public void UnpinMessage(ChatMessage chatMessage);
    //send to server for pin
    public void SetpinMesage(RoomChatLeftShowResult rchatleft);
public void setLockAndUnLock(ChatMessage chatMessage,boolean _islock);
public void setCountNewMessage();
    public void ToastMessage(String message,boolean isshow);
}
