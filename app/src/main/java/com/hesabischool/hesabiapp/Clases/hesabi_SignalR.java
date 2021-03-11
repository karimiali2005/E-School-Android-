package com.hesabischool.hesabiapp.Clases;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.hesabischool.hesabiapp.vm_ModelServer.ChatMessage;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftShowResult;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import com.microsoft.signalr.OnClosedCallback;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class hesabi_SignalR {
    Context context;

    public static HubConnection hubConnection;
    public hesabi_SignalR(Context context) {
    this.context=context;
       // MAP hubConnection= HubConnectionBuilder.create(app.baseUrl.signalr).withHeaders(map).build();
        hubConnection= HubConnectionBuilder.create(app.baseUrl.signalr).build();
        hubConnection.onClosed(new OnClosedCallback() {
            @Override
            public void invoke(Exception exception) {
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        StrtSignalR();
                    }
                }, 5000);
            }
        });
      StrtSignalR();

        hubConnection.on("ReceiveMessage",(chatMessage, connectionId)->{
            String h_cID=hubConnection.getConnectionId();
            if(!h_cID.equals(connectionId))
            {
                //Todo Chck Curent Page and Next
                int x=0;
                Calculating(chatMessage);
            }


        }, ChatMessage.class,String.class);
    }

    private void Calculating(ChatMessage chatMessage) {

        if(app.Info.checkpage.curentActivity.equals("MainChat"))
        {

            switch (chatMessage.chatType) {
                case "C":
                 //   RoomChatLeftShowResult rclst=new ConvertChatmessageToRoomChatShowLeftResualt(chatMessage,new RoomChatLeftShowResult()).convert();
             //    app.Info.checkpage.callForCheangeMainChat.insertOrUpdate(rclst);

                    break;
            }
        }else if(app.Info.checkpage.curentActivity.equals("DetilsChat"))
        {
            switch (chatMessage.chatType) {
                case "C":
                    //Insert For View This Message For Curent User
                    app.retrofit.retrofit().RoomChatViewInsert(chatMessage.roomChatId,chatMessage.groupId).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            int x=0;
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
int x=0;
                        }
                    });
                    RoomChatLeftShowResult rclst=new ConvertChatmessageToRoomChatShowLeftResualt(chatMessage,new RoomChatLeftShowResult()).convert();
                  app.Info.checkpage.callForCheange.AddMessage(rclst);
                    break;
                case "D":

                    app.Info.checkpage.callForCheange.DeleteMessage(chatMessage.roomChatId);
                    break;
                case "E":
                    RoomChatLeftShowResult rclst2=new ConvertChatmessageToRoomChatShowLeftResualt(chatMessage,new RoomChatLeftShowResult()).convert();
                    app.Info.checkpage.callForCheange.updateMessage(chatMessage.roomChatId,rclst2);
                    break;
                case "P":
                  //Todo pain
                    break;
                case "UP":
                    //Todo unPain
                    break;
                case "L":
                    //Todo lock
                    break;
                case "UL":
                    //Todo unLock
                    break;
                case "DM":
                    //Todo For Delete my message
                    break;
                case "DA":
                    //Todo For Delete All message
                    break;

            }

        }
    }

    private void StrtSignalR() {
        hubConnection.start().subscribe(() -> {
// connected
int x=0;
connected();
                },
                error -> {
// error
                 String e= error.getMessage();
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            StrtSignalR();
                        }
                    }, 5000);
                });

    }

    private void connected() {
        hubConnection.invoke("JoinRoom",app.Info.User.userID );
    }

    public static void sendMessage(ChatMessage chatMessage)
    {
      //  hubConnection.send("SendMessage",chatMessage);
        if(hubConnection!=null)
        {
        if(hubConnection.getConnectionState()== HubConnectionState.CONNECTED)
        {
        hubConnection.invoke("SendMessage",chatMessage);
        }
        }
    }
}
