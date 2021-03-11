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

import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.textfield.TextInputEditText;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.R;

import static com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap;

public class MohsenEditText extends TextInputEditText {


    public MohsenEditText(@NonNull Context context) {
        this(context, null);
    }

    public MohsenEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public MohsenEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(wrap(context, attrs, defStyleAttr, 0), attrs, defStyleAttr);
        @SuppressLint("RestrictedApi") TypedArray attributes =
                ThemeEnforcement.obtainStyledAttributes(
                        context,
                   attrs,
                R.styleable.TextInputEditText,
                defStyleAttr,
                R.style.Widget_Design_TextInputEditText);

        setTextInputLayoutFocusedRectEnabled(
                attributes.getBoolean(R.styleable.TextInputEditText_textInputLayoutFocusedRectEnabled, false));

        attributes.recycle();

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
                String s=app.Convert.ConvertFAToEN(editable.toString());
                setText(s);
                setSelection(s.length());
                addTextChangedListener(this);
            }
        });
    }


}
