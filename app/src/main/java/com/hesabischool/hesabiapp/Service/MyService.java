package com.hesabischool.hesabiapp.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hesabischool.hesabiapp.Clases.ConvertChatmessageToRoomChatShowLeftResualt;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.Clases.hesabi_SignalR;
import com.hesabischool.hesabiapp.database.dbConnector;
import com.hesabischool.hesabiapp.database.dbQuerySelect;
import com.hesabischool.hesabiapp.viewmodel.vm_sendoflinechat;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer2;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer3;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatRightShowResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends Service {
    Context context;
    dbConnector db;
    dbQuerySelect dqs;
    Handler handler;
    final int delay = 5000; // 1000 milliseconds == 1 second
    boolean allowEnter = true;
int sizeForSend=0;
    @Override
    public void onCreate() {
        //ساخته شدن
        context = this;
        db = new dbConnector(context);
        dqs = new dbQuerySelect(context);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        //متوقف شدن
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //شروع شدن
        handler = new Handler();

        sendMessage();
        //return super.onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT;
    }

    private void sendMessage() {
        handler.postDelayed(new Runnable() {
            public void run() {
                if (app.net.isNetworkConnected(context) && allowEnter) {
                    allowEnter = false;
                    SendDataTo();
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    private void SendDataTo() {
        try {
            // List<RoomChatLeftShowResult> lvm= (List<RoomChatLeftShowResult>) dqs.SelesctListWhere(new RoomChatLeftShowResult(),"IsSqlLite","true");
            List<vm_sendoflinechat> lvm = (List<vm_sendoflinechat>) dqs.SelesctList(new vm_sendoflinechat());
            if (lvm != null && lvm.size() > 0) {
                sizeForSend=lvm.size();
                for (vm_sendoflinechat r : lvm) {
                    String where = " WHERE RoomChatID = " + String.valueOf(r.RoomChatID);
                    where += " AND  IsSqlLite = 1 ;";
                    List<RoomChatLeftShowResult> lvm2 = (List<RoomChatLeftShowResult>) dqs.SelesctListArryWhere(new RoomChatLeftShowResult(), where);
                    if (lvm2 != null && lvm2.size() > 0) {
                        SendToServer(lvm2.get(0));
                    }

                }
               // allowEnter = true;
            } else {
                allowEnter = true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void SendToServer(RoomChatLeftShowResult rv) {
        try {
            //Todo Send message to server
            final RoomChatLeftShowResult r=rv;
            setChatRoom(r);

        } catch (Exception ex) {

        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    private void cheksize()
    {
        sizeForSend-=sizeForSend;
        if(sizeForSend<1)
        {
            allowEnter=true;
            sizeForSend=0;
        }
    }



    private void setChatRoom(final RoomChatLeftShowResult r) {

        app.retrofit.retrofit().RoomChatInsert(r.RoomChatGroupID, r.TextChat, r.Filename, r.TagLearn, r.RoomChatParentID, r.RoomID, r.TeacherID, r.CourseID, r.ParentTextChat, r.ParentSenderName,r.RoomChatGroupTitle,r.RoomChatGroupType).enqueue(new Callback<GetDataFromServer3>() {
            @Override
            public void onResponse(Call<GetDataFromServer3> call, Response<GetDataFromServer3> response) {
                if (response.isSuccessful()) {

                     RoomChatLeftShowResult finalRcl = r;
//                     RoomChatLeftShowResult finalRcl1 =new RoomChatLeftShowResult(r.RoomChatID,r.RoomChatDate,r.SenderID,r.SenderName,r.RecieverID,r.RecieverName,r.RoomID,r.TeacherID,r.CourseID,r.TextChat,r.Filename
//                     ,r.TagLearn,r.RoomChatDelete,r.RoomChatUpdate,r.RoomChatParentID,r.AttachMsg,r.RoomChatGroupID,r.ParentSenderName,r.ParentTextChat,r.RoomChatViewNumber,r.MimeType,r.RoomChatDateString,r.IsSqlLite);
                   int lastIdRoomChat=finalRcl.RoomChatID;
                    GetDataFromServer3 lr = response.body();
                 //   GetDataFromServer rl = new GetDataFromServer();
                    hesabi_SignalR.sendMessage(lr.value);
                    RoomChatLeftShowResult rclst=new ConvertChatmessageToRoomChatShowLeftResualt(lr.value).convert();
                 //   rl.value.RoomChatLeftViewModel.RoomChatLeftShowResult.add(rclst);
                    String where = " RoomChatID = " + String.valueOf(lastIdRoomChat);
                    where += " AND IsSqlLite = 1";
                    try {
                        db.dq.saveTosqlUpdate(rclst, where);
                        String where2 = " RoomChatID = " + String.valueOf(lastIdRoomChat);
                        db.dq.saveTosqlDelete(new vm_sendoflinechat(), where2);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
checkpageCurente("U", lastIdRoomChat,rclst);
                    cheksize();
                }
            }

            @Override
            public void onFailure(Call<GetDataFromServer3> call, Throwable t) {
                cheksize();
int i=0;
            }
        });
    }

    private void checkpageCurente(String type, int oldroomchatId,RoomChatLeftShowResult newr) {
        if(app.Info.checkpage.curentActivity.equals("DetilsChat")&&app.Info.checkpage.roomchatright.RoomChatGroupID==newr.RoomChatGroupID)
        {
            //update
            if(type.equals("U"))
            {
                app.Info.checkpage.callForCheange.updateMessage(oldroomchatId,newr);
            }

        }
    }
}
