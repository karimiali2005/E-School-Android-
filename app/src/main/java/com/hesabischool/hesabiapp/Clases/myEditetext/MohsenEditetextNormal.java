package com.hesabischool.hesabiapp.Clases.myEditetext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.internal.ThemeEnforcement;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.R;

public class MohsenEditetextNormal extends AppCompatEditText {

    public MohsenEditetextNormal(@NonNull Context context) {
        super(context);
    }

    public MohsenEditetextNormal(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MohsenEditetextNormal(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                removeTextChangedListener(this);
                //.. do changes here ..//
                String s= app.Convert.ConvertFAToEN(editable.toString());
                setText(s);
                setSelection(s.length());
                addTextChangedListener(this);
            }
        });
    }
}
