package com.hesabischool.hesabiapp.adaptor;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.DetilsChat;
import com.hesabischool.hesabiapp.Interfasces.callForCheange;
import com.hesabischool.hesabiapp.R;
import com.hesabischool.hesabiapp.viewmodel.vm_checkPage;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatRightShowResult;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.hesabischool.hesabiapp.Clases.Time.getTimeAgo2;

public class Adaptor_chatRight extends RecyclerView.Adapter<Adaptor_chatRight.MyviewHolder> {
    Context context;
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

    @Override
    public void onBindViewHolder(@NonNull Adaptor_chatRight.MyviewHolder holder, final int position) {
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
        if(!app.check.EpmtyOrNull(vm.get(position).TextChat))
        {

        holder.txt_lastMessaage.setText(Html.fromHtml(vm.get(position).TextChat));
        }

       // holder.txt_time.setText(vm.get(position).RoomChatDateString);
       holder.txt_time.setText(getTimeAgo2(vm.get(position).RoomChatDate));

        holder.constParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
