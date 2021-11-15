package com.hesabischool.hesabiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hesabischool.hesabiapp.Clases.ExceptionHandler;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.Clases.checkInpute;
import com.hesabischool.hesabiapp.database.dbConnector;
import com.hesabischool.hesabiapp.viewmodel.vm_Input;
import com.hesabischool.hesabiapp.vm_ModelServer.LoginUserResult;
import com.hesabischool.hesabiapp.vm_ModelServer.LoginViewModel;

import java.util.Collection;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    Context context;
    TextInputEditText edtpass, edtusername;
    TextInputLayout outlinedTextField, outlinedTextField2;
    Button btnlogin;
    checkInpute ci;
    dbConnector db;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_login);
        try {
            context = this;
            db = new dbConnector(context);
            ci = new checkInpute();
            edtpass = findViewById(R.id.edtpass);
            edtusername = findViewById(R.id.edtusername);
            btnlogin = findViewById(R.id.btnlogin);
            outlinedTextField = findViewById(R.id.outlinedTextField);
            outlinedTextField2 = findViewById(R.id.outlinedTextField2);
            ci.addcheckInpute(new vm_Input(edtpass, outlinedTextField2, false, false, ""));
            ci.addcheckInpute(new vm_Input(edtusername, outlinedTextField, false, false, ""));
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ci.checkinput()) {
                        LoginViewModel vmu = new LoginViewModel();
                        vmu.usersName = edtusername.getText().toString();
                        vmu.usersPass = edtpass.getText().toString();
                        vmu.mobileName = getDeviceName();
                        vmu.PlatfornName = getAndroidVersion();
                        vmu.TokenFireBase= FirebaseInstanceId.getInstance().getToken();
                        Send(vmu);
                    }

                }
            });
            forgetpass();
            singin();
        } catch (Exception ex) {

        }
    }

    private void singin() {
        TextView txtsingin = findViewById(R.id.txt_singin);
        txtsingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openweb("https://www.hesabischool.com/Account/Register");
            }
        });

    }

    private void forgetpass() {
        TextView txt_forgetpass = findViewById(R.id.txt_forget_pass);
        txt_forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openweb("https://www.hesabischool.com/Account/ForgetPassword");
            }
        });

    }

    private void openweb(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
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

    public static String getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        return "Android SDK: " + sdkVersion + " (" + release + ")";
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }
}