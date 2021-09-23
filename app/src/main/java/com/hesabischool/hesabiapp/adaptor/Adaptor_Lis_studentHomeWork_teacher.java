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
import com.hesabischool.hesabiapp.details_ansver_teacher;
import com.hesabischool.hesabiapp.vm_ModelServer.HomeworkDetailShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.HomeworkShowResult;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptor_Lis_studentHomeWork_teacher extends RecyclerView.Adapter<Adaptor_Lis_studentHomeWork_teacher.MyviewHolder> {
    Context context;
    ImageLoader imgLoader;

    public List<HomeworkDetailShowResult> vm;
    AlertDialog alertDialog;

    public Adaptor_Lis_studentHomeWork_teacher(Context context, List<HomeworkDetailShowResult> vm) {
        this.context = context;
        this.vm = vm;
       // this.alertDialog=alertDialog;

    }

    @NonNull
    @Override
    public Adaptor_Lis_studentHomeWork_teacher.MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detils_ansver , viewGroup, false);
        return new Adaptor_Lis_studentHomeWork_teacher.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_Lis_studentHomeWork_teacher.MyviewHolder holder, int position) {

        HomeworkDetailShowResult _vm=vm.get(position);
        imgLoader = new ImageLoader(context);

        imgLoader.DisplayPicture(_vm.UserID, holder.img);

        holder.txtname.setText(_vm.FullName);
        holder.txtresulr.setText(_vm.FinalScore);
        holder.txt_des.setText(_vm.HomeworkResponse);
        holder.txtdatestart.setText(app.Convert.CovertToPersianDate(_vm.HomeworkAnswerSartString));
        holder.txtdate_end.setText(app.Convert.CovertToPersianDate(_vm.HomeworkAnswerFinishString));
        holder.txt_type.setText(_vm.HomeworkAnswerStatusTitle);

holder.btn_see.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        app.Info.idStudent=_vm.UserID;
        Intent i=new Intent(context, details_ansver_teacher.class);
        context.startActivity(i);
    }
});

     
    }

    @Override
    public int getItemCount() {
        return vm.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
       Button btn_see;
       TextView txtname,txtresulr,txt_des,txtdatestart,txtdate_end,txt_type;
        CircleImageView img;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
         btn_see=itemView.findViewById(R.id.btn_see);
            txtname=itemView.findViewById(R.id.txtname);
            txtresulr=itemView.findViewById(R.id.txtresulr);
            txt_des=itemView.findViewById(R.id.txt_des);
            txtdatestart=itemView.findViewById(R.id.txtdatestart);
            txtdate_end=itemView.findViewById(R.id.txtdate_end);
            txt_type=itemView.findViewById(R.id.txt_type);
            img=itemView.findViewById(R.id.img);

        }
    }
}
