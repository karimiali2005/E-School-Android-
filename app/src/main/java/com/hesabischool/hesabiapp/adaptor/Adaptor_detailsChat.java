package com.hesabischool.hesabiapp.adaptor;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.hesabischool.hesabiapp.Clases.Download;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.DetilsChat;
import com.hesabischool.hesabiapp.Interfasces.LayzyLoad;
import com.hesabischool.hesabiapp.Interfasces.callForCheange;
import com.hesabischool.hesabiapp.PdfActivity;
import com.hesabischool.hesabiapp.R;
import com.hesabischool.hesabiapp.ForgerunadService.DownloadService;
import com.hesabischool.hesabiapp.database.dbQuerySelect;
import com.hesabischool.hesabiapp.viewmodel.vm_upload;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer6;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatForwardUser;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftPropertyResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftShowResult;
import com.potyvideo.library.AndExoPlayerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ACTIVITY_SERVICE;

public class Adaptor_detailsChat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    public List<RoomChatLeftShowResult> vm=new ArrayList<>();
    public int scroled = 0;
    LayzyLoad layzyLoad;
    dbQuerySelect dqs;
  public boolean select=false;
  FloatingActionButton fab_down;
    callForCheange callForCheange;
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static final String MESSAGE_PROGRESS = "message_progress";
    RoomChatLeftPropertyResult rPropertyResult;
//For Message Dont Read
   public int size2=-1;
    public Adaptor_detailsChat(Context context, List<RoomChatLeftShowResult> vm, LayzyLoad layzyLoad, callForCheange callForCheange,FloatingActionButton fab_down ,int size2) {
        this.context = context;
        this.vm = vm;
        this.layzyLoad = layzyLoad;
        dqs = new dbQuerySelect(context);
        this.callForCheange = callForCheange;
        this.fab_down=fab_down;
this.size2=size2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        //0 is my
        //1 is other
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mymessage, parent, false);
                return new mysendchat(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_othermessage, parent, false);
                return new othersendchat(view);
        }
        return null;
    }
    String currentDateShow= "2000-02-02";
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        final RoomChatLeftShowResult lvm = vm.get(position);





        switch (ismymesssage(position)) {
            case 0:
                setchatForMe(holder, position);
                break;
            case 1:
                setchatForOther(holder, position);
                break;
        }

        if (lvm !=null && lvm.RoomChatDate!=null && currentDateShow.compareTo(lvm.RoomChatDate.substring(0,10) )!=0)
        {

            currentDateShow =lvm.RoomChatDate.substring(0,10) ;

            if(holder instanceof mysendchat)
            {
                ((mysendchat)holder).rel_ShowDate.setVisibility(View.VISIBLE);
                ((mysendchat)holder).rel_ShowDateText.setText(app.Convert.CovertToPersianDateSmall(currentDateShow) );

            }else
            {
                ((othersendchat)holder).rel_ShowDate.setVisibility(View.VISIBLE);
                ((othersendchat)holder).rel_ShowDateText.setText(app.Convert.CovertToPersianDateSmall(currentDateShow));
            }
        }

//Check For Show Message Dont Read
        if(position==size2)
        {
            size2=-1;
            if(holder instanceof mysendchat)
            {
                ((mysendchat)holder).rel_noReadMessage.setVisibility(View.VISIBLE);

            }else
            {
                ((othersendchat)holder).rel_noReadMessage.setVisibility(View.VISIBLE);
            }

        }else
        {
            if(holder instanceof mysendchat)
            {
                ((mysendchat)holder).rel_noReadMessage.setVisibility(View.GONE);

            }else
            {
                ((othersendchat)holder).rel_noReadMessage.setVisibility(View.GONE);
            }
        }

