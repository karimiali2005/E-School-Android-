package com.hesabischool.hesabiapp.viewmodel;

import com.hesabischool.hesabiapp.Interfasces.callForCheange;
import com.hesabischool.hesabiapp.Interfasces.callForCheangeMainChat;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatRightShowResult;

public class vm_checkPage {
    public String curentActivity="";
 public RoomChatRightShowResult roomchatright=new RoomChatRightShowResult();
public callForCheange callForCheange;
public callForCheangeMainChat callForCheangeMainChat;

    public vm_checkPage(String curentActivity, RoomChatRightShowResult roomchatright,callForCheange callForCheange,callForCheangeMainChat callForCheangeMainChat) {
        this.curentActivity = curentActivity;
        this.roomchatright = roomchatright;
        this.callForCheange=callForCheange;
        this.callForCheangeMainChat=callForCheangeMainChat;
    }

    public vm_checkPage() {
    }
}
