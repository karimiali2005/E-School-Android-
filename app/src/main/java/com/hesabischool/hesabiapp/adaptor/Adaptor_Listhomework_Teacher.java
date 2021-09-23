package com.hesabischool.hesabiapp.adaptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.ImageCash.ImageLoader;
import com.hesabischool.hesabiapp.R;
import com.hesabischool.hesabiapp.Taklif_ListStudent_teacher;
import com.hesabischool.hesabiapp.vm_ModelServer.HomeworkShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatGroupOnlineViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptor_Listhomework_Teacher extends RecyclerView.Adapter<Adaptor_Listhomework_Teacher.MyviewHolder> {
    Context context;
    ImageLoader imgLoader;

    public List<HomeworkShowResult> vm;
  //  AlertDialog alertDialog;

    public Adaptor_Listhomework_Teacher(Context context, List<HomeworkShowResult> vm) {
        this.context = context;
        this.vm = vm;
      //  this.alertDialog=alertDialog;

    }

    @NonNull
    @Override
    public Adaptor_Listhomework_Teacher.MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_taklif_teacher, viewGroup, false);
        return new Adaptor_Listhomework_Teacher.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_Listhomework_Teacher.MyviewHolder holder, int position) {
        HomeworkShowResult v=vm.get(position);
holder.txt_titel.setText(v.HomeworkTile);
holder.txt_CountSender.setText(String.valueOf(v.StudentAnswerNumber));
holder.txt_TotaStudentCount.setText(String.valueOf(v.StudentAllNumber));
holder.txtstartdate.setText(app.Convert.CovertToPersianDate(v.HomeworkStartDate.toString()));
holder.txtEndDate.setText(app.Convert.CovertToPersianDate(v.HomeworkFinishDate.toString()));
holder.btnsee.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        app.Info.idhomework=v.HomeworkID;
        Intent i=new Intent(context, Taklif_ListStudent_teacher.class);
        context.startActivity(i);
    }
});
    }

    @Override
    public int getItemCount() {
        return vm.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        Button btnsee,btnedit,btndelete;
        ImageView imgtype;
        TextView txt_CountSender,txt_TotaStudentCount,txt_titel,txtEndDate,txtstartdate;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            btnsee = itemView.findViewById(R.id.btnsee);
            btndelete = itemView.findViewById(R.id.btndelete);
            btnedit = itemView.findViewById(R.id.btnedit);
            imgtype = itemView.findViewById(R.id.imgtype);
            txtstartdate = itemView.findViewById(R.id.txtstartdate);
            txtEndDate = itemView.findViewById(R.id.txtEndDate);
            txt_titel = itemView.findViewById(R.id.txt_titel);
            txt_TotaStudentCount = itemView.findViewById(R.id.txt_TotaStudentCount);
            txt_CountSender = itemView.findViewById(R.id.txt_CountSender);


        }
    }
}
