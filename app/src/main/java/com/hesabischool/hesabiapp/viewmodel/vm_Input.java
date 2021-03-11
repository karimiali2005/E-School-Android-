package com.hesabischool.hesabiapp.viewmodel;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class vm_Input {
    public TextInputEditText EditText;
    public TextInputLayout inputLayout;
    public boolean isnull=false;
    public boolean idmobile=false;
    public String txthelper="";

    public vm_Input(TextInputEditText editText, TextInputLayout inputLayout, boolean isnull, boolean idmobile, String txthelper) {
        EditText = editText;
        this.inputLayout = inputLayout;
        this.isnull = isnull;
        this.idmobile = idmobile;
        this.txthelper = txthelper;
    }


}
