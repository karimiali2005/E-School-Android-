package com.hesabischool.hesabiapp.vm_ModelServer;

import java.util.Date;

public class LoginUserResult {
    public int userID;
    public String fullName;
    public String irNational;
    public String birthDate;
    public int userTypeID;
    public String userTypeTitle;
    public int userActive;
    public String reasonInactive;
    public String mobileNumber;
    public String picName;
    public String password;
    public String examAddress;
    public String token;

    public LoginUserResult() {
    }

    public LoginUserResult(int userID, String fullName, String irNational, String birthDate, int userTypeID, String userTypeTitle, int userActive, String reasonInactive, String mobileNumber, String picName, String password, String examAddress, String token) {
        this.userID = userID;
        this.fullName = fullName;
        this.irNational = irNational;
        this.birthDate = birthDate;
        this.userTypeID = userTypeID;
        this.userTypeTitle = userTypeTitle;
        this.userActive = userActive;
        this.reasonInactive = reasonInactive;
        this.mobileNumber = mobileNumber;
        this.picName = picName;
        this.password = password;
        this.examAddress = examAddress;
        token = token;
    }
}
