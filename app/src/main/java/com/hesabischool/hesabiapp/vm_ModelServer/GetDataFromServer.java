package com.hesabischool.hesabiapp.vm_ModelServer;

import java.util.ArrayList;
import java.util.List;

public class GetDataFromServer {
    public RoomChatViewModel value;
    public List<Object> formatters;
    public List<Object> contentTypes;
    public Object declaredType;
    public int statusCode;

    public GetDataFromServer() {
        value=new RoomChatViewModel();
        formatters=new ArrayList<>();
        contentTypes=new ArrayList<>();
        declaredType=new Object();
        statusCode=0;

    }
}