//        if (position == scroled) {
//            // its a last item
//            layzyLoad.call();
//
//        }

        if(select)
        {

            cheangeBackgerandForSeconds(holder);
            select=false;
        }


    }

    private void setchatForMe(final RecyclerView.ViewHolder holder, final int position) {
        ((mysendchat) holder).lin_video.removeAllViews();
        ((mysendchat) holder).lin_audeo.removeAllViews();
        //  lin_img.removeAllViews();
        ((mysendchat) holder).lin_file.removeAllViews();

        ((mysendchat) holder).lin_img.setVisibility(View.GONE);
        ((mysendchat) holder).lin_video.setVisibility(View.GONE);
        ((mysendchat) holder).lin_audeo.setVisibility(View.GONE);
        ((mysendchat) holder).lin_file.setVisibility(View.GONE);

        final RoomChatLeftShowResult lvm = vm.get(position);

        if(lvm.TagLearn)
        {
            ((mysendchat) holder).txttag.setVisibility(View.VISIBLE);
        }else
        {
            ((mysendchat) holder).txttag.setVisibility(View.GONE);
        }

        if(lvm.RoomChatParentID==0)
        {
            ((mysendchat) holder).rel_parent.setVisibility(View.GONE);
        }

        ((mysendchat) holder).Rel.setTag(lvm.RoomChatID);
        ((mysendchat) holder).txt_time.setText(lvm.RoomChatDateString);
        ((mysendchat) holder).txt_countsee.setText(String.valueOf(lvm.RoomChatViewNumber));
        if (lvm.IsSqlLite) {
            ((mysendchat) holder).img_issqllite.setVisibility(View.VISIBLE);
        } else {
            ((mysendchat) holder).img_issqllite.setVisibility(View.GONE);
        }

        if (app.check.EpmtyOrNull(lvm.Filename)) {
            ((mysendchat) holder).lin_text.setVisibility(View.VISIBLE);
            // File is Text
            if (lvm.RoomChatParentID != 0) {
                //todo has Parents
                ((mysendchat) holder).rel_parent.setVisibility(View.VISIBLE);
                String text=Html.fromHtml(lvm.ParentTextChat).toString();
                ((mysendchat) holder).txt_sumrytextparents.setText(text);
                ((mysendchat) holder).txtparentname.setText(lvm.ParentSenderName);


            }
            else {
                //todo No parents
                ((mysendchat) holder).rel_parent.setVisibility(View.GONE);

            }
            String message = Html.fromHtml(lvm.TextChat).toString();
            ((mysendchat) holder).txtmessage.setText(message);

        }
        else {
            // File Not a Text Type
            if (!app.check.EpmtyOrNull(lvm.TextChat)) {
                ((mysendchat) holder).lin_text.setVisibility(View.VISIBLE);
                String message = Html.fromHtml(lvm.TextChat).toString();
                ((mysendchat) holder).txtmessage.setText(message);

            } else {
                ((mysendchat) holder).lin_text.setVisibility(View.GONE);
            }

            List<String> mime_types_images = new ArrayList<>();
            mime_types_images.add("image/jpeg");
            mime_types_images.add("image/jpg");
            mime_types_images.add("image/png");
            mime_types_images.add("image/gif");

            List<String> mime_types_audios = new ArrayList<>();
            mime_types_audios.add("audio/wav");
            mime_types_audios.add("audio/mp3");
            mime_types_audios.add("audio/ogg");
            mime_types_audios.add("audio/mpeg");
            mime_types_audios.add("audio/mp4");
            mime_types_audios.add("audio/aac");
            mime_types_audios.add("video/ogg");


            List<String> mime_types_videos = new ArrayList<>();
            mime_types_videos.add("video/mp4");
            mime_types_videos.add("video/webm");
            mime_types_videos.add("video/quicktime");
            if (lvm.RoomChatParentID != 0) {
                //todo has Parents
                ((mysendchat) holder).rel_parent.setVisibility(View.VISIBLE);
                String text=Html.fromHtml(lvm.ParentTextChat).toString();
                ((mysendchat) holder).txt_sumrytextparents.setText(text);
                ((mysendchat) holder).txtparentname.setText(lvm.ParentSenderName);


            } else {
                //todo No parents
                ((mysendchat) holder).rel_parent.setVisibility(View.GONE);

            }




            String urlAdress ="";
            if(app.check.EpmtyOrNull(lvm.RoomChatDate))
            {
                urlAdress=lvm.mainAdress;
            }else
            {
               // urlAdress= ((lvm.TagLearn) ? app.Info.LearnFile : app.Info.NormalFile)+formateDateDownLoad(lvm.RoomChatDate)+"/"+ lvm.Filename;
                urlAdress= ((lvm.TagLearn) ? app.Info.LearnFile : app.Info.NormalFile)+lvm.RoomChatFolder+"/"+ lvm.Filename;

            }
            if (mime_types_images.indexOf(lvm.MimeType) >= 0) {

                ((mysendchat) holder).lin_img.setVisibility(View.VISIBLE);


                if (fileExist(lvm.RoomChatID, lvm.Filename)) {
                 //   ((mysendchat) holder).img_shower.setVisibility(View.VISIBLE);
                    showImage(holder, position);
                } else {
                    //Todo Dowanload file
                    gotodowanloadfile_Image(((mysendchat) holder).lin_img, lvm.Filename, urlAdress, position, holder);
                }

            }
            else if (mime_types_audios.indexOf(lvm.MimeType) >= 0)
            {//audio
                ((mysendchat) holder).lin_audeo.setVisibility(View.VISIBLE);
                if (fileExist(lvm.RoomChatID, lvm.Filename)) {
                    playAudeo(holder, position);
                } else {
                    //Todo Dowanload file
                    gotodowanloadfile_Audeo(((mysendchat) holder).lin_audeo, lvm.Filename, urlAdress, position, holder);
                }


//End Audeo
            }
            else if (mime_types_videos.indexOf(lvm.MimeType) >= 0) {
                //video
                ((mysendchat) holder).lin_video.setVisibility(View.VISIBLE);
                if (fileExist(lvm.RoomChatID, lvm.Filename)) {
                    //Show Video
                    playVideo(holder, position);
                } else {
                    //doawnload
                    gotodowanloadfile_Video(((mysendchat) holder).lin_video, lvm.Filename, urlAdress, position, holder);

                }

            }
            else {
                //todo For unkonw File
                ((mysendchat) holder).lin_file.setVisibility(View.VISIBLE);
                if (fileExist(lvm.RoomChatID, lvm.Filename)) {
                    //Show Video

                    showFile(holder, position);
                }
                else {
                    //doawnload
                    gotodowanloadfile_File(((mysendchat) holder).lin_file, lvm.Filename, urlAdress, position, holder);

                }
//                ((mysendchat) holder).lin_video.removeAllViews();
//                TextView text = new TextView(context);
//                text.setText("File Not unknow....");
//                ((mysendchat) holder).lin_video.setVisibility(View.VISIBLE);
//                ((mysendchat) holder).lin_video.addView(text);
            }

        }

        ((mysendchat) holder).rel_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callForCheange.gotoPostionItem(lvm.RoomChatParentID);
            }
        });


        //=============================PopUp=========================
        ((mysendchat) holder).img_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(context, ((mysendchat) holder).img_popup);
              rPropertyResult=callForCheange.getPeroperty();
//              if(app.Info.User.userTypeID==1)
//              {
//                  //Student
//                  if(rPropertyResult.PermissionStudentChatDelete)
//                  {
//                      menu.getMenu().add("حذف");
//                  }
//                  if (rPropertyResult.PermissionStudentChatEdit)
//                  {
//                      menu.getMenu().add("ویرایش");
//                  }
//
//              }

              if (app.Info.User.userTypeID==4)
              {
               //Teacher
                  menu.getMenu().add("سنجاق");
              }
                menu.getMenu().add("حذف");
                menu.getMenu().add("ویرایش");
                menu.getMenu().add("پاسخ");
                menu.getMenu().add("ارسال");
             openmenu(menu,lvm);
            }
        });

    }

//    private String formateDateDownLoad(String roomChatDate) {
//        // https://hesabidownload.ir/hesabischoolfiles/Normal/Month20210923/b2c95615723748a39ccaec5e41c66fe2.jpg
//
//      //  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//       // DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
////      try{
////          if(!app.check.EpmtyOrNull(roomChatDate))
////          {
////              Date date=app.Convert.CovertStringToDate(roomChatDate);
////              String fDate = new SimpleDateFormat("yyyyMMdd").format(date);
////              fDate=app.Convert.ConvertFAToEN(fDate);
////              return "Month"+fDate;
////          }
////
////
////      } catch (Exception ex)
////      {
////
////
////      }
////        Toast.makeText(context, roomChatDate, Toast.LENGTH_SHORT).show();
////        return  "";
//    }

    private void showFile(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof mysendchat) {
                ((mysendchat) holder).lin_file.removeAllViews();
            } else {
                //TODO FOR OTHER
                ((othersendchat) holder).lin_file.removeAllViews();
            }
            View viewFILE = View.inflate(context, R.layout.item_open_file, null);
            final Button btn_download = viewFILE.findViewById(R.id.btn_download);

            btn_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // File file = new File(getFileUri(vm.get(position).RoomChatID, vm.get(position).Filename).getPath());
if(vm.get(position).MimeType.contains("application/pdf")&&vm.get(position).MimeType.contains("ASDE"))
{
//app.Info.PdfUri=getFileUri(vm.get(position).RoomChatID, vm.get(position).Filename);
app.Info.PdfUri=getFileContentUri(vm.get(position).RoomChatID, vm.get(position).Filename);
Intent pdfintent=new Intent(context, PdfActivity.class);
context.startActivity(pdfintent);
}else
{

    LoadFile(vm.get(position).RoomChatID, vm.get(position).Filename,vm.get(position).MimeType);
   /* Intent target = new Intent(Intent.ACTION_VIEW);

    //target.setDataAndType(Uri.fromFile(file),"application/pdf");
    // target.setDataAndType(Uri.fromFile(file),"vm.get(position).MimeType");
    Uri uri=getFileContentUri(vm.get(position).RoomChatID, vm.get(position).Filename);
    target.setDataAndType(uri,vm.get(position).MimeType);

    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);

    Intent intent = Intent.createChooser(target, "بازکردن با");
    try {
        context.startActivity(intent);
    } catch (ActivityNotFoundException e) {
        // Instruct the user to install a PDF reader here, or something
    }*/
}


                }
            });


            if (holder instanceof mysendchat) {
                ((mysendchat) holder).lin_file.addView(viewFILE);
            } else {
                //todo
                ((othersendchat) holder).lin_file.addView(viewFILE);
            }




    }

    private void openmenu(PopupMenu menu, RoomChatLeftShowResult lvm) {

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (menuItem.getTitle().equals("پاسخ")) {
                    if(DetilsChat.islock&&app.Info.User.userTypeID!=4)
                    {
                        Toast.makeText(context, "امکان پاسخ وجود ندارد ", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        callForCheange.ReplayMessage(lvm,false);
                    }

                }
                else if (menuItem.getTitle().equals("ویرایش")) {
                    //Edite
                    if(DetilsChat.islock&&app.Info.User.userTypeID!=4)
                    {
                        Toast.makeText(context, "امکان ویرایش وجود ندارد ", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        callForCheange.ReplayMessage(lvm,true);
                    }

                }
                else if (menuItem.getTitle().equals("حذف")) {
                    //Delete
                    if(DetilsChat.islock&&app.Info.User.userTypeID!=4)
                    {
                        Toast.makeText(context, "امکان حذف وجود ندارد ", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        callForCheange.DialogDeleteMessage(lvm);
                    }

                }
                else if (menuItem.getTitle().equals("ارسال")) {
                    //Forward
                    //todo Forward messsage
                    if(app.net.CheckCommunication(context))
                    {
                        app.Info.forwardList=new ArrayList<>();
                        //todo goto get list
                        View vds=app.Dialog_.dialog_creat(context,R.layout.dialog_forward);
                        AlertDialog a= app.Dialog_.show_dialog(context,vds,true);
                        RecyclerView res=vds.findViewById(R.id.rec_chat);
                        EditText edtsearch=vds.findViewById(R.id.edt_search);
                        Button btnsend=vds.findViewById(R.id.btn_send);
                        ImageView img_close=vds.findViewById(R.id.img_close);
btnsend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(app.Info.forwardList!=null&&app.Info.forwardList.size()>0)
        {
            String listitem="";
            for (int item:app.Info.forwardList)
            {
                listitem+=String.valueOf(item)+",";
            }
            app.progress.onCreateDialog(context);
            app.retrofit.retrofit().RoomChatForwardSend(listitem,lvm.RoomChatID).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    app.retrofit.erorRetrofit(response,context);
                    if(response.isSuccessful())
                    {
                        Toast.makeText(context, "با موفقیت ارسال شد", Toast.LENGTH_SHORT).show();
                        app.Dialog_.dimos_dialog(a);
                    }

                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
app.retrofit.FailRetrofit(t,context);
                }
            });
        }
        else
        {
            Toast.makeText(context, "هیچ موردی برای ارسال انتخاب نشده است ", Toast.LENGTH_SHORT).show();
        }
    }
});
                        final List<RoomChatForwardUser>[] vm2 = new List[]{new ArrayList<>()};

                        img_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                app.Dialog_.dimos_dialog(a);
                            }
                        });


                        edtsearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                                List<RoomChatForwardUser> _vm = new ArrayList<>();
                                for(RoomChatForwardUser item:vm2[0])
                                {
                                    if(item.GroupTitle.contains(editable.toString()))
                                    {
                                        _vm.add(item);
                                    }

                                }
                                SetRecyclerShowForwardContextResualt(res,_vm,a);

                            }
                        });

                        app.progress.onCreateDialog(context);
                        app.retrofit.retrofit().RoomChatForwardUserShow(lvm.RoomChatID,lvm.RoomChatGroupID).enqueue(new Callback<GetDataFromServer6>() {
                            @Override
                            public void onResponse(Call<GetDataFromServer6> call, Response<GetDataFromServer6> response) {
                                app.retrofit.erorRetrofit(response,context);
                                if(response.isSuccessful())
                                {
                                    vm2[0] =response.body().value;
                                    SetRecyclerShowForwardContextResualt(res,vm2[0],a);
                                }
                            }

                            @Override
                            public void onFailure(Call<GetDataFromServer6> call, Throwable t) {
                                app.retrofit.FailRetrofit(t,context);
                            }
                        });



                    }
                }
                else if (menuItem.getTitle().equals("سنجاق")) {
                    //pin
                    callForCheange.SetpinMesage(lvm);
                }

                return false;
            }
        });
        menu.show();

    }

    private void SetRecyclerShowForwardContextResualt(RecyclerView r, List<RoomChatForwardUser> vm, AlertDialog aa) {

        r.removeAllViews();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.scrollToPosition(0);
        r.setLayoutManager(layoutManager);
        r.setHasFixedSize(true);
        r.setItemViewCacheSize(20);
        r.setDrawingCacheEnabled(true);
        r.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Adaptor_ForwardShow a = new Adaptor_ForwardShow(context, vm,aa);
        r.setLayoutManager(layoutManager);
        r.setAdapter(a);
    }


    private void gotodowanloadfile_Video(final View v, final String filename, final String fileadress, final int position, final RecyclerView.ViewHolder holder) {
        ((LinearLayout) v).removeAllViews();
        final View viewDowanload = View.inflate(context, R.layout.item_dowanload_video, null);
        Button btndowanload = viewDowanload.findViewById(R.id.btn_download);
        final TextView progress_text = viewDowanload.findViewById(R.id.progress_text);
        final ProgressBar progress = viewDowanload.findViewById(R.id.progress);
        ((LinearLayout) v).addView(viewDowanload);
        final boolean[] click = {false};
        btndowanload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    click[0] =!click[0];
                    if(click[0])
                    {
                        app.Info.Filename = filename;
                        app.Info.Fileadress = fileadress;
                        app.Info.Fileview=v;
                        startDownload();
                    }else
                    {
                        //todo close dowanload
                        CloseDownload();
                    }



                } else {
                    requestPermission();
                }
            }
        });


        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(MESSAGE_PROGRESS)&&app.Info.Fileview==v) {


                    Download download = intent.getParcelableExtra("download");
                  progress.setProgress(download.getProgress());
                    if (download.getProgress() == 100) {
                        app.Info.isAllowDowanload=true;
                        progress_text.setText("دانلود کامل شد");
                        ((LinearLayout) app.Info.Fileview).removeView(viewDowanload);
                        playVideo(holder, position);

                    } else {

                        progress_text.setText(String.format("دانلود (%d/%d) MB", download.getCurrentFileSize(), download.getTotalFileSize()));

                    }
                }
            }
        };
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void gotodowanloadfile_File(final View v, final String filename, final String fileadress, final int position, final RecyclerView.ViewHolder holder) {
        ((LinearLayout) v).removeAllViews();
        final View viewDowanload = View.inflate(context, R.layout.item_dowanload_file, null);
        Button btndowanload = viewDowanload.findViewById(R.id.btn_download);
        final TextView progress_text = viewDowanload.findViewById(R.id.progress_text);
        final ProgressBar progress = viewDowanload.findViewById(R.id.progress);
        if (holder instanceof mysendchat) {
            ((mysendchat) holder).lin_file.addView(viewDowanload);
        } else {
            //todo
            ((othersendchat) holder).lin_file.addView(viewDowanload);
        }
       // ((LinearLayout) v).addView(viewDowanload);
        btndowanload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    app.Info.Filename = filename;
                    app.Info.Fileadress = fileadress;
                    app.Info.Fileview=v;
                    startDownload();

                } else {
                    requestPermission();
                }
            }
        });


        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(MESSAGE_PROGRESS)&&app.Info.Fileview==v) {


                    Download download = intent.getParcelableExtra("download");
                    progress.setProgress(download.getProgress());
                    if (download.getProgress() == 100) {
                        app.Info.isAllowDowanload=true;
                        progress_text.setText("دانلود کامل شد");
                        ((LinearLayout) app.Info.Fileview).removeView(viewDowanload);
                        showFile(holder, position);

                    } else {

                        progress_text.setText(String.format("دانلود (%d/%d) MB", download.getCurrentFileSize(), download.getTotalFileSize()));

                    }
                }
            }
        };
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void playVideo(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof mysendchat) {
            ((mysendchat) holder).lin_video.removeAllViews();
        } else {
            ((othersendchat) holder).lin_video.removeAllViews();
        }
        View viewvideo = View.inflate(context, R.layout.item_playvideo, null);
        final ImageView imgplay = viewvideo.findViewById(R.id.imgplay);

        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = app.Dialog_.dialog_creat(context, R.layout.dilaog_play_video);
              //  final BetterVideoPlayer player = v.findViewById(R.id.bvp);
                AndExoPlayerView andExoPlayerView = v.findViewById(R.id.andExoPlayerView);
                final CardView cardclose = v.findViewById(R.id.cardclose);
               // andExoPlayerView.setSource(getFileUri(vm.get(position).RoomChatID, vm.get(position).Filename).toString());
                HashMap<String , String> extraHeaders = new HashMap<>();
                int k=vm.get(position).RoomChatID;
                String st2= vm.get(position).Filename;
                String st=getFileUri(vm.get(position).RoomChatID, vm.get(position).Filename).toString();
                andExoPlayerView.setSource(getFileUri(vm.get(position).RoomChatID, vm.get(position).Filename).toString(),extraHeaders);
                final AlertDialog al = app.Dialog_.show_dialog(context, v);

                cardclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        al.dismiss();

                    }
                });
            }
        });
        if (holder instanceof mysendchat) {
            ((mysendchat) holder).lin_video.addView(viewvideo);
        } else {
            ((othersendchat) holder).lin_video.addView(viewvideo);
        }
    }

    private void playAudeo(final RecyclerView.ViewHolder holder, final int position) {
        //todo play file
        final String finalUrlAdress1 = "";
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (holder instanceof mysendchat) {
                    ((mysendchat) holder).lin_audeo.removeAllViews();
                } else {
                    ((othersendchat) holder).lin_audeo.removeAllViews();
                }

                final Handler seekHandler = new Handler();
                final Runnable[] run = new Runnable[1];

                View viewaudeo = View.inflate(context, R.layout.item_audeoplayer, null);
                final SeekBar seekbar = viewaudeo.findViewById(R.id.seekbar);
                final TextView texttime = viewaudeo.findViewById(R.id.texttime);
                final ImageView play = viewaudeo.findViewById(R.id.play);

                final MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(context, getFileUri(vm.get(position).RoomChatID, vm.get(position).Filename));
                    mediaPlayer.prepare();// might take long for buffering.
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //  mediaPlayer.create(context,getFileUri(vm.get(position).Filename));
                mediaPlayer.setLooping(false);

                seekbar.setMax(mediaPlayer.getDuration());
                seekbar.setTag(position);
                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (mediaPlayer != null && fromUser) {
                            mediaPlayer.seekTo(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                //AudioPlayerView audioPlayerView =viewaudeo.findViewById(R.id.audeo);
                //  audioPlayerView.withUrl(urlAdress);
                texttime.setText("0:00/" + calculateDuration(mediaPlayer.getDuration()));
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.pause();
                        play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    }
                });
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!mediaPlayer.isPlaying()) {
                            mediaPlayer.start();
                            play.setImageResource(R.drawable.ic_baseline_stop_24);
                            run[0] = new Runnable() {
                                @Override
                                public void run() {
                                    // Updateing SeekBar every 100 miliseconds
                                    seekbar.setProgress(mediaPlayer.getCurrentPosition());
                                    seekHandler.postDelayed(run[0], 100);
                                    //For Showing time of audio(inside runnable)
                                    int miliSeconds = mediaPlayer.getCurrentPosition();
                                    if (miliSeconds != 0) {
                                        //if audio is playing, showing current time;
                                        long minutes = TimeUnit.MILLISECONDS.toMinutes(miliSeconds);
                                        long seconds = TimeUnit.MILLISECONDS.toSeconds(miliSeconds);
                                        if (minutes == 0) {
                                            texttime.setText("0:" + seconds + "/" + calculateDuration(mediaPlayer.getDuration()));
                                        } else {
                                            if (seconds >= 60) {
                                                long sec = seconds - (minutes * 60);
                                                texttime.setText(minutes + ":" + sec + "/" + calculateDuration(mediaPlayer.getDuration()));
                                            }
                                        }
                                    } else {
                                        //Displaying total time if audio not playing
                                        int totalTime = mediaPlayer.getDuration();
                                        long minutes = TimeUnit.MILLISECONDS.toMinutes(totalTime);
                                        long seconds = TimeUnit.MILLISECONDS.toSeconds(totalTime);
                                        if (minutes == 0) {
                                            texttime.setText("0:" + seconds);
                                        } else {
                                            if (seconds >= 60) {
                                                long sec = seconds - (minutes * 60);
                                                texttime.setText(minutes + ":" + sec);
                                            }
                                        }
                                    }
                                }

                            };
                            run[0].run();
                        } else {
                            mediaPlayer.pause();
                            play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                        }
                    }
                });

                if (holder instanceof mysendchat) {
                    ((mysendchat) holder).lin_audeo.addView(viewaudeo);
                } else {
                    ((othersendchat) holder).lin_audeo.addView(viewaudeo);
                }

            }
        });


    }

    private void gotodowanloadfile_Audeo(final View v, final String filename, final String fileadress, final int position, final RecyclerView.ViewHolder holder) {
        ((LinearLayout) v).removeAllViews();
        final View viewDowanload = View.inflate(context, R.layout.item_dowanload_audeo, null);
        Button btndowanload = viewDowanload.findViewById(R.id.btn_download);
        final TextView progress_text = viewDowanload.findViewById(R.id.progress_text);
        final ProgressBar progress = viewDowanload.findViewById(R.id.progress);
        ((LinearLayout) v).addView(viewDowanload);
        btndowanload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    app.Info.Filename = filename;
                    app.Info.Fileadress = fileadress;
                    app.Info.Fileview = v;
                    startDownload();

                } else {
                    requestPermission();
                }
            }
        });


        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(MESSAGE_PROGRESS)&&app.Info.Fileview==v) {

                    Download download = intent.getParcelableExtra("download");
                    progress.setProgress(download.getProgress());
                    if (download.getProgress() == 100) {
                        app.Info.isAllowDowanload=true;
                        progress_text.setText("فایل دانلود شد");
                        ((LinearLayout) app.Info.Fileview).removeView(viewDowanload);
                        playAudeo(holder, position);

                    } else {

                        progress_text.setText(String.format("در حال دانلود (%d/%d) MB", download.getCurrentFileSize(), download.getTotalFileSize()));

                    }
                }
            }
        };
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    private void setchatForOther(final RecyclerView.ViewHolder holder, int position) {

        ((othersendchat) holder).lin_video.removeAllViews();
        ((othersendchat) holder).lin_audeo.removeAllViews();
        // r).lin_img.removeAllViews();
        ((othersendchat) holder).lin_file.removeAllViews();

       ((othersendchat) holder).lin_img.setVisibility(View.GONE);
       ((othersendchat) holder).lin_video.setVisibility(View.GONE);
       ((othersendchat) holder).lin_audeo.setVisibility(View.GONE);
       ((othersendchat) holder).lin_file.setVisibility(View.GONE);

        RoomChatLeftShowResult lvm = vm.get(position);

        if(lvm.TagLearn)
        {
            ((othersendchat) holder).txttag.setVisibility(View.VISIBLE);
        }else
        {
            ((othersendchat) holder).txttag.setVisibility(View.GONE);
        }

        if(lvm.RoomChatParentID==0)
        {
            ((othersendchat) holder).rel_parent.setVisibility(View.GONE);
        }

        ((othersendchat) holder).Rel.setTag(lvm.RoomChatID);
        ((othersendchat) holder).txt_time.setText(lvm.RoomChatDateString);
        ((othersendchat) holder).txt_countsee.setText(String.valueOf(lvm.RoomChatViewNumber));
        ((othersendchat) holder).txtnamesender.setText(lvm.SenderName);

        if (app.check.EpmtyOrNull(lvm.Filename)) {
            ((othersendchat) holder).lin_text.setVisibility(View.VISIBLE);
            // File is Text
            if (lvm.RoomChatParentID != 0) {
                //todo has Parents
                ((othersendchat) holder).rel_parent.setVisibility(View.VISIBLE);
                String text=Html.fromHtml(lvm.ParentTextChat).toString();
                ((othersendchat) holder).txt_sumrytextparents.setText(text);
                ((othersendchat) holder).txtparentname.setText(lvm.ParentSenderName);


            } else {
                //todo No parents
                ((othersendchat) holder).rel_parent.setVisibility(View.GONE);

            }
            String message = Html.fromHtml(lvm.TextChat).toString();
            ((othersendchat) holder).txtmessage.setText(message);

        }
        else {
            // File Not a Text Type
            if (!app.check.EpmtyOrNull(lvm.TextChat)) {
                ((othersendchat) holder).lin_text.setVisibility(View.VISIBLE);
                String message = Html.fromHtml(lvm.TextChat).toString();
                ((othersendchat) holder).txtmessage.setText(message);

            } else {
                ((othersendchat) holder).lin_text.setVisibility(View.GONE);
            }
            List<String> mime_types_images = new ArrayList<>();
            mime_types_images.add("image/jpeg");
            mime_types_images.add("image/png");
            mime_types_images.add("image/gif");

            List<String> mime_types_audios = new ArrayList<>();
            mime_types_audios.add("audio/wav");
            mime_types_audios.add("audio/mp3");
            mime_types_audios.add("audio/ogg");
            mime_types_audios.add("audio/mpeg");
            mime_types_audios.add("audio/mp4");
            mime_types_audios.add("audio/aac");
            mime_types_audios.add("video/ogg");


            List<String> mime_types_videos = new ArrayList<>();
            mime_types_videos.add("video/mp4");
            mime_types_videos.add("video/webm");
            mime_types_videos.add("video/quicktime");
            if (lvm.RoomChatParentID != 0) {
                //todo has Parents
                ((othersendchat) holder).rel_parent.setVisibility(View.VISIBLE);
                String text=Html.fromHtml(lvm.ParentTextChat).toString();
                ((othersendchat) holder).txt_sumrytextparents.setText(text);
                ((othersendchat) holder).txtparentname.setText(lvm.ParentSenderName);


            } else {
                //todo No parents
                ((othersendchat) holder).rel_parent.setVisibility(View.GONE);

            }
          //  String urlAdress = ((lvm.TagLearn) ? app.Info.LearnFile : app.Info.NormalFile) + lvm.Filename;
            String urlAdress ="";
            if(app.check.EpmtyOrNull(lvm.RoomChatDate))
            {
                urlAdress=lvm.mainAdress;
            }else
            {
               // urlAdress= ((lvm.TagLearn) ? app.Info.LearnFile : app.Info.NormalFile)+formateDateDownLoad(lvm.RoomChatDate)+"/"+ lvm.Filename;
                urlAdress= ((lvm.TagLearn) ? app.Info.LearnFile : app.Info.NormalFile)+lvm.RoomChatFolder+"/"+ lvm.Filename;

            }
            if (mime_types_images.indexOf(lvm.MimeType) >= 0) {
                ((othersendchat) holder).lin_img.setVisibility(View.VISIBLE);

               /* urlAdress=app.Info.Path1+urlAdress;
                ((othersendchat) holder).lin_img.setVisibility(View.VISIBLE);
                //Image
                final String finalUrlAdress = urlAdress;
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.get()
                                .load(finalUrlAdress)
                                .into(((othersendchat) holder).img_shower);
                    }
                });*/
                if (fileExist(lvm.RoomChatID, lvm.Filename)) {

                    showImage(holder, position);
                } else {
                    //Todo Dowanload file
                    gotodowanloadfile_Image(((othersendchat) holder).lin_img, lvm.Filename, urlAdress, position, holder);
                }


            }
            else if (mime_types_audios.indexOf(lvm.MimeType) >= 0) {//audio
                ((othersendchat) holder).lin_audeo.setVisibility(View.VISIBLE);
                if (fileExist(lvm.RoomChatID, lvm.Filename)) {
                    playAudeo(holder, position);
                } else {
                    //Todo Dowanload file
                    gotodowanloadfile_Audeo(((othersendchat) holder).lin_audeo, lvm.Filename, urlAdress, position, holder);
                }


//End Audeo
            }
            else if (mime_types_videos.indexOf(lvm.MimeType) >= 0) {
                //video
                ((othersendchat) holder).lin_video.setVisibility(View.VISIBLE);
                if (fileExist(lvm.RoomChatID, lvm.Filename)) {
                    //Show Video
                    playVideo(holder, position);
                } else {
                    //doawnload
                    gotodowanloadfile_Video(((othersendchat) holder).lin_video, lvm.Filename, urlAdress, position, holder);

                }

            }
            else {
                //todo For unkonw File
                ((othersendchat) holder).lin_file.setVisibility(View.VISIBLE);
                if (fileExist(lvm.RoomChatID, lvm.Filename)) {
                    //Show Video

                    showFile(holder, position);
                }
                else {
                    //doawnload
                    gotodowanloadfile_File(((othersendchat) holder).lin_file, lvm.Filename, urlAdress, position, holder);

                }}
        }


        ((othersendchat) holder).rel_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callForCheange.gotoPostionItem(lvm.RoomChatParentID);
            }
        });
        //=============================PopUp=========================
        ((othersendchat) holder).img_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(context, ((othersendchat) holder).img_popup);
                rPropertyResult=callForCheange.getPeroperty();
                if(DetilsChat.isAdvertise==false) {
                    if (app.Info.User.userTypeID == 4) {
                        //Teacher
                        menu.getMenu().add("حذف");
                        menu.getMenu().add("ویرایش");
                        menu.getMenu().add("سنجاق");
                    }

                    menu.getMenu().add("پاسخ");
                    menu.getMenu().add("ارسال");
                    openmenu(menu, lvm);
                }
            }
        });

    }

    private void gotodowanloadfile_Image(final View v, final String filename, final String urlAdress, final int position, final RecyclerView.ViewHolder holder) {
        ((LinearLayout) v).removeAllViews();

        final View viewDowanload = View.inflate(context, R.layout.item_dowanload_image, null);
        Button btndowanload = viewDowanload.findViewById(R.id.btn_download);
        final TextView progress_text = viewDowanload.findViewById(R.id.progress_text);
        final ProgressBar progress = viewDowanload.findViewById(R.id.progress);
        ((LinearLayout) v).addView(viewDowanload);
        btndowanload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    int po = position;
                    app.Info.Filename = filename;
                    app.Info.Fileadress = urlAdress;
                    app.Info.Fileview = v;
                    startDownload();

                } else {
                    requestPermission();
                }
            }
        });


        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(MESSAGE_PROGRESS)&&app.Info.Fileview==v) {

                    Download download = intent.getParcelableExtra("download");
                    progress.setProgress(download.getProgress());
                    if (download.getProgress() == 100) {
                        app.Info.isAllowDowanload=true;
                        progress_text.setText("دانلود فایل کامل شد ");
                        ((LinearLayout) app.Info.Fileview).removeView(viewDowanload);
                        showImage(holder, position);

                    } else {

                        progress_text.setText(String.format("در حال دانلود  (%d/%d) MB", download.getCurrentFileSize(), download.getTotalFileSize()));

                    }
                }
            }
        };
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    private void cheangeBackgerandForSeconds(final RecyclerView.ViewHolder holder) {

        if (holder instanceof mysendchat) {


            Drawable background = context.getResources().getDrawable(R.drawable.shap_sender_message);
            background.setColorFilter(Color.parseColor("#e5d4f8"), PorterDuff.Mode.SRC_IN);
            ((mysendchat) holder).Rel.setBackground(background);

            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    ((mysendchat) holder).Rel.setBackground(context.getResources().getDrawable(R.drawable.shap_sender_message));
                }
            }, 3000);

        } else {


            Drawable background = context.getResources().getDrawable(R.drawable.shap_risive_message);
            background.setColorFilter(Color.parseColor("#e5d4f8"), PorterDuff.Mode.SRC_IN);
            ((othersendchat) holder).Rel.setBackground(background);

            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    ((othersendchat) holder).Rel.setBackground(context.getResources().getDrawable(R.drawable.shap_risive_message));
                }
            }, 3000);


        }
    }

    private void showImage(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof mysendchat) {
            ((mysendchat) holder).lin_img.removeAllViews();
        } else {
            ((othersendchat) holder).lin_img.removeAllViews();
        }
        View viewimage = View.inflate(context, R.layout.item_showimage, null);
        final ImageView imgShow = viewimage.findViewById(R.id.img_shower);

