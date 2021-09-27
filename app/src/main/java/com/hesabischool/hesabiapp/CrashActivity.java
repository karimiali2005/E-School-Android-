package com.hesabischool.hesabiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.hesabischool.hesabiapp.Clases.app;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class CrashActivity extends AppCompatActivity {
    TextView txt_eror;
    Context context;
    MaterialCardView materialCardView;
    Button btn;
    LinearLayout lin;
    ConstraintLayout con;
    ImageView imgheader;
    String datevalue;
    int click=0;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        context = this;
        txt_eror = findViewById(R.id.txt_eror);
        materialCardView = findViewById(R.id.card_loading);
        imgheader = findViewById(R.id.imgheader);
        lin = findViewById(R.id.lin);
        con = findViewById(R.id.con);
        btn = findViewById(R.id.btn);

        Intent intent = this.getIntent();
        String data = intent.getStringExtra("error");
        if (!app.check.EpmtyOrNull(data)) {
            txt_eror.setText(data);
            datevalue=data;
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, datevalue);
                startActivity(Intent.createChooser(share, "Share using"));
            }
        });
        materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,Splash.class);
                startActivity(i);
                finish();
            }
        });
imgheader.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        click++;
        if(click>=5)
        {
            con.setVisibility(View.GONE);
            lin.setVisibility(View.VISIBLE);

        }
    }
});

    }
    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        // Do extra stuff here
        Toast.makeText(context, "امکان بازگشت وجود ندارد.", Toast.LENGTH_SHORT).show();
    }
}