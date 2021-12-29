package com.hesabischool.hesabiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hesabischool.hesabiapp.Clases.ExceptionHandler;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.database.dbConnector;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer15;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer17;
import com.hesabischool.hesabiapp.vm_ModelServer.LoginUserResult;
import com.hesabischool.hesabiapp.vm_ModelServer.LoginViewModel;
import com.hesabischool.hesabiapp.vm_ModelServer.SettingContextViewModel;
import com.hesabischool.hesabiapp.vm_ModelServer.VersioningViewModel;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hesabischool.hesabiapp.Login.getAndroidVersion;
import static com.hesabischool.hesabiapp.Login.getDeviceName;

public class Splash extends AppCompatActivity {
Context context;
dbConnector db;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_splash);
        context =this;
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        db=new dbConnector(context);
        try {
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
if(app.net.isNetworkConnected(context))
{
  // chekuserexist();
   GetVersion();
}else
{
                    chekuserexist();

}

                }
            },1000);
        }catch (Exception ex)
        {

        }


    }

    private void GetVersion() {
      app.progress.onCreateDialog(context);
      app.retrofit.retrofit().GetLoadVersion().enqueue(new Callback<GetDataFromServer15>() {
          @Override
          public void onResponse(Call<GetDataFromServer15> call, Response<GetDataFromServer15> response) {
              app.retrofit.erorRetrofit(response,context);
              if(response.isSuccessful())
              {
                  CheckVersioning(response.body().value);
              }
          }

          @Override
          public void onFailure(Call<GetDataFromServer15> call, Throwable t) {
              app.retrofit.FailRetrofit(t,context);
          }
      });


    }

    private void CheckVersioning(VersioningViewModel value) {
        int versionCode = -1;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            // = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(value.VersionCodeForceInstalling>versionCode)
        {
            View vd=app.Dialog_.dialog_creat(context,R.layout.dialog_update);
            TextView txt=vd.findViewById(R.id.txt);
            Button btn_dowanload=vd.findViewById(R.id.btn_download);
            Button btn_cancel=vd.findViewById(R.id.btn_cancel);
            btn_cancel.setVisibility(View.GONE);
            btn_dowanload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo Open file Dowanload
                    openweb(value.VersionUrlForceInstalling);
                }
            });
            txt.setText("نسخه فعلی شما منسوخ شده است لطفا نسبت به بروزرسانی اقدام نمایید.");
            app.Dialog_.show_dialog(context,vd,false);
            //اجبار
         //   Error = "NewVersionForce";
         //   VersionName=value.VersionNameForceInstalling;

        }
       else if(value.VersionCodeNoForceInstalling>versionCode)
        {
            //نسخه جدید در دسترس است در صورت تمایل  دانلود کنید
            View vd=app.Dialog_.dialog_creat(context,R.layout.dialog_update);
            TextView txt=vd.findViewById(R.id.txt);
            Button btn_dowanload=vd.findViewById(R.id.btn_download);
            Button btn_cancel=vd.findViewById(R.id.btn_cancel);
            txt.setText("نسخه بروز تری نسبت به نسخه فعلی در دسترس است در صورت تمایل  اقدام به دانلود نمایید.");
            AlertDialog al=app.Dialog_.show_dialog(context,vd,true);
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    app.Dialog_.dimos_dialog(al);
                }
            });
            btn_dowanload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo Open file Dowanload
                    openweb(value.VersionUrlNoForceInstalling);
                }
            });


        }else
        {
            chekuserexist();
        }
    }

    private void chekuserexist() {

        String query = "SELECT * FROM User ";
        Cursor c = null;
        c = db.select(query);
        if (c.moveToNext()) {
//isExsist
            app.Info.User.fullName=c.getString(c.getColumnIndex("fullName"));
            app.Info.User.birthDate=c.getString(c.getColumnIndex("birthDate"));
            app.Info.User.examAddress=c.getString(c.getColumnIndex("examAddress"));
            app.Info.User.irNational=c.getString(c.getColumnIndex("irNational"));
            app.Info.User.mobileNumber=c.getString(c.getColumnIndex("mobileNumber"));
            app.Info.User.password=c.getString(c.getColumnIndex("password"));
            app.Info.User.picName=c.getString(c.getColumnIndex("picName"));
            app.Info.User.reasonInactive=c.getString(c.getColumnIndex("reasonInactive"));
            app.Info.User.token=c.getString(c.getColumnIndex("token"));
            app.Info.User.userActive=c.getInt(c.getColumnIndex("userActive"));
            app.Info.User.userID=c.getInt(c.getColumnIndex("userID"));
            app.Info.User.userTypeID=c.getInt(c.getColumnIndex("userTypeID"));
            app.Info.User.userTypeTitle=c.getString(c.getColumnIndex("userTypeTitle"));

//todo if For Refresh
            if(app.Info.UnAturized)
            {
                LoginViewModel vmu=new LoginViewModel();
                vmu.usersName =  app.Info.User.irNational;
                vmu.usersPass = app.Info.User.password;
                vmu.mobileName = getDeviceName();
                vmu.PlatfornName = getAndroidVersion();
                vmu.TokenFireBase= FirebaseInstanceId.getInstance().getToken();
                Send(vmu);

            }else
            {
                Intent i=new Intent(context,MainChat.class);
                //=========================================Get ChatMessage If app Open By Notification ============================

                String newString="";
                Bundle extras = getIntent().getExtras();
                if(extras != null) {
                    newString= extras.getString("idc");
                    if(!app.check.EpmtyOrNull(newString))
                    {
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("idc", newString);
                    }
                }

                //=================================================================================================================
app.retrofit.retrofit().SettingGet().enqueue(new Callback<SettingContextViewModel>() {
    @Override
    public void onResponse(Call<SettingContextViewModel> call, Response<SettingContextViewModel> response) {
        app.retrofit.erorRetrofit(response,context);

        if(response.isSuccessful())
        {


        }
    }

    @Override
    public void onFailure(Call<SettingContextViewModel> call, Throwable t) {
app.retrofit.FailRetrofit(t,context);
    }
});

                startActivity(i);
                finish();
            }


        }
        else
        {
            Intent i=new Intent(context,Login.class);
            //    Intent i=new Intent(context,MainChat.class);
            startActivity(i);
            finish();
        }
    }
    private void Send(LoginViewModel vm) {
        try {

            app.progress.onCreateDialog(context);
            app.retrofit.retrofit().Login(vm).enqueue(new Callback<LoginUserResult>() {
                @Override
                public void onResponse(Call<LoginUserResult> call, Response<LoginUserResult> response) {
                    app.retrofit.erorRetrofit(response, context);
                    if (response.isSuccessful()) {
                        LoginUserResult  user=response.body();
                        user.password=vm.usersPass;
                        if (db.dq.AddOrUpdateUser(user)) {
                            app.Info.User = response.body();
                            Intent i = new Intent(context, MainChat.class);
                            startActivity(i);
                            finish();

                        } else {
                            Toast.makeText(context, R.string.problam, Toast.LENGTH_SHORT).show();
                        }


                    }
                    else
                    {
                        db.DeleteValueAllTable();
                        Intent i=new Intent(context,Login.class);
                        startActivity(i);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<LoginUserResult> call, Throwable t) {
                    app.retrofit.FailRetrofit(t, context);

                }
            });



        } catch (Exception ex) {
            throw ex;
        }


    }
    private void openweb(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}