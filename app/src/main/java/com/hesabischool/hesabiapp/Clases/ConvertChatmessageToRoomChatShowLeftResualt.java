package com.hesabischool.hesabiapp.Clases;

import com.hesabischool.hesabiapp.vm_ModelServer.ChatMessage;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftShowResult;

public class ConvertChatmessageToRoomChatShowLeftResualt {
    ChatMessage chatMessage;
    RoomChatLeftShowResult rcl2;

    public ConvertChatmessageToRoomChatShowLeftResualt(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;

    }

    public RoomChatLeftShowResult convert()
    {
        RoomChatLeftShowResult rcl=new RoomChatLeftShowResult();

        rcl.SenderName=chatMessage.senderName;
        rcl.SenderID=chatMessage.senderId;
        rcl.IsSqlLite=false;
        rcl.RoomChatID=chatMessage.roomChatId;
        rcl.ParentSenderName=chatMessage.parentSenderName;
        rcl.ParentTextChat=chatMessage.parentTextChat;
       rcl.RoomChatParentID=chatMessage.roomChatParentId;
       rcl.TagLearn=chatMessage.tagLearn;
       rcl.Filename=chatMessage.filename;
       rcl.TextChat=chatMessage.textChat;
       rcl.RoomID=0;
       rcl.RoomChatGroupID=chatMessage.groupId;
       rcl.RoomChatDateString=chatMessage.roomChatDateString;
       rcl.MimeType=chatMessage.mimeType;
       rcl.AttachMsg=chatMessage.attachMsg;
       rcl.RoomChatViewNumber=chatMessage.roomChatViewNumber;
       rcl.RoomChatDate=chatMessage.roomChatDate;

    return rcl;
    }


}
