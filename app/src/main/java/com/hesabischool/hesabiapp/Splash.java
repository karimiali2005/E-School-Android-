package com.hesabischool.hesabiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.hesabischool.hesabiapp.Clases.ExceptionHandler;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.database.dbConnector;

public class Splash extends AppCompatActivity {
Context context;
dbConnector db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_splash);
        context =this;
        db=new dbConnector(context);
        try {
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chekuserexist();

                }
            },1000);
        }catch (Exception ex)
        {

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
            Intent i=new Intent(context,MainChat.class);
            startActivity(i);
            finish();
        }else
        {
            Intent i=new Intent(context,Login.class);
            //    Intent i=new Intent(context,MainChat.class);
            startActivity(i);
            finish();
        }
    }
}