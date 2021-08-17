package com.hesabischool.hesabiapp.ImageCash;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shaon on 12/3/2016.
 */

public class ServerResponseUpload {
    // variable name should be same as in the json response from php    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}