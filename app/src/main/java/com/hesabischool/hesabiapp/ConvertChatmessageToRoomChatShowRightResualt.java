package com.hesabischool.hesabiapp;

import com.hesabischool.hesabiapp.vm_ModelServer.ChatMessage;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatRightShowResult;

public class ConvertChatmessageToRoomChatShowRightResualt {
    ChatMessage chatMessage=new ChatMessage();
    RoomChatRightShowResult rcl2=new RoomChatRightShowResult();

    public ConvertChatmessageToRoomChatShowRightResualt(ChatMessage chatMessage, RoomChatRightShowResult roomChatLeftShowResult) {
        this.chatMessage = chatMessage;
        this.rcl2 = roomChatLeftShowResult;
    }

    public RoomChatRightShowResult convert()
    {
        RoomChatRightShowResult rcl=new RoomChatRightShowResult();
        rcl=rcl2;
     rcl.MessageNewNumber=chatMessage.roomChatViewNumber;
     rcl.RoomID=chatMessage.roomChatId;
     rcl.TextChat=chatMessage.textChat;
     rcl.RoomChatGroupID=chatMessage.groupId;
     rcl.RoomChatDateString=chatMessage.roomChatDateString;


        return rcl;
    }


}


