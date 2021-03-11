package com.hesabischool.hesabiapp.Clases;

import com.hesabischool.hesabiapp.viewmodel.vm_Input;

import java.util.ArrayList;
import java.util.List;

public class checkInpute {
private List<vm_Input> vm_inputs=new ArrayList<>();

    public checkInpute() {
    }

    public void addcheckInpute(vm_Input vi) {
        vm_inputs.add(vi);
    }

    public boolean checkinput()
    {
        boolean b=true;
        for(vm_Input vi:vm_inputs)
        {

           if(vi.isnull==true||!app.check.EpmtyOrNull(vi.EditText.getText().toString()))
            {
                vi.inputLayout.setHelperText("");
                String vlaue=vi.EditText.getText().toString();
            }else
           {
               vi.inputLayout.setHelperText((vi.txthelper.equals("")?"وارد کردن این قسمت اجباری است":vi.txthelper));
               b=false;
           }

        }
        return b;
    }

    public String getValueinput(int postion)
    {
        String value;
        try{
            vm_Input vi=vm_inputs.get(postion);
            value=app.Convert.ConvertFAToEN(vi.EditText.getText().toString());
            return value;

        }catch (Exception ex)
        {
            return ex.getMessage();
        }
    }

}
