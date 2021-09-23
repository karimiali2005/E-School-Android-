package com.hesabischool.hesabiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.adaptor.Adaptor_Listhomework_Teacher;
import com.hesabischool.hesabiapp.adaptor.Adaptor_chatRight;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer7;
import com.hesabischool.hesabiapp.vm_ModelServer.HomeworkShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatRightShowResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Taklif extends AppCompatActivity {
Context context;
RecyclerView shimmerRecycler;
Adaptor_Listhomework_Teacher ma;
int pagenumber=1;
int pagesize=10;
    RoomChatRightShowResult rcharright = new RoomChatRightShowResult();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taklif);
        context=this;
        rcharright = app.Info.checkpage.roomchatright;
Risave();
    }
    private  void Risave()
    {
        app.progress.onCreateDialog(context);
        app.retrofit.retrofit().ManageHomWork(rcharright.RoomID,rcharright.CourseID,pagenumber,pagesize).enqueue(new Callback<GetDataFromServer7>() {
            @Override
            public void onResponse(Call<GetDataFromServer7> call, Response<GetDataFromServer7> response) {
                app.retrofit.erorRetrofit(response,context);
                if(response.isSuccessful())
                {
                    SetRecycler(response.body().value);

                }
            }

            @Override
            public void onFailure(Call<GetDataFromServer7> call, Throwable t) {
                app.retrofit.FailRetrofit(t,context);
            }
        });

    }


    private void SetRecycler(List<HomeworkShowResult> vm) {

        //  shimmerRecycler.showShimmerAdapter();
        shimmerRecycler=findViewById(R.id.rec);
        shimmerRecycler.removeAllViews();
        LinearLayoutManager layoutManager =  new LinearLayoutManager(context);


        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.setHasFixedSize(true);
        shimmerRecycler.setItemViewCacheSize(20);
        shimmerRecycler.setDrawingCacheEnabled(true);
        shimmerRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        ma = new Adaptor_Listhomework_Teacher(context, vm);
        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.setAdapter(ma);

    }
}