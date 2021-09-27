 package com.hesabischool.hesabiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hesabischool.hesabiapp.Clases.ExceptionHandler;
//import com.hesabischool.hesabiapp.Clases.ImageLoder_ASDE1373;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.Clases.hesabi_Risave;
import com.hesabischool.hesabiapp.Clases.hesabi_SignalR;

import com.hesabischool.hesabiapp.Interfasces.callForCheange;
import com.hesabischool.hesabiapp.Interfasces.callForCheangeMainChat;
import com.hesabischool.hesabiapp.Service.MyService;
import com.hesabischool.hesabiapp.adaptor.Adaptor_ContentShow;
import com.hesabischool.hesabiapp.adaptor.Adaptor_chatRight;
import com.hesabischool.hesabiapp.database.dbConnector;
import com.hesabischool.hesabiapp.database.dbQuerySelect;
import com.hesabischool.hesabiapp.viewmodel.vm_sendoflinechat;
import com.hesabischool.hesabiapp.vm_ModelServer.ChatMessage;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer3;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer4;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatContactResult;
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
import com.hesabischool.hesabiapp.ImageCash.ImageLoader;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomLiveViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainChat extends AppCompatActivity {
    RelativeLayout rel_searchbar;
    RelativeLayout rel_toolbar;
    ImageView img_back;
    ImageView img_search;
    EditText edt_search;
    Context context;
    dbQuerySelect dq;
    private Intent servIntent;
    Adaptor_chatRight ma;
    RecyclerView shimmerRecycler;
    hesabi_Risave hesabi_risave;
   // ImageLoader imgLoader;
    ImageView img_live;
    ImageView img_add;
    ImageLoader  imgLoader;
    DrawerLayout drawerLayout;
    ImageView menu;
    dbConnector db;
    public  static int postionItem;
    List<RoomChatRightShowResult> sValue=new ArrayList<>();
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
            db=new dbConnector(context);
            postionItem=0;
            shimmerRecycler = (RecyclerView) findViewById(R.id.rec_chat);
            rel_searchbar=findViewById(R.id.rel_searchbar);
            rel_toolbar=findViewById(R.id.rel_toolbar);
            img_back=findViewById(R.id.img_back);
            img_search=findViewById(R.id.img_search);
            edt_search=findViewById(R.id.edt_search);
            img_live=findViewById(R.id.img_live);
            img_add=findViewById(R.id.img_add);
            hesabi_SignalR hesabi_signalR = new hesabi_SignalR(context);
            dq = new dbQuerySelect(context);
            servIntent = new Intent(context, MyService.class);
            hesabi_risave = new hesabi_Risave(context);
            //   GetDataFromServer();
            ToolBar();
            runService();
            Risave();
            checkBundelNotification();
            NavigationViewCheck();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void ToolBar() {

        img_live.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
//                PopupMenu popupMenu = new PopupMenu(context,img_live);
//               // popupMenu.setForceShowIcon(true);
////                for (String s : array) {
////                    popup.getMenu().add(s);
////                }
//                    popupMenu.getMenu().add("sdsd").setIcon(R.drawable.bg_chat);
//                    popupMenu.getMenu().add("sdsd2");
//                    popupMenu.getMenu().add("sdsd263");
//                @SuppressLint("RestrictedApi") MenuPopupHelper menuHelper = new MenuPopupHelper(context, popupMenu,img_live);
//                menuHelper.setForceShowIcon(true); // show icons!!!!!!!!
//                menuHelper.show();
//              //  popupMenu.show();

                @SuppressLint("RestrictedApi") MenuBuilder menuBuilder = new MenuBuilder(context);
                try {
                    List<RoomLiveViewModel> lvm = (List<RoomLiveViewModel>) dq.SelesctList(new RoomLiveViewModel());

                    for(RoomLiveViewModel item:lvm)
                    {
                        if(item.LiveType==1)
                        {
                            menuBuilder.add(item.RoomChatGroupTitle).setIcon(R.drawable.liver_adobe);

                        }else if(item.LiveType==2)
                        {
                            menuBuilder.add(item.RoomChatGroupTitle).setIcon(R.drawable.live_gitsib);

                        }else if(item.LiveType==3)
                        {
                            menuBuilder.add(item.RoomChatGroupTitle).setIcon(R.drawable.live_zoom);
                        }


                    }

                    menuBuilder.setCallback(new MenuBuilder.Callback() {
                        @Override
                        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem menuItem) {
                            // your "setOnMenuItemClickListener" code goes here
                            for (int i=0;i<lvm.size();i++)
                            {
                                if(lvm.get(i).RoomChatGroupTitle.equals(menuItem.getTitle()))
                                {
                                   if(!app.check.EpmtyOrNull(lvm.get(i).LivePassword)&&!app.check.EpmtyOrNull(lvm.get(i).LiveUsername))
                                   {
                                     View vdl=app.Dialog_.dialog_creat(context,R.layout.dialog_live);
                                      TextView txt=vdl.findViewById(R.id.txt);
                                       Button btn=vdl.findViewById(R.id.btn_enter);
                                       String message="";
                                       if(!app.check.EpmtyOrNull(lvm.get(i).LiveUsername))
                                       {
                                        message+=" نام کاربری: "+lvm.get(i).LiveUsername+"\n";
                                       }
                                       if(!app.check.EpmtyOrNull(lvm.get(i).LivePassword))
                                       {
                                           message+=" کلمه عبور: "+lvm.get(i).LivePassword+"\n";

                                       }
                                       txt.setText(message);
                                       int finalI = i;
                                       btn.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               openweb(lvm.get(finalI).LiveAddress);
                                           }
                                       });
                                       app.Dialog_.show_dialog(context,vdl,true);

                                   }else
                                   {
                                       //todo open webview
                                       openweb(lvm.get(i).LiveAddress);
                                   }
                                    return false;
                                }

                            }
                            return false;
                        }

                        @Override
                        public void onMenuModeChange(MenuBuilder menu) {
                        }
                    });
                    MenuPopupHelper menuHelper = new MenuPopupHelper(context, menuBuilder, view);
                    menuHelper.setForceShowIcon(true); // show icons!!!!!!!!
                    menuHelper.show();


                                    } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            //    new SupportMenuInflater(context).inflate(R.menu.menu_main, menuBuilder);






            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(app.net.CheckCommunication(context))
                {
                    //todo goto get list
                    View vds=app.Dialog_.dialog_creat(context,R.layout.dialog_add);
                    RecyclerView res=vds.findViewById(R.id.rec_chat);
                    EditText edtsearch=vds.findViewById(R.id.edt_search);
                    final List<RoomChatContactResult>[] vm = new List[]{new ArrayList<>()};
                    AlertDialog a= app.Dialog_.show_dialog(context,vds,true);
                    edtsearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                             List<RoomChatContactResult> _vm = new ArrayList<>();
                             for(RoomChatContactResult item:vm[0])
                             {
                                 if(item.FullName.contains(editable.toString()))
                                 {
                                     _vm.add(item);
                                 }

                             }
                            SetRecyclerShowContextResualt(res,_vm,a);

                        }
                    });

                    app.progress.onCreateDialog(context);
                    app.retrofit.retrofit().RoomChatContactShow().enqueue(new Callback<GetDataFromServer4>() {
    @Override
    public void onResponse(Call<GetDataFromServer4> call, Response<GetDataFromServer4> response) {
        app.retrofit.erorRetrofit(response,context);
        if(response.isSuccessful())
        {
            vm[0] =response.body().value;
            SetRecyclerShowContextResualt(res,vm[0],a);
        }
    }

    @Override
    public void onFailure(Call<GetDataFromServer4> call, Throwable t) {
app.retrofit.FailRetrofit(t,context);
    }
});



                }
            }
        });
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edt_search.setText("");
                rel_toolbar.setVisibility(View.GONE);
                rel_searchbar.setVisibility(View.VISIBLE);
                try {
                     sValue = (List<RoomChatRightShowResult>) dq.SelesctListOrderByDesending(new RoomChatRightShowResult(), "RoomChatDate");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_search.setText("");
                rel_toolbar.setVisibility(View.VISIBLE);
                rel_searchbar.setVisibility(View.GONE);
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
if(app.check.EpmtyOrNull(editable.toString().trim()))
{
    Risave();
}else
{

        List<RoomChatRightShowResult> ss=new ArrayList<>();
        for (RoomChatRightShowResult item:sValue)
        {
            if(item.RoomChatTitle.contains(editable.toString()))
            {
                ss.add(item);
            }
        }
        //    List<RoomChatRightShowResult> ss= (List<RoomChatRightShowResult>) dq.SelesctList(new RoomChatRightShowResult());
        SetRecycler(ss);
}

            }
        });

    }
    private void openweb(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void NavigationViewCheck() {
        NavigationView nav = findViewById(R.id.nav);
        menu=findViewById(R.id.ic_menu);
        drawerLayout=findViewById(R.id.drawer);
        View headerView = nav.getHeaderView(0);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        //========================
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getTitle().equals("خروج"))
                {
                    db.DeleteValueAllTable();
                    Intent i=new Intent(context,Login.class);
                    startActivity(i);
                    finish();
              //  Toast.makeText(context, menuItem.getTitle(), Toast.LENGTH_SHORT).show();


                }
                return false;
            }
        });
//=======Header==========================
        TextView htxt_name=headerView.findViewById(R.id.txt_name);
        TextView htxt_mobile=headerView.findViewById(R.id.txt_mobile);
        CircleImageView imgprofile=headerView.findViewById(R.id.img_profile);
        htxt_name.setText(app.Info.User.fullName);
        htxt_mobile.setText(app.Info.User.mobileNumber);
        imgLoader = new ImageLoader(context);

     //   imgLoader = new ImageLoder_ASDE1373(context);

     //   String url = app.baseUrl.retrofit + app.baseUrl.picurl+"?picName="+app.Info.User.picName;

   //     imgLoader.LoadImage(imgprofile);




        imgLoader.DisplayPicture(app.Info.User.userID, imgprofile);



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
               // Toast.makeText(context, String.valueOf(ch.groupId), Toast.LENGTH_SHORT).show();
                ma.gotoDetilsmessage(ch);

            }
        }
    }

    private void Risave() {

        try {
            List<RoomChatRightShowResult> s = (List<RoomChatRightShowResult>) dq.SelesctListOrderByDesending(new RoomChatRightShowResult(), "RoomChatDate");
            //    List<RoomChatRightShowResult> ss= (List<RoomChatRightShowResult>) dq.SelesctList(new RoomChatRightShowResult());
         ArrayList<RoomChatRightShowResult> fs= new ArrayList<>();
fs= (ArrayList<RoomChatRightShowResult>) app.copyList(s);
      //   fs= (ArrayList<RoomChatRightShowResult>) s;
//            for(RoomChatRightShowResult r:fs)
//            {
//                if(r.RoomChatDate.equals("null")||app.check.EpmtyOrNull(r.RoomChatDate))
//                {
//                    RoomChatRightShowResult ss=new RoomChatRightShowResult();
//                    ss=r;
//                    fs.remove(r);
//                   fs.add(ss);
//
//                }
//            }
            try {
                for(RoomChatRightShowResult r:s)
            {
                if(r.RoomChatDate.equals("null")||app.check.EpmtyOrNull(r.RoomChatDate))
                {
                    RoomChatRightShowResult ss=new RoomChatRightShowResult();
                    ss=r;
                    fs.remove(r);
                   fs.add(ss);

                }
            }
//                for (int i=0;i<s.size();i++)
//                {
//                    RoomChatRightShowResult r=s.get(i);
//                    if(r.TeacherID==819)
//                    {
//                        String p=r.TextChat;
//                        r.TextChat=p+"dfdfdf";
//                    }
//                    if(r.RoomChatDate.trim().equals("null")||app.check.EpmtyOrNull(r.RoomChatDate))
//                    {
//                        // RoomChatRightShowResult ss=new RoomChatRightShowResult();
//                        //ss=r;
//                        fs.remove(i);
//                        fs.add(r);
//
//                    }else
//                    {
//                        String cv=r.RoomChatDate;
//                    }
//
//                }



                SetRecycler(fs);
            }catch (Exception ex)
            {
                throw ex;
            }


        } catch (IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        //   runService();
        app.Info.checkpage.curentActivity = "MainChat";
        app.Info.checkpage.callForCheangeMainChat = c;

       // Risave();
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
        LinearLayoutManager layoutManager =  new LinearLayoutManager(context);

        layoutManager.scrollToPosition(postionItem);
        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.setHasFixedSize(true);
       shimmerRecycler.setItemViewCacheSize(20);
        shimmerRecycler.setDrawingCacheEnabled(true);
        shimmerRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        ma = new Adaptor_chatRight(context, vm);
        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.setAdapter(ma);

    }
    private void SetRecyclerShowContextResualt(RecyclerView r,List<RoomChatContactResult> vm,AlertDialog aa) {

        //  shimmerRecycler.showShimmerAdapter();
        r.removeAllViews();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.scrollToPosition(0);
        r.setLayoutManager(layoutManager);
        r.setHasFixedSize(true);
        r.setItemViewCacheSize(20);
        r.setDrawingCacheEnabled(true);
        r.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Adaptor_ContentShow a = new Adaptor_ContentShow(context, vm,aa);
        r.setLayoutManager(layoutManager);
        r.setAdapter(a);
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