//        Bitmap bitmapImage = BitmapFactory.decodeFile(getFileUri(vm.get(position).RoomChatID, vm.get(position).Filename).getPath());
//        int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
//        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
//        imgShow.setImageBitmap(scaled);
        imgShow.setImageURI(getFileUri(vm.get(position).RoomChatID, vm.get(position).Filename));

        imgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             View vv= app.Dialog_.dialog_creat(context,R.layout.dialog_showimage);
             ImageView imgClose=vv.findViewById(R.id.img_close);
             //ImageView img=vv.findViewById(R.id.img);
                PhotoView photoView = (PhotoView) vv.findViewById(R.id.photo_view);
             AlertDialog a=app.Dialog_.show_dialog(context,vv);
                photoView.setImageURI(getFileUri(vm.get(position).RoomChatID, vm.get(position).Filename));

             imgClose.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     app.Dialog_.dimos_dialog(a);
                 }
             });

            }
        });

        //   imgShow.setImageURI(getFileUri(vm.get(position).RoomChatID, vm.get(position).Filename));

        if (holder instanceof mysendchat) {
            ((mysendchat) holder).lin_img.addView(viewimage);
        } else {
            ((othersendchat) holder).lin_img.addView(viewimage);
        }

        //..............................................................

    }

    @Override
    public int getItemCount() {
        return vm.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (vm != null) {
            return ismymesssage(position);
        }
        return 0;
    }

    private int ismymesssage(int position) {

        if (vm.get(position).SenderID == app.Info.User.userID) {
            return 0;
        } else {
            return 1;
        }

    }
    private String calculateDuration(int duration) {
        String finalDuration = "";
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        if (minutes == 0) {
            finalDuration = "0:" + seconds;
        } else {
            if (seconds >= 60) {
                long sec = seconds - (minutes * 60);
                finalDuration = minutes + ":" + sec;
            }
        }
        return finalDuration;
    }

    public boolean fileExist(int roomchaId, String fname) {
        String path = getLocalPathFile(roomchaId);
        if (app.check.EpmtyOrNull(path)) {
            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fname);
            return outputFile.exists();
        } else {
            return true;
        }


    }

    private String getLocalPathFile(int roomChatId) {
        try {
            List<vm_upload> vm = (List<vm_upload>) dqs.SelesctListWhere(new vm_upload(), " RoomChatID", String.valueOf(roomChatId));
            if (vm != null && vm.size() > 0) {
                String pathName = vm.get(0).Path;
                return pathName;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getLocalNameFile(int roomChatId) {
        try {
            List<vm_upload> vm = (List<vm_upload>) dqs.SelesctListWhere(new vm_upload(), " RoomChatID", String.valueOf(roomChatId));
            if (vm != null && vm.size() > 0) {
                String pathName = vm.get(0).Name;
                return pathName;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Uri getFileUri(int roomChatId, String fname) {
        String pathname = getLocalPathFile(roomChatId);
        if (app.check.EpmtyOrNull(pathname)) {
            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fname);
           // Uri urifile = Uri.fromFile(outputFile);
            Uri urifile ;
            if (Build.VERSION.SDK_INT < 24) {
                urifile = Uri.fromFile(outputFile);
            } else {
                urifile = Uri.parse(outputFile.getPath()); // My work-around for new SDKs, worked for me in Android 10 using Solid Explorer Text Editor as the external editor.
            }

            return urifile;
        } else {

            //Uri urifile = Uri.fromFile(new File(pathname));
            Uri urifile;
            if (Build.VERSION.SDK_INT < 24) {
                urifile = Uri.fromFile(new File(pathname));
            } else {
                //urifile = Uri.fromFile(new File(pathname));
                urifile = Uri.parse(new File(pathname).getPath()); // My work-around for new SDKs, worked for me in Android 10 using Solid Explorer Text Editor as the external editor.

            }


            return urifile;
        }

    }
    public Uri getFileContentUri(int roomChatId,String fname)
    {
        final Uri[] contentUri = {null};
        File file=null;
        String filepath="";
        String pathname = getLocalPathFile(roomChatId);
        if (app.check.EpmtyOrNull(pathname)) {
            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fname);
            file=outputFile;
            filepath=outputFile.getPath();
        } else {
            File outputFile=new File(pathname,fname);
            file=outputFile;
            filepath=outputFile.getPath();
        }

        contentUri[0] = Uri.fromFile(new File(filepath));

        MediaScannerConnection.scanFile(context,
                new String[] { file.getAbsolutePath() }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        contentUri[0] =uri;
                    }
                });
        MediaScannerConnection.scanFile(context, new String[] { file.getAbsolutePath() }, null,
                (path, uri) ->
                        contentUri[0] =uri
        );


        return contentUri[0];
    }

    public void  LoadFile(int roomChatId,String fname,String mimeType)
    {



        String pathname = getLocalNameFile(roomChatId);
        if (!app.check.EpmtyOrNull(pathname))
            fname=pathname;

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fname);

        MediaScannerConnection.scanFile(context,
                new String[] { file.getAbsolutePath() }, new String[]{mimeType},
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Intent target = new Intent(Intent.ACTION_VIEW);

                        //target.setDataAndType(Uri.fromFile(file),"application/pdf");
                        // target.setDataAndType(Uri.fromFile(file),"vm.get(position).MimeType");

                        target.setDataAndType(uri,mimeType);

                        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        Intent intent = Intent.createChooser(target, "بازکردن با");
                        try {
                            context.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            // Instruct the user to install a PDF reader here, or something
                        }
                    }
                });


    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(((Activity) context), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    private void startDownload() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ((Activity)context).runOnUiThread(new Runnable() {
                    public void run() {
                        if(app.Info.isAllowDowanload)
                        {  app.Info.isAllowDowanload=false;
                            Intent intent = new Intent(context, DownloadService.class);
                            (context).startService(intent);
                            Toast.makeText(context,"شروع دانلود...", Toast.LENGTH_SHORT).show();

                        }else
                        {

                            Toast.makeText(context,"در حال دانلود فایلی هستید بعد از اتمام کار", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
    private void CloseDownload() {

        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!app.Info.isAllowDowanload)
                {
                    app.Info.isAllowDowanload=true;
                    Intent intent = new Intent(context, DownloadService.class);
                    (context).stopService(intent);
                    Toast.makeText(context, "دانلود متوقف شد...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) ((Activity)context).getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public class mysendchat extends RecyclerView.ViewHolder {
        RelativeLayout rel_parent;
        RelativeLayout rel_noReadMessage;
        RelativeLayout rel_ShowDate;
        TextView rel_ShowDateText;
        TextView txtparentname, txt_sumrytextparents;
        RelativeLayout Rel;
        LinearLayout lin_text;
        TextView txtmessage;
        TextView txttag;
        LinearLayout lin_img;

        ImageView img_issqllite;
        LinearLayout lin_audeo;
        LinearLayout lin_file;
        //    AudioPlayerView audioplaey_shower;
        LinearLayout lin_video;
        //   BetterVideoPlayer betervideo_shower;
        TextView txt_countsee, txt_time;
        ImageView img_popup;

        public mysendchat(@NonNull View itemView) {
            super(itemView);
            rel_parent = itemView.findViewById(R.id.rel_parent);
            txtparentname = itemView.findViewById(R.id.txtparentname);
            txt_sumrytextparents = itemView.findViewById(R.id.txt_sumrytextparents);
            Rel = itemView.findViewById(R.id.Rel);
            rel_noReadMessage = itemView.findViewById(R.id.rel_noReadMessage);
            rel_ShowDateText=itemView.findViewById(R.id.rel_ShowDateText);
            rel_ShowDate = itemView.findViewById(R.id.rel_ShowDate);
            lin_text = itemView.findViewById(R.id.lin_text);
            txtmessage = itemView.findViewById(R.id.txtmessage);
            lin_img = itemView.findViewById(R.id.lin_img);

            img_issqllite = itemView.findViewById(R.id.img_issqllite);
            lin_audeo = itemView.findViewById(R.id.lin_audeo);
            //   audioplaey_shower = itemView.findViewById(R.id.audioplaey_shower);
            lin_video = itemView.findViewById(R.id.lin_video);
            lin_file = itemView.findViewById(R.id.lin_file);
            //   betervideo_shower = itemView.findViewById(R.id.betervideo_shower);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_countsee = itemView.findViewById(R.id.txt_countsee);
            txttag = itemView.findViewById(R.id.txt_tag);
            img_popup = itemView.findViewById(R.id.imgpopup);

        }
    }

    public class othersendchat extends RecyclerView.ViewHolder {
        TextView txtnamesender;
        TextView txttag;
        RelativeLayout Rel;
        RelativeLayout rel_noReadMessage;
        TextView rel_ShowDateText;
        RelativeLayout rel_ShowDate;
        RelativeLayout rel_parent;
        TextView txtparentname, txt_sumrytextparents;
        LinearLayout lin_text;
        TextView txtmessage;
        LinearLayout lin_img;

        LinearLayout lin_audeo;
        LinearLayout lin_file;
        //    AudioPlayerView audioplaey_shower;
        LinearLayout lin_video;
        //   BetterVideoPlayer betervideo_shower;
        TextView txt_countsee, txt_time;
        ImageView img_popup;

        public othersendchat(@NonNull View itemView) {
            super(itemView);
            txtnamesender = itemView.findViewById(R.id.txtnamesender);
            txttag = itemView.findViewById(R.id.txt_tag);
            rel_parent = itemView.findViewById(R.id.rel_parent);
            rel_noReadMessage = itemView.findViewById(R.id.rel_noReadMessage);
            rel_ShowDateText = itemView.findViewById(R.id.rel_ShowDateText);
            rel_ShowDate = itemView.findViewById(R.id.rel_ShowDate);
            txtparentname = itemView.findViewById(R.id.txtparentname);
            txt_sumrytextparents = itemView.findViewById(R.id.txt_sumrytextparents);
            Rel = itemView.findViewById(R.id.Rel);
            lin_text = itemView.findViewById(R.id.lin_text);
            txtmessage = itemView.findViewById(R.id.txtmessage);
            lin_img = itemView.findViewById(R.id.lin_img);

            lin_audeo = itemView.findViewById(R.id.lin_audeo);
            //   audioplaey_shower = itemView.findViewById(R.id.audioplaey_shower);
            lin_video = itemView.findViewById(R.id.lin_video);
            lin_file = itemView.findViewById(R.id.lin_file);
            //     betervideo_shower = itemView.findViewById(R.id.betervideo_shower);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_countsee = itemView.findViewById(R.id.txt_countsee);
            img_popup = itemView.findViewById(R.id.imgpopup);


        }
    }
}
