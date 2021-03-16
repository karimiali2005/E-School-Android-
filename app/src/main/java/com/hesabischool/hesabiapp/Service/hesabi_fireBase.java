package com.hesabischool.hesabiapp.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.DetilsChat;
import com.hesabischool.hesabiapp.MainChat;
import com.hesabischool.hesabiapp.R;
import com.hesabischool.hesabiapp.Splash;
import com.hesabischool.hesabiapp.vm_ModelServer.ChatMessage;

import java.lang.reflect.Type;
import java.util.List;

import static com.hesabischool.hesabiapp.Clases.FontOverride.NOTIFICATION_CHANNEL_ID;

public class hesabi_fireBase extends FirebaseMessagingService {

    //    // extends FirebaseInstanceIdService
//    @Override
//    public void onTokenRefresh() {
//        // Get updated InstanceID token.
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//     //   Log.d(TAG, "Refreshed token: " + refreshedToken);
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
//      //  sendRegistrationToServer(refreshedToken);
//    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    Context context;

    public hesabi_fireBase() {
        this.context = this;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            String body = remoteMessage.getData().get("body");
            Gson gson2 = new Gson();
          //  TypeToken listType = TypeToken.getParameterized(List.class, Class.forName(ChatMessage));
            Type listtype=new TypeToken<ChatMessage>(){}.getType();
            ChatMessage ch= gson2.fromJson(body, listtype);
            //todo get chat message json

            if (app.Info.checkpage.curentActivity.equals("")) {
                //app is Close
                //todo send notification
                notifi_when_app_destroy(body);
            } else {
                //app is open


                if (app.Info.checkpage.roomchatright.RoomChatGroupID == ch.groupId) {
                    // dont send notifi
                } else {
                    //todo send notifi
                    notifi_when_app_run(body);
                }


            }

        }

        // Check if message contains a notification payload.
    }


    private void notifi_when_app_destroy(String json) {
        Gson gson2 = new Gson();
        //  TypeToken listType = TypeToken.getParameterized(List.class, Class.forName(ChatMessage));
        Type listtype=new TypeToken<ChatMessage>(){}.getType();
        ChatMessage ch= gson2.fromJson(json, listtype);

        int idnot = ch.groupId;
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
// app.Info.FkfCompanyId=m.FkfCompanyId;
        Intent notificationIntent = new Intent(this, Splash.class);
        notificationIntent.putExtra("idc", json);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, idnot,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_baseline_message_24)
                .setContentIntent(pendingIntent)
                .setContentText(ch.textChat)
                .setContentInfo("پیام جدید").setSound(uri)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setAutoCancel(true)

        ;
// long[] v = {500,1000};
// Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
// notificationBuilder.setVibrate(v);
// notificationBuilder.setSound(uri);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(idnot, notificationBuilder.build());

    }

    private void notifi_when_app_run(String json) {
        Gson gson2 = new Gson();

        Type listtype=new TypeToken<ChatMessage>(){}.getType();
        ChatMessage ch= gson2.fromJson(json, listtype);

        int idnot = ch.groupId;
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
// app.Info.FkfCompanyId=m.FkfCompanyId;
        Intent notificationIntent = new Intent(this, MainChat.class);
        notificationIntent.putExtra("idc", json);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, idnot,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_baseline_message_24)
                .setContentIntent(pendingIntent)
                .setContentText(ch.textChat)
                .setContentInfo("پیام جدید").setSound(uri)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setAutoCancel(true)

        ;
// long[] v = {500,1000};
// Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
// notificationBuilder.setVibrate(v);
// notificationBuilder.setSound(uri);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(idnot, notificationBuilder.build());

    }
}