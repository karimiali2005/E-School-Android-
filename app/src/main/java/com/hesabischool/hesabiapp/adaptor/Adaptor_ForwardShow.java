package com.hesabischool.hesabiapp.adaptor;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.ImageCash.ImageLoader;
import com.hesabischool.hesabiapp.R;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatForwardUser;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatGroupOnlineViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adaptor_ForwardShow extends RecyclerView.Adapter<Adaptor_ForwardShow.MyviewHolder> {
    Context context;
    ImageLoader imgLoader;

    public List<RoomChatForwardUser> vm;
    AlertDialog alertDialog;

    public Adaptor_ForwardShow(Context context, List<RoomChatForwardUser> vm, AlertDialog alertDialog) {
        this.context = context;
        this.vm = vm;
        this.alertDialog=alertDialog;
        imgLoader = new ImageLoader(context);
    }

    @NonNull
    @Override
    public Adaptor_ForwardShow.MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_forward, viewGroup, false);
        return new Adaptor_ForwardShow.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_ForwardShow.MyviewHolder holder, int position) {


        imgLoader.DisplayPicture(vm.get(position).UserID, holder.img_profile);
        holder.txt_badeg.setVisibility(View.GONE);
        holder.txt_time.setVisibility(View.GONE);
        holder.txt_namegrupe.setText(vm.get(position).GroupTitle);
        holder.ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    app.Info.forwardList.add(vm.get(position).GroupID);
                }else
                {
                    app.Info.forwardList.remove(vm.get(position).GroupID);
                }
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
        CheckBox ch;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            txt_namegrupe = itemView.findViewById(R.id.txt_namegrupe);
            txt_lastMessaage = itemView.findViewById(R.id.txt_lastMessaage);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_badeg = itemView.findViewById(R.id.txt_badeg);
            img_profile = itemView.findViewById(R.id.img_profile);
            constParent = itemView.findViewById(R.id.constParent);
            ch = itemView.findViewById(R.id.ch);
        }
    }
}
