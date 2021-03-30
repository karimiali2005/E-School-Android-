package com.hesabischool.hesabiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hesabischool.hesabiapp.Clases.ExceptionHandler;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.Clases.hesabi_Risave;
import com.hesabischool.hesabiapp.Clases.hesabi_SignalR;
import com.hesabischool.hesabiapp.Image.ImageLoader;
import com.hesabischool.hesabiapp.Interfasces.callForCheange;
import com.hesabischool.hesabiapp.Interfasces.callForCheangeMainChat;
import com.hesabischool.hesabiapp.Service.MyService;
import com.hesabischool.hesabiapp.adaptor.Adaptor_chatRight;
import com.hesabischool.hesabiapp.database.dbQuerySelect;
import com.hesabischool.hesabiapp.vm_ModelServer.ChatMessage;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatRightShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatViewModel;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainChat extends AppCompatActivity {
    Context context;
    dbQuerySelect dq;
    private Intent servIntent;
    Adaptor_chatRight ma;
    RecyclerView shimmerRecycler;
    hesabi_Risave hesabi_risave;
    ImageLoader imgLoader;
    callForCheangeMainChat c = new callForCheangeMainChat() {

        @Override
        public void callGetDataFromServer() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GetDataFromServer();
                }
            });

        }

        @Override
        public void refresh() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Risave();
                }
            });

        }

        @Override
        public void ToastMessage(String message, boolean isshow) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (isshow) {
                        if (app.check.EpmtyOrNull(message)) {
                            app.linProgress.showProgress(context);
                        } else {
                            app.linProgress.showProgress(context, message);

                        }
                    } else {
                        app.linProgress.hideProgress(context);
                    }
                }
            });
        }

    };

    public void GetDataFromServer() {
        hesabi_risave.GetdateFromServerFirst();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_main_chat);
        try {
            context = this;
            shimmerRecycler = (RecyclerView) findViewById(R.id.rec_chat);
            hesabi_SignalR hesabi_signalR = new hesabi_SignalR(context);
            dq = new dbQuerySelect(context);
            servIntent = new Intent(context, MyService.class);
            hesabi_risave = new hesabi_Risave(context);
            //   GetDataFromServer();
            runService();
            Risave();
            checkBundelNotification();
            NavigationViewCheck();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void NavigationViewCheck() {
        NavigationView nav = findViewById(R.id.nav);
        View headerView = nav.getHeaderView(0);
//=======Header==========================
        TextView htxt_name=headerView.findViewById(R.id.txt_name);
        TextView htxt_mobile=headerView.findViewById(R.id.txt_mobile);
        CircleImageView imgprofile=headerView.findViewById(R.id.img_profile);
        htxt_name.setText(app.Info.User.fullName);
        htxt_mobile.setText(app.Info.User.mobileNumber);

        imgLoader = new ImageLoader(this);

        String url = app.baseUrl.retrofit + app.baseUrl.picurl+"?picName="+app.Info.User.picName;

        imgLoader.DisplayPicture(app.Info.User.userID,url, imgprofile);




    }

    private void checkBundelNotification() {
        String newString = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            newString = extras.getString("idc");
            if (!app.check.EpmtyOrNull(newString)) {

                Gson gson2 = new Gson();
                //  TypeToken listType = TypeToken.getParameterized(List.class, Class.forName(ChatMessage));
                Type listtype = new TypeToken<ChatMessage>() {
                }.getType();
                ChatMessage ch = gson2.fromJson(newString, listtype);
                Toast.makeText(context, String.valueOf(ch.groupId), Toast.LENGTH_SHORT).show();
                ma.gotoDetilsmessage(ch);

            }
        }
    }

    private void Risave() {

        try {
            List<RoomChatRightShowResult> s = (List<RoomChatRightShowResult>) dq.SelesctListOrderByDesending(new RoomChatRightShowResult(), "RoomChatDate");
            //    List<RoomChatRightShowResult> ss= (List<RoomChatRightShowResult>) dq.SelesctList(new RoomChatRightShowResult());
            SetRecycler(s);

        } catch (IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        //   runService();
        app.Info.checkpage.curentActivity = "MainChat";
        app.Info.checkpage.callForCheangeMainChat = c;

        Risave();
        GetDataFromServer();

        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


        String newString = "";

        Bundle extras = intent.getExtras();
        if (extras != null) {

            newString = extras.getString("idc");
            if (!app.check.EpmtyOrNull(newString)) {

                Gson gson2 = new Gson();
                //  TypeToken listType = TypeToken.getParameterized(List.class, Class.forName(ChatMessage));
                Type listtype = new TypeToken<ChatMessage>() {
                }.getType();
                ChatMessage ch = gson2.fromJson(newString, listtype);
                Toast.makeText(context, String.valueOf(ch.groupId), Toast.LENGTH_SHORT).show();
                ma.gotoDetilsmessage(ch);

            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(servIntent);
        super.onDestroy();
    }

    private void runService() {
        startService(servIntent);
    }

    private void SetRecycler(List<RoomChatRightShowResult> vm) {

        //  shimmerRecycler.showShimmerAdapter();
        shimmerRecycler.removeAllViews();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.scrollToPosition(0);
        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.setHasFixedSize(true);
        shimmerRecycler.setItemViewCacheSize(20);
        shimmerRecycler.setDrawingCacheEnabled(true);
        shimmerRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        ma = new Adaptor_chatRight(context, vm);
        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.setAdapter(ma);
    }


    private int findRoomChatLeft(int id) {
        int i = -1;
        for (RoomChatRightShowResult r : ma.vm) {
            i++;
            if (r.RoomChatGroupID == id) {
                return i;
            }
        }
        return -1;
    }

}