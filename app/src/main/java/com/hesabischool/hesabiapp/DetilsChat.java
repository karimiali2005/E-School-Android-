package com.hesabischool.hesabiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.CircleProgress;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hesabischool.hesabiapp.Clases.ConvertChatmessageToRoomChatShowLeftResualt;
import com.hesabischool.hesabiapp.Clases.ExceptionHandler;
import com.hesabischool.hesabiapp.Clases.FileUtil;
import com.hesabischool.hesabiapp.Clases.ProgressRequestBody;
import com.hesabischool.hesabiapp.Clases.RecordVoice.RecordeVoice;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.Clases.hesabi_SignalR;
import com.hesabischool.hesabiapp.Interfasces.LayzyLoad;
import com.hesabischool.hesabiapp.Interfasces.callForCheange;
import com.hesabischool.hesabiapp.adaptor.Adaptor_chatRight;
import com.hesabischool.hesabiapp.adaptor.Adaptor_detailsChat;
import com.hesabischool.hesabiapp.database.dbConnector;
import com.hesabischool.hesabiapp.database.dbQuerySelect;
import com.hesabischool.hesabiapp.viewmodel.vm_checkPage;
import com.hesabischool.hesabiapp.viewmodel.vm_sendoflinechat;
import com.hesabischool.hesabiapp.viewmodel.vm_upload;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer2;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer3;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatRightShowResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetilsChat extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks {
    ImageView img_tag, img_send, img_mic, img_file;
    EditText edt_chat;
    TextView txtnamegrupe;
    ImageView img_more;
    Context context;
    dbConnector db;
    dbQuerySelect dq;
    boolean isEdite = false;
    FloatingActionButton fab_down;
    boolean taglearn = false;
    RecyclerView shimmerRecycler;
    RoomChatRightShowResult rcharright = new RoomChatRightShowResult();
    List<RoomChatLeftShowResult> lvm = new ArrayList<>();
    RoomChatLeftShowResult roomchatForEdite = new RoomChatLeftShowResult();
    LinearLayoutManager layoutManager;
    CircleProgress circle_progress;
    int index = 0;
    int take = 30;
    int ofcet = 0;
    int pagenuber = 1;
    int roomChatParentId = 0;
    String parentTextChat = "";
    String parentSenderName = "";
    ImageView img_close;
    ImageView imgrplybox;
    TextView txt_parentname, txt_parenttext;
    Adaptor_detailsChat ma;
    LinearLayout lin_replay;

    MaterialCardView card_pin;
    ImageView img_unpin;
    TextView txt_pin;

    LayzyLoad layzyLoad = new LayzyLoad() {
        @Override
        public void call() {
            if (index != -1) {
                Risave();
            }
        }
    };
    callForCheange c = new callForCheange() {

        @Override
        public void updateMessage(int oldroomchatId, RoomChatLeftShowResult newr) {

            final int index = findRoomChatLeft(oldroomchatId);

            if (index != -1) {
                ma.vm.set(index, newr);
                shimmerRecycler.post(new Runnable() {
                    @Override
                    public void run() {

                        ma.notifyItemChanged(index);
                        //  ma.notifyDataSetChanged();
                        //     layoutManager.scrollToPosition(finalPoslast);
                        //      ma.scroled = 0;
                    }
                });
            }
        }

        @Override
        public void ReplayMessage(RoomChatLeftShowResult rchatleft, boolean isedite) {
            if (isedite) {
                imgrplybox.setImageResource(R.drawable.ic_edit2);
                setEditeBox(rchatleft);
            } else {
                imgrplybox.setImageResource(R.drawable.ic_reply_message);


                setReplayBox(rchatleft);
            }
        }

        @Override
        public void DeleteMessage(int roomchatId) {
            final int index = findRoomChatLeft(roomchatId);

            if (index != -1) {
                ma.vm.remove(index);
                shimmerRecycler.post(new Runnable() {
                    @Override
                    public void run() {

                        ma.notifyItemRemoved(index);//

                        //   ma.notifyDataSetChanged();
                        //     layoutManager.scrollToPosition(finalPoslast);
                        //      ma.scroled = 0;
                    }
                });
            }
        }

        @Override
        public void DialogDeleteMessage(final RoomChatLeftShowResult rchatleft) {
            View viewdel = app.Dialog_.dialog_creat(context, R.layout.dialog_delete_mesage);
            Button btnSelf = viewdel.findViewById(R.id.btn_self);
            Button btn_all = viewdel.findViewById(R.id.btn_all);
            Button btn_cancel = viewdel.findViewById(R.id.btn_cancel);
            final AlertDialog al = app.Dialog_.show_dialog(context, viewdel);
            btn_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (app.net.CheckCommunication(context)) {

                        SendForDeleate(rchatleft);
                        al.dismiss();
                    }
                }
            });
            btnSelf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SendForDeleateForMe(rchatleft);
                    al.dismiss();
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    al.dismiss();
                }
            });

        }

        private void SendForDeleate(final RoomChatLeftShowResult rchatleft) {
            app.progress.onCreateDialog(context);
            app.retrofit.retrofit().RoomChatDelete(rchatleft.RoomChatID).enqueue(new Callback<GetDataFromServer3>() {
                @Override
                public void onResponse(Call<GetDataFromServer3> call, Response<GetDataFromServer3> response) {
                    app.retrofit.erorRetrofit(response, context);
                    if (response.isSuccessful()) {
                        c.DeleteMessage(rchatleft.RoomChatID);

                        hesabi_SignalR.sendMessage(response.body().value);
                    }
                }

                @Override
                public void onFailure(Call<GetDataFromServer3> call, Throwable t) {
                    app.retrofit.FailRetrofit(t, context);
                }
            });
        }

        private void SendForDeleateForMe(final RoomChatLeftShowResult rchatleft) {
            app.progress.onCreateDialog(context);
            app.retrofit.retrofit().RoomChatViewDelete(rchatleft.RoomChatID).enqueue(new Callback<GetDataFromServer3>() {
                @Override
                public void onResponse(Call<GetDataFromServer3> call, Response<GetDataFromServer3> response) {
                    app.retrofit.erorRetrofit(response, context);
                    if (response.isSuccessful()) {
                        c.DeleteMessage(rchatleft.RoomChatID);

                        hesabi_SignalR.sendMessage(response.body().value);
                    }
                }

                @Override
                public void onFailure(Call<GetDataFromServer3> call, Throwable t) {
                    app.retrofit.FailRetrofit(t, context);
                }
            });
        }

        @Override
        public void gotoPostionItem(final int idRoomChat) {
            final int postion = findRoomChatLeft(idRoomChat);
            if (postion != -1) {

                shimmerRecycler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Goto Posetion " + String.valueOf(postion), Toast.LENGTH_SHORT).show();

                        layoutManager.scrollToPosition(postion);
                        ma.select = true;
                        ma.notifyItemChanged(postion);
                        //     ma.notifyDataSetChanged();

                    }
                });


            } else {
                Toast.makeText(context, "LoadMore...", Toast.LENGTH_SHORT).show();
                //Todo Load More
            }
        }

        @Override
        public void getFileVoice(File file) {

            uploadFileVoice(file, "audio/wav");
        }

        @Override
        public void gotodown() {
            shimmerRecycler.post(new Runnable() {
                @Override
                public void run() {
                    layoutManager.scrollToPosition(ma.vm.size() - 1);
                }
            });
        }

        @Override
        public void AddMessage(RoomChatLeftShowResult rchatleft) {
            loadrecNotifyAddMessageFromSignalR(rchatleft);
        }


    };

    private void popup() {
        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(context, img_more);
                menu.getMenu().add("تقویم");
                menu.getMenu().add("تکلیف");
                menu.getMenu().add("جستجو");

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getTitle().equals(menu.getMenu().getItem(0).getTitle())) {

                        } else if (menuItem.getTitle().equals(menu.getMenu().getItem(1).getTitle())) {
                            //Edite

                        } else if (menuItem.getTitle().equals(menu.getMenu().getItem(2).getTitle())) {
                            //Delete

                        } else if (menuItem.getTitle().equals(menu.getMenu().getItem(3).getTitle())) {
                            //Forward
                        }

                        return false;
                    }
                });
                menu.show();
            }
        });

    }



    private void setEditeBox(RoomChatLeftShowResult rchatleft) {
        lin_replay.setVisibility(View.VISIBLE);
        txt_parentname.setText(rchatleft.SenderName);
        txt_parenttext.setText(rchatleft.TextChat);
        edt_chat.setText(rchatleft.TextChat);
        roomchatForEdite = rchatleft;
        isEdite = true;
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeReplyBox();
            }
        });

    }

    private void setReplayBox(RoomChatLeftShowResult rchatleft) {
        lin_replay.setVisibility(View.VISIBLE);
        parentTextChat = rchatleft.TextChat;
        roomChatParentId = rchatleft.RoomChatID;
        parentSenderName = rchatleft.SenderName;
        txt_parentname.setText(parentSenderName);
        txt_parenttext.setText(parentTextChat);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeReplyBox();
            }
        });


    }

    private void removeReplyBox() {
        lin_replay.setVisibility(View.GONE);
        roomChatParentId = 0;
        parentTextChat = "";
        parentSenderName = "";
        txt_parentname.setText("");
        txt_parenttext.setText("");
        if (isEdite) {
            isEdite = false;
            edt_chat.setText("");
        }


    }

    private int findRoomChatLeft(int roomChatID) {
        int i = -1;
        for (RoomChatLeftShowResult r : ma.vm) {
            i++;
            if (r.RoomChatID == roomChatID) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_detils_chat);

        context = this;
        try {
            dq = new dbQuerySelect(context);
            db = new dbConnector(context);
            shimmerRecycler = (RecyclerView) findViewById(R.id.rec_chat);
            layoutManager = new LinearLayoutManager(context);
            SetCurentPage();
            rcharright = app.Info.checkpage.roomchatright;
            img_tag = findViewById(R.id.img_tag);
            img_more = findViewById(R.id.img_more);
            txtnamegrupe = findViewById(R.id.txt_hesabi);
            imgrplybox = findViewById(R.id.imgrplybox);
            txt_parenttext = findViewById(R.id.txt_parenttext);
            fab_down = findViewById(R.id.fab_down);
            txt_parentname = findViewById(R.id.txt_parentname);
            img_close = findViewById(R.id.img_close);
            img_send = findViewById(R.id.img_send);
            img_mic = findViewById(R.id.img_mic);
            img_file = findViewById(R.id.img_file);
            edt_chat = findViewById(R.id.edt_chat);
            lin_replay = findViewById(R.id.lin_replay);
            circle_progress = findViewById(R.id.circle_progress);
            txt_pin = findViewById(R.id.txt_pin);
            img_unpin = findViewById(R.id.img_unpin);

            edt_chat.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (app.check.EpmtyOrNull(charSequence.toString())) {
                        img_mic.setVisibility(View.VISIBLE);
                        img_send.setVisibility(View.GONE);
                    } else {
                        img_send.setVisibility(View.VISIBLE);
                        img_mic.setVisibility(View.INVISIBLE);

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            img_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (isEdite) {
                        //Todo Edite
                        if (app.net.CheckCommunication(context)) {
                            SendMessageForEdite(edt_chat.getText().toString());
                        }
                    } else {
                        RoomChatLeftShowResult r = new RoomChatLeftShowResult();
                        Date d = Calendar.getInstance().getTime();
                        android.text.format.DateFormat df = new android.text.format.DateFormat();
                        String s = String.valueOf(df.format("yyyy-MM-ddThh:mm:ss", d));

                        r.RoomChatGroupID = rcharright.RoomChatGroupID;
                        r.TextChat = edt_chat.getText().toString();
                        r.Filename = "";

                        r.TagLearn = taglearn;
                        r.RoomChatParentID = roomChatParentId;
                        r.RoomID = rcharright.RoomID;
                        r.TeacherID = rcharright.TeacherID;
                        r.CourseID = rcharright.CourseID;
                        r.ParentTextChat = parentTextChat;
                        r.ParentSenderName = parentSenderName;
                        r.RoomChatID = getRandomId();
                        r.IsSqlLite = true;
                        r.SenderID = app.Info.User.userID;
                        r.SenderName = app.Info.User.fullName;
                        r.RoomChatDate = s;
                        r.RoomChatDateString = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(d);
                        insertToSqllite(r);
                        edt_chat.setText("");
                        removeReplyBox();
                    }

                }
            });
            img_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showFileChooser();
                }
            });
            RecordeVoice recordeVoice = new RecordeVoice(img_mic, c);
            recordeVoice.run();
            popup();
            txtnamegrupe.setText(rcharright.RoomChatTitle);
/*img_mic.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        RecordeVoice();
    }
});*/
            Risave();
        } catch (Exception ex) {

        }
    }

    private void SetCurentPage() {
        app.Info.checkpage.callForCheange = c;
        app.Info.checkpage.curentActivity = "DetilsChat";
    }

    private void SendMessageForEdite(String textchat) {
        app.progress.onCreateDialog(context);
        app.retrofit.retrofit().RoomChatEdit(roomchatForEdite.RoomChatID, textchat, taglearn, roomchatForEdite.ParentTextChat, roomchatForEdite.ParentSenderName).enqueue(new Callback<GetDataFromServer3>() {
            @Override
            public void onResponse(Call<GetDataFromServer3> call, Response<GetDataFromServer3> response) {
                app.retrofit.erorRetrofit(response, context);
                if (response.isSuccessful()) {
                    GetDataFromServer3 lr = response.body();
                    //   GetDataFromServer rl = new GetDataFromServer();
                    RoomChatLeftShowResult rclst = new ConvertChatmessageToRoomChatShowLeftResualt(lr.value, roomchatForEdite).convert();
                    c.updateMessage(roomchatForEdite.RoomChatID, rclst);

                    removeReplyBox();
                }
            }

            @Override
            public void onFailure(Call<GetDataFromServer3> call, Throwable t) {
                app.retrofit.FailRetrofit(t, context);
            }
        });

    }

    private void insertToSqllite(RoomChatLeftShowResult r) {
        try {
            db.dq.saveTosqlAdd(r);
            vm_sendoflinechat vmc = new vm_sendoflinechat();
            vmc.RoomChatID = r.RoomChatID;
            db.dq.saveTosqlAdd(vmc);
            loadrecNotifyAddMessage(r);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void insertToSqlliteFile(RoomChatLeftShowResult r, File file) {
        try {
            //  String path = getRealPathFromURI(uri);
            vm_upload vm_upload = new vm_upload();
            vm_upload.Name = file.getName();
            vm_upload.Path = file.getPath();
            vm_upload.RoomChatID = r.RoomChatID;
            db.dq.saveTosqlAdd(vm_upload);
            loadrecNotifyAddMessage(r);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private int getRandomId() {
        int min = 1;
        int max = 99999999;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        return i1;
    }

    private void Risave() {
        try {
            app.linProgress.showProgress(context);
            if (app.net.isNetworkConnected(context)) {
                if (pagenuber == 1) {
                    app.retrofit.retrofit().RoomChatLeft(rcharright.RoomChatGroupID, rcharright.RoomChatGroupType, false, rcharright.MessageNewNumber, rcharright.RoomID, rcharright.TeacherID, rcharright.CourseID, pagenuber, 30, rcharright.PicName, rcharright.RoomChatTitle).enqueue(new Callback<GetDataFromServer2>() {
                        @Override
                        public void onResponse(Call<GetDataFromServer2> call, Response<GetDataFromServer2> response) {
                            //     app.retrofit.erorRetrofit(response, context);
                            app.linProgress.hideProgress(context);
                            if (response.isSuccessful()) {
                                GetDataFromServer2 r = response.body();
                                GetDataFromServer rr = new GetDataFromServer();
                                rr.value.RoomChatLeftViewModel = r.value;
                                db.dq.addOrUpdateData(rr);
                                pagenuber++;
                                getdataFromSqlLite();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetDataFromServer2> call, Throwable t) {
                            //     app.retrofit.FailRetrofit(t, context);
                            app.linProgress.hideProgress(context);
                        }
                    });

                } else {
                    app.retrofit.retrofit().RoomChatLeft2(rcharright.RoomChatGroupID, false, pagenuber, 30, true, true).enqueue(new Callback<GetDataFromServer2>() {
                        @Override
                        public void onResponse(Call<GetDataFromServer2> call, Response<GetDataFromServer2> response) {
                            app.retrofit.erorRetrofit(response, context);
                            app.linProgress.hideProgress(context);
                            if (response.isSuccessful()) {
                                GetDataFromServer2 r = response.body();
                                GetDataFromServer rr = new GetDataFromServer();
                                rr.value.RoomChatLeftViewModel = r.value;
                                db.dq.addOrUpdateData(rr);
                                pagenuber++;
                                getdataFromSqlLite();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetDataFromServer2> call, Throwable t) {
                            app.retrofit.FailRetrofit(t, context);
                            app.linProgress.hideProgress(context);
                            getdataFromSqlLite();
                        }
                    });

                }

            } else {
                app.linProgress.hideProgress(context);
                //Todo go to sqllite
                getdataFromSqlLite();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getdataFromSqlLite() {
        try {

            //  lvm = (List<RoomChatLeftShowResult>) dq.SelesctListOrderByAscndingAndWhereTakeAndOfcet(new RoomChatLeftShowResult(), " roomChatDate", "roomChatGroupID", String.valueOf(rcharright.RoomChatGroupID), take, ofcet);
            String where = " WHERE roomChatGroupID = " + String.valueOf(rcharright.RoomChatGroupID);
            where += " AND roomChatDelete = 0 ";
            lvm = (List<RoomChatLeftShowResult>) dq.SelesctListOrderByDesendingAndWhereArryTakeAndOfcet(new RoomChatLeftShowResult(), "roomChatDate", where, take, ofcet);

            if (lvm == null || lvm.size() < 1) {
                index = -1;
            }


            //  lvm= (List<RoomChatLeftShowResult>) dq.SelesctList(new RoomChatLeftShowResult());
            if (ofcet == 0) {
                SetRecyceler();
            } else {
                loadrecNotify();
            }

            ofcet += take;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void SetRecyceler() {

        //  shimmerRecycler.showShimmerAdapter();
        List<RoomChatLeftShowResult> lvm2 = new ArrayList<>();
        for (int i = 0; i < lvm.size(); i++) {
            lvm2.add(0, lvm.get(i));
        }
        layoutManager.scrollToPosition(lvm.size() - 1);
        //  LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        //  layoutManager.setReverseLayout(true);
        //  layoutManager.setStackFromEnd(true);
        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.setHasFixedSize(true);
        shimmerRecycler.setItemViewCacheSize(30);

        shimmerRecycler.setDrawingCacheEnabled(true);
        shimmerRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        ma = new Adaptor_detailsChat(context, lvm2, layzyLoad, c, fab_down);
        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.setAdapter(ma);
    }

    private void loadrecNotify() {

//        for (int i = lvm.size() - 1; i >= 0; i--) {
//            ma.vm.add(0, lvm.get(i));
//        }
        //  final int poslast=(ma.vm.size())-(lvm.size())-3;
        int poslast = ma.vm.size() - 1;
        for (int i = 0; i < lvm.size(); i++) {
            ma.vm.add(0, lvm.get(i));
        }
        ma.scroled = ma.vm.size() - 1;

        poslast = ma.scroled - poslast;
        final int finalPoslast = poslast;
        shimmerRecycler.post(new Runnable() {
            @Override
            public void run() {


                ma.notifyDataSetChanged();
                layoutManager.scrollToPosition(finalPoslast);
                ma.scroled = 0;
            }
        });


    }

    private void loadrecNotifyAddMessage(final RoomChatLeftShowResult r) {

        shimmerRecycler.post(new Runnable() {
            @Override
            public void run() {
                ma.vm.add(r);
                layoutManager.scrollToPosition(ma.vm.size() - 1);
                ma.notifyItemInserted(ma.vm.size() - 1);
                ma.notifyDataSetChanged();


            }
        });


    }

    private void loadrecNotifyAddMessageFromSignalR(final RoomChatLeftShowResult r) {

        shimmerRecycler.post(new Runnable() {
            @Override
            public void run() {
                ma.vm.add(r);
                //   layoutManager.scrollToPosition(ma.vm.size() - 1);
                ma.notifyItemInserted(ma.vm.size() - 1);
                ma.notifyDataSetChanged();

//Todo ADD COUNT MESSAGE dont Read

            }
        });


    }

    @Override
    public void onProgressUpdate(int percentage) {
        circle_progress.setProgress(percentage);
    }

    @Override
    public void onError() {
        Toast.makeText(context, "خطا ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinish() {
        circle_progress.setProgress(100);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                circle_progress.setVisibility(View.GONE);
            }
        }, 5000);
    }

    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();

                    // Get the file instance

                    // File file = new File(uri.getPath());

                    try {

                        File file = FileUtil.from(context, uri);
                        ContentResolver cR = context.getContentResolver();
                        String mime = cR.getType(uri);
                        uploadFile(file, mime);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Initiate the upload

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadFile(File file, String content_type) {
        if (app.net.isNetworkConnected(context)) {
            final RoomChatLeftShowResult r = addfileTosqlLitePath(file, content_type);
            circle_progress.setVisibility(View.VISIBLE);
            ProgressRequestBody fileBody = new ProgressRequestBody(file, content_type, this);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
            app.retrofit.retrofit().StoreFile(filePart, String.valueOf(taglearn), rcharright.RoomChatGroupID, "", roomChatParentId, rcharright.RoomID, rcharright.TeacherID, rcharright.CourseID, parentTextChat, parentSenderName).enqueue(new Callback<GetDataFromServer3>() {
                @Override
                public void onResponse(Call<GetDataFromServer3> call, Response<GetDataFromServer3> response) {
                    if (response.isSuccessful()) {
                        int lastIdRoomChat = r.RoomChatID;
                        GetDataFromServer3 lr = response.body();
                        //   GetDataFromServer rl = new GetDataFromServer();
                        hesabi_SignalR.sendMessage(lr.value);
                        RoomChatLeftShowResult rclst = new ConvertChatmessageToRoomChatShowLeftResualt(lr.value, r).convert();
                        //   rl.value.RoomChatLeftViewModel.RoomChatLeftShowResult.add(rclst);
                        String where = " WHERE RoomChatID = " + String.valueOf(lastIdRoomChat);
                        //   where += " AND IsSqlLite = 1";
                        try {
                            db.dq.saveTosqlAdd(rclst);

                            List<vm_upload> vm = (List<vm_upload>) dq.SelesctListArryWhere(new vm_upload(), where);
                            if (vm != null && vm.size() > 0) {
                                vm_upload vmu = vm.get(0);
                                vmu.RoomChatID = rclst.RoomChatID;
                                String whereupload = " RoomChatID = " + String.valueOf(lastIdRoomChat);
                                db.dq.saveTosqlUpdate(vmu, whereupload);
                            }


                            c.updateMessage(rclst.RoomChatID, rclst);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(context, "ارسال فایل با مشکل روبرو شده است دوباره تلاش کنید ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetDataFromServer3> call, Throwable t) {

                    int x = 0;
                }
            });

        } else {
            Toast.makeText(context, "اینترنت خود را بررسی کنید برای ارسال فایل باید اینترنت شما فعال باشد ", Toast.LENGTH_LONG).show();
        }


    }

    private void uploadFileVoice(File file, String content_type) {
        if (app.net.isNetworkConnected(context)) {
            final RoomChatLeftShowResult r = addfileTosqlLitePath(file, content_type);
            circle_progress.setVisibility(View.VISIBLE);
            ProgressRequestBody fileBody = new ProgressRequestBody(file, content_type, this);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
            app.retrofit.retrofit().SendRecordAudio(filePart, String.valueOf(taglearn), rcharright.RoomChatGroupID, "", roomChatParentId, rcharright.RoomID, rcharright.TeacherID, rcharright.CourseID, parentTextChat, parentSenderName).enqueue(new Callback<GetDataFromServer3>() {
                @Override
                public void onResponse(Call<GetDataFromServer3> call, Response<GetDataFromServer3> response) {
                    if (response.isSuccessful()) {
                        int lastIdRoomChat = r.RoomChatID;
                        GetDataFromServer3 lr = response.body();
                        //   GetDataFromServer rl = new GetDataFromServer();
                        hesabi_SignalR.sendMessage(lr.value);
                        RoomChatLeftShowResult rclst = new ConvertChatmessageToRoomChatShowLeftResualt(lr.value, r).convert();
                        //   rl.value.RoomChatLeftViewModel.RoomChatLeftShowResult.add(rclst);
                        String where = " WHERE RoomChatID = " + String.valueOf(lastIdRoomChat);
                        //   where += " AND IsSqlLite = 1";
                        try {
                            db.dq.saveTosqlAdd(rclst);

                            List<vm_upload> vm = (List<vm_upload>) dq.SelesctListArryWhere(new vm_upload(), where);
                            if (vm != null && vm.size() > 0) {
                                vm_upload vmu = vm.get(0);
                                vmu.RoomChatID = rclst.RoomChatID;
                                String whereupload = " RoomChatID = " + String.valueOf(lastIdRoomChat);
                                db.dq.saveTosqlUpdate(vmu, whereupload);
                            }


                            c.updateMessage(rclst.RoomChatID, rclst);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(context, "ارسال فایل با مشکل روبرو شده است دوباره تلاش کنید ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetDataFromServer3> call, Throwable t) {

                    int x = 0;
                }
            });

        } else {
            Toast.makeText(context, "اینترنت خود را بررسی کنید برای ارسال فایل باید اینترنت شما فعال باشد ", Toast.LENGTH_LONG).show();
        }
    }

    private RoomChatLeftShowResult addfileTosqlLitePath(File file, String mime) {

        RoomChatLeftShowResult r = new RoomChatLeftShowResult();
        Date d = Calendar.getInstance().getTime();
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        String s = String.valueOf(df.format("yyyy-MM-ddThh:mm:ss", d));

        r.RoomChatGroupID = rcharright.RoomChatGroupID;
        r.TextChat = edt_chat.getText().toString();
        r.Filename = file.getName();
        r.MimeType = mime;
        r.TagLearn = taglearn;
        r.RoomChatParentID = roomChatParentId;
        r.RoomID = rcharright.RoomID;
        r.TeacherID = rcharright.TeacherID;
        r.CourseID = rcharright.CourseID;
        r.ParentTextChat = parentTextChat;
        r.ParentSenderName = parentSenderName;
        r.RoomChatID = getRandomId();
        r.IsSqlLite = true;
        r.SenderID = app.Info.User.userID;
        r.SenderName = app.Info.User.fullName;
        r.RoomChatDate = s;
        r.RoomChatDateString = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(d);
        insertToSqlliteFile(r, file);
        return r;
    }
}