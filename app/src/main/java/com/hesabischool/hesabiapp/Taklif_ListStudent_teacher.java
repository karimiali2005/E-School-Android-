package com.hesabischool.hesabiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.adaptor.Adaptor_Lis_studentHomeWork_teacher;
import com.hesabischool.hesabiapp.adaptor.Adaptor_Listhomework_Teacher;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer8;
import com.hesabischool.hesabiapp.vm_ModelServer.HomeworkDetailShowResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Taklif_ListStudent_teacher extends AppCompatActivity {
    Context context;
    RecyclerView shimmerRecycler;
    Adaptor_Lis_studentHomeWork_teacher ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taklif__list_student_teacher);
        context=this;
        Risave();
    }
    private  void Risave()
    {
        app.progress.onCreateDialog(context);
       app.retrofit.retrofit().ManageHomWorkDetails(app.Info.idhomework).enqueue(new Callback<GetDataFromServer8>() {
           @Override
           public void onResponse(Call<GetDataFromServer8> call, Response<GetDataFromServer8> response) {
               app.retrofit.erorRetrofit(response,context);
               if(response.isSuccessful())
               {
                   SetRecycler(response.body().value);
               }
           }

           @Override
           public void onFailure(Call<GetDataFromServer8> call, Throwable t) {
app.retrofit.FailRetrofit(t,context);
           }
       });

    }


    private void SetRecycler(List<HomeworkDetailShowResult> vm) {

        //  shimmerRecycler.showShimmerAdapter();
        shimmerRecycler=findViewById(R.id.rec);
        shimmerRecycler.removeAllViews();
        LinearLayoutManager layoutManager =  new LinearLayoutManager(context);


        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.setHasFixedSize(true);
        shimmerRecycler.setItemViewCacheSize(20);
        shimmerRecycler.setDrawingCacheEnabled(true);
        shimmerRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        ma = new Adaptor_Lis_studentHomeWork_teacher(context, vm);
        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.setAdapter(ma);

    }
}