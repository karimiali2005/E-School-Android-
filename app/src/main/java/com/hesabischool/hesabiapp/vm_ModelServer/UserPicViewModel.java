package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserPicViewModel {
    @SerializedName("userID")
    public int UserId;
    @SerializedName("userPicName")
    public String UserPicName;
}

