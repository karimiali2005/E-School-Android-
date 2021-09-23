package com.hesabischool.hesabiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.ImageCash.ImageLoader;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer9;
import com.hesabischool.hesabiapp.vm_ModelServer.vm_HomeworkDetailsShowByIDResult;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class details_ansver_teacher extends AppCompatActivity {
Context context;
CircleImageView img;
ImageLoader imgLoader;
TextView txtname,txt_stucion,txtDateSee,txtDateSend,txt_DateEnd,txtAnsverStudent,txt_ansverTeachr,txt_studentDes;
vm_HomeworkDetailsShowByIDResult vm=new vm_HomeworkDetailsShowByIDResult();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_ansver_teacher);
        context=this;
        txt_studentDes=findViewById(R.id.txt_studentDes);
        txt_ansverTeachr=findViewById(R.id.txt_ansverTeachr);
        txtAnsverStudent=findViewById(R.id.txtAnsverStudent);
        txt_DateEnd=findViewById(R.id.txt_DateEnd);
        txtDateSend=findViewById(R.id.txtDateSend);
        txtDateSee=findViewById(R.id.txtDateSee);
        txt_stucion=findViewById(R.id.txt_stucion);
        img=findViewById(R.id.img);
        txtname=findViewById(R.id.txtname);
        imgLoader = new ImageLoader(context);

        Risave();
    }

    private void Risave() {
        app.progress.onCreateDialog(context);
        app.retrofit.retrofit().ManageAnswerHomWorkDetails(app.Info.idhomework,app.Info.idStudent).enqueue(new Callback<GetDataFromServer9>() {
            @Override
            public void onResponse(Call<GetDataFromServer9> call, Response<GetDataFromServer9> response) {
                app.retrofit.erorRetrofit(response,context);
                if(response.isSuccessful())
                {
                   vm=response.body().value;
                   SetValueToUi();
                }
            }

            @Override
            public void onFailure(Call<GetDataFromServer9> call, Throwable t) {
app.retrofit.FailRetrofit(t,context);
            }
        });
    }

    private void SetValueToUi() {
        imgLoader.DisplayPicture(vm.UserID, img);
        txt_ansverTeachr.setText(vm.TeacherComment);
        txt_DateEnd.setText(app.Convert.CovertToPersianDate(vm.HomeworkAnswerFinishDate));
        txtDateSend.setText(app.Convert.CovertToPersianDate(vm.HomeworkAnswerSartDate));
        txt_stucion.setText(vm.HomeworkAnswerStatusTitle);
        txtAnsverStudent.setText(vm.HomeworkResponse);
        txt_studentDes.setText(vm.HomeworkAnswerComment);



    }
}