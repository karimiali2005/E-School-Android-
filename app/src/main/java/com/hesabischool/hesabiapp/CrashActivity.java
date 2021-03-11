package com.hesabischool.hesabiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hesabischool.hesabiapp.Clases.app;

public class CrashActivity extends AppCompatActivity {
    TextView txt_eror;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        context = this;
        txt_eror = findViewById(R.id.txt_eror);
        Intent intent = this.getIntent();
        String data = intent.getStringExtra("error");
        if (!app.check.EpmtyOrNull(data)) {
            txt_eror.setText(data);
        }
    }
}