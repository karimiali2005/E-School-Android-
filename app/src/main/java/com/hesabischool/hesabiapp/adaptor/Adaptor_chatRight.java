package com.hesabischool.hesabiapp.adaptor;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.DetilsChat;
import com.hesabischool.hesabiapp.ImageCash.ImageLoader;
import com.hesabischool.hesabiapp.Interfasces.callForCheange;
import com.hesabischool.hesabiapp.MainChat;
import com.hesabischool.hesabiapp.R;
import com.hesabischool.hesabiapp.viewmodel.vm_checkPage;
import com.hesabischool.hesabiapp.vm_ModelServer.ChatMessage;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatRightShowResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.hesabischool.hesabiapp.Clases.Time.getTimeAgo2;

public class Adaptor_chatRight extends RecyclerView.Adapter<Adaptor_chatRight.MyviewHolder> {
    Context context;
    ImageLoader  imgLoader;

    public List<RoomChatRightShowResult> vm;

    public Adaptor_chatRight(Context context, List<RoomChatRightShowResult> vm) {
        this.context = context;
        this.vm = vm;

    }

    @NonNull
    @Override
    public Adaptor_chatRight.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new Adaptor_chatRight.MyviewHolder(view);
    }

    int gotomessage = 0;

    public void gotoDetilsmessage(ChatMessage ch) {
     //   Toast.makeText(context, "Adaptor:" + String.valueOf(gotomessage), Toast.LENGTH_SHORT).show();
        if (gotomessage < 10) {
            int position = findRoomChatRight(ch.groupId);

            if (position == -2) {

                gotomessage++;
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gotoDetilsmessage(ch);
                    }
                }, 5000);

            } else {
                gotomessage = 0;
                if (position != -1) {

                    app.Info.checkpage.roomchatright = vm.get(position);
                    Intent i = new Intent(context, DetilsChat.class);

                    context.startActivity(i);
                }
            }

        } else {
            gotomessage = 0;
        }


    }

    private int findRoomChatRight(int roomChatGroupID) {
        if (vm == null || vm.size() < 1) {
            return -2;
        }
        int i = -1;
        for (RoomChatRightShowResult r : vm) {
            i++;
            if (r.RoomChatGroupID == roomChatGroupID) {
                return i;
            }
        }
        return -1;
    }
boolean s=false;
    @Override
    public void onBindViewHolder(@NonNull Adaptor_chatRight.MyviewHolder holder, final int position) {

        imgLoader = new ImageLoader(context);

        imgLoader.DisplayPicture(vm.get(position).UserIDPic, holder.img_profile);


        if (vm.get(position).MessageNewNumber != 0) {
            holder.txt_badeg.setVisibility(View.VISIBLE);
            if (vm.get(position).MessageNewNumber < 99) {
                holder.txt_badeg.setText(String.valueOf(vm.get(position).MessageNewNumber));
            } else {
                holder.txt_badeg.setText("99+");
            }
        } else {
            holder.txt_badeg.setVisibility(View.GONE);
        }
        //Todo if For homeWork
        holder.txt_namegrupe.setText(vm.get(position).RoomChatTitle);
      //  if (!app.check.EpmtyOrNull(vm.get(position).TextChat)) {
            String value = vm.get(position).TextChat;
            if (app.check.EpmtyOrNull(value)|| value.equals("null"))
            {
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

                if (mime_types_images.indexOf(vm.get(position).Mimetype) >= 0) {
                    //todo Image
                    String KindFile="";
                    KindFile="تصویر";
                    holder.txt_lastMessaage.setText(KindFile);
                } else if (mime_types_audios.indexOf(vm.get(position).Mimetype) >= 0) {//audio
                  //todo music
                    String KindFile="";
                    KindFile="صدا";
                    holder.txt_lastMessaage.setText(KindFile);
//End Audeo
                } else if (mime_types_videos.indexOf(vm.get(position).Mimetype) >= 0) {
                   //todo video
                    String KindFile="";
                    KindFile="ویدئو";
                    holder.txt_lastMessaage.setText(KindFile);
                }else if(app.check.EpmtyOrNull(vm.get(position).Mimetype)|| vm.get(position).Mimetype.equals("null"))
                {
                    String KindFile="";

                    holder.txt_lastMessaage.setText(KindFile);
                }
                else {
                    //todo For unkonw File
                    String KindFile="";
                    KindFile="فایل";
                    holder.txt_lastMessaage.setText(KindFile);
                }




            }else {

                holder.txt_lastMessaage.setText(Html.fromHtml(vm.get(position).TextChat));
            }
    //    }


        // holder.txt_time.setText(vm.get(position).RoomChatDateString);
        holder.txt_time.setText(getTimeAgo2(vm.get(position).RoomChatDate));

        holder.constParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainChat.postionItem=position;
                app.Info.checkpage.roomchatright = vm.get(position);
                Intent i = new Intent(context, DetilsChat.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vm.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView txt_namegrupe, txt_lastMessaage, txt_time, txt_badeg;
        CircleImageView img_profile;
        ConstraintLayout constParent;


        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            txt_namegrupe = itemView.findViewById(R.id.txt_namegrupe);
            txt_lastMessaage = itemView.findViewById(R.id.txt_lastMessaage);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_badeg = itemView.findViewById(R.id.txt_badeg);
            img_profile = itemView.findViewById(R.id.img_profile);
            constParent = itemView.findViewById(R.id.constParent);
        }
    }
}
