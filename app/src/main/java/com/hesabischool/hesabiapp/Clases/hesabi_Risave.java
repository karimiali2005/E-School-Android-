package com.hesabischool.hesabiapp.Clases;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.hesabischool.hesabiapp.database.dbConnector;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class hesabi_Risave {
 Context context;
    dbConnector db;
    public hesabi_Risave(Context context) {
        this.context = context;
        db=new dbConnector(context);
    }
    public void GetdateFromServerFirst() {
       // app.linProgress.showProgress(context,"درحال بررسی ....");
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                try {
                //    Toast.makeText(context, "ServiceRun", Toast.LENGTH_SHORT).show();
                    if (app.net.isNetworkConnected(context)) {
                        try {
                            app.retrofit.retrofit().RoomChatRight().enqueue(new Callback<GetDataFromServer>() {
                                @Override
                                public void onResponse(Call<GetDataFromServer> call, Response<GetDataFromServer> response) {

                                    app.retrofit.erorRetrofitForService(response, context);
                                    if (response.isSuccessful()) {
                                        GetDataFromServer r = response.body();
                                        db.dq.addOrUpdateData(r);

                                    }
app.linProgress.hideProgress(context);
                                }

                                @Override
                                public void onFailure(Call<GetDataFromServer> call, Throwable t) {

                                    app.retrofit.FailRetrofitForService(t, context);
                                    app.linProgress.hideProgress(context);
                                }
                            });
                        } catch (Exception ex) {

                            int x=0;
                            app.linProgress.hideProgress(context);
                        }
                    }
                } catch (Exception ex) {

                    int i = 0;
                    app.linProgress.hideProgress(context);
                }
            }
        });

    }
}
