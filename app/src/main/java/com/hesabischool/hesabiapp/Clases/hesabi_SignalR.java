package com.hesabischool.hesabiapp.Clases;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.hesabischool.hesabiapp.database.dbConnector;
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
dbConnector db;
    public static HubConnection hubConnection;

    public hesabi_SignalR(Context context) {
        this.context = context;
        this.db=new dbConnector(context);
        // MAP hubConnection= HubConnectionBuilder.create(app.baseUrl.signalr).withHeaders(map).build();
        hubConnection = HubConnectionBuilder.create(app.baseUrl.signalr).build();
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

        hubConnection.on("ReceiveMessage", (chatMessage, connectionId) -> {
            String h_cID = hubConnection.getConnectionId();
            if (!h_cID.equals(connectionId)) {
                //Todo Chck Curent Page and Next
                int x = 0;
                Calculating(chatMessage);
            }


        }, ChatMessage.class, String.class);
    }

    private void Calculating(ChatMessage chatMessage) {

        if (app.Info.checkpage.curentActivity.equals("MainChat")) {

            switch (chatMessage.chatType) {
                case "C":
                    //   RoomChatLeftShowResult rclst=new ConvertChatmessageToRoomChatShowLeftResualt(chatMessage,new RoomChatLeftShowResult()).convert();
                    //    app.Info.checkpage.callForCheangeMainChat.insertOrUpdate(rclst);
                    app.Info.checkpage.callForCheangeMainChat.callGetDataFromServer();
                    break;
            }
        }
        else if (app.Info.checkpage.curentActivity.equals("DetilsChat")) {
            if (app.Info.checkpage.roomchatright.RoomChatGroupID == chatMessage.groupId) {
                switch (chatMessage.chatType) {
                    case "C":
                        //Insert For View This Message For Curent User
                        app.retrofit.retrofit().RoomChatViewInsert(chatMessage.roomChatId, chatMessage.groupId).enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                int x = 0;
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                int x = 0;
                            }
                        });
                        RoomChatLeftShowResult rclst = new ConvertChatmessageToRoomChatShowLeftResualt(chatMessage, new RoomChatLeftShowResult()).convert();
                        app.Info.checkpage.callForCheange.AddMessage(rclst);
                        break;
                    case "D":

                        app.Info.checkpage.callForCheange.DeleteMessage(chatMessage.roomChatId);
                        break;
                    case "E":
                        RoomChatLeftShowResult rclst2 = new ConvertChatmessageToRoomChatShowLeftResualt(chatMessage, new RoomChatLeftShowResult()).convert();
                        app.Info.checkpage.callForCheange.updateMessage(chatMessage.roomChatId, rclst2);
                        break;
                    case "P":
                        //Todo pain
                        app.Info.checkpage.callForCheange.pinMessage(chatMessage);
                        break;
                    case "UP":
                        //Todo unPain
                        app.Info.checkpage.callForCheange.UnpinMessage(chatMessage);
                        break;
                    case "L":
                        //Todo lock
                        app.Info.checkpage.callForCheange.setLockAndUnLock(chatMessage, true);
                        break;
                    case "UL":
                        //Todo unLock
                        app.Info.checkpage.callForCheange.setLockAndUnLock(chatMessage, false);
                        break;


                }
            }


        }

        if(chatMessage.chatType.equals("DM"))
        {
            //=========DeleteFrom sqlllite
            try {
                String where2 = " RoomChatID = " + String.valueOf(chatMessage.roomChatId);
                db.dq.saveTosqlDelete(new RoomChatLeftShowResult(), where2);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //==============End========================
        }else if(chatMessage.chatType.equals("DA"))
        {
            //=========DeleteFrom sqlllite
            try {
                String where2 = " RoomChatID = " + String.valueOf(chatMessage.roomChatId);
                db.dq.saveTosqlDelete(new RoomChatLeftShowResult(), where2);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //==============End========================
        }else if(chatMessage.chatType.equals("D"))
        {
            //=========DeleteFrom sqlllite
            try {
                String where2 = " RoomChatID = " + String.valueOf(chatMessage.roomChatId);
                db.dq.saveTosqlDelete(new RoomChatLeftShowResult(), where2);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //==============End========================
        }

    }

    int x = 0;

    private void StrtSignalR() {
        hubConnection.start().subscribe(() -> {
// connected
                    setToastToActivity("متصل گردید.", true);
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //hiden Lin
                            setToastToActivity("", false);
                        }
                    }, 5000);

                    connected();
                    if (x != 0) {

                        app.Info.checkpage.callForCheangeMainChat.callGetDataFromServer();
                    }
                    x++;
                },
                error -> {
// error
                    setToastToActivity("در حال اتصال...", true);

                    String e = error.getMessage();
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            StrtSignalR();
                        }
                    }, 5000);
                });

    }

    private void setToastToActivity(String s, boolean b) {

        if (app.Info.checkpage.curentActivity.equals("MainChat")) {

            app.Info.checkpage.callForCheangeMainChat.ToastMessage(s, b);

        } else if (app.Info.checkpage.curentActivity.equals("DetilsChat")) {
            app.Info.checkpage.callForCheange.ToastMessage(s, b);
        }
    }

    private void connected() {
        hubConnection.invoke("JoinRoom", app.Info.User.userID);
    }

    public static void sendMessage(ChatMessage chatMessage) {
        //  hubConnection.send("SendMessage",chatMessage);
        if (hubConnection != null) {
            if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
                hubConnection.invoke("SendMessage", chatMessage);
            }
        }
    }
}
