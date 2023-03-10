package com.hesabischool.hesabiapp.adaptor;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.ImageCash.ImageLoader;
import com.hesabischool.hesabiapp.R;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatContactResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatGroupOnlineViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adaptor_OnlineShow extends RecyclerView.Adapter<Adaptor_OnlineShow.MyviewHolder> {
    Context context;
    ImageLoader imgLoader;

    public List<RoomChatGroupOnlineViewModel> vm;
    AlertDialog alertDialog;

    public Adaptor_OnlineShow(Context context, List<RoomChatGroupOnlineViewModel> vm, AlertDialog alertDialog) {
        this.context = context;
        this.vm = vm;
        this.alertDialog=alertDialog;

    }

    @NonNull
    @Override
    public Adaptor_OnlineShow.MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_forward, viewGroup, false);
        return new Adaptor_OnlineShow.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_OnlineShow.MyviewHolder holder, int position) {

        imgLoader = new ImageLoader(context);
        imgLoader.DisplayPicture(vm.get(position).UserID, holder.img_profile);

    if(vm.get(position).IsOnline)
    {
    holder.img_online.setImageResource(R.drawable.shape_online);
    }
    else
    {
        holder.img_online.setImageResource(R.drawable.shape_ofline);

    }

        holder.txt_namegrupe.setText(vm.get(position).FullName);
//        holder.constParent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              //  app.Info.checkpage.roomchatright = vm.get(position);
//              //  Intent i = new Intent(context, DetilsChat.class);
//              //  context.startActivity(i);
//                app.progress.onCreateDialog(context);
//            app.retrofit.retrofit().RoomChatGroupInsert(vm.get(position).UserID,vm.get(position).FullName).enqueue(new Callback<Object>() {
//                @Override
//                public void onResponse(Call<Object> call, Response<Object> response) {
//                    app.retrofit.erorRetrofit(response,context);
//                    if(response.isSuccessful())
//                    {
//                        app.Dialog_.dimos_dialog(alertDialog);
//                        Toast.makeText(context, "???? ???????????? ?????????? ??????????", Toast.LENGTH_SHORT).show();
//                      //  app.Info.checkpage.roomchatright = vm.get(position);
//                       // Intent i = new Intent(context, DetilsChat.class);
//                      //  context.startActivity(i);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Object> call, Throwable t) {
//app.retrofit.FailRetrofit(t,context);
//                }
//            });
//            }
//        });
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
        ImageView img_online;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            txt_namegrupe = itemView.findViewById(R.id.txt_namegrupe);
            txt_lastMessaage = itemView.findViewById(R.id.txt_lastMessaage);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_badeg = itemView.findViewById(R.id.txt_badeg);
            img_profile = itemView.findViewById(R.id.img_profile);
            constParent = itemView.findViewById(R.id.constParent);
            img_online = itemView.findViewById(R.id.img_online);
            ch = itemView.findViewById(R.id.ch);
            ch.setVisibility(View.GONE);
            img_online.setVisibility(View.VISIBLE);

        }
    }
}
