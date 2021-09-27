package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

public class VersioningViewModel
{

    @SerializedName("versionCodeForceInstalling")
    public int VersionCodeForceInstalling ;
    @SerializedName("versionNameForceInstalling")
    public String VersionNameForceInstalling ;
    @SerializedName("versionCodeNoForceInstalling")
    public int VersionCodeNoForceInstalling ;
    @SerializedName("versionNameNoForceInstalling")
    public String VersionNameNoForceInstalling ;

    @SerializedName("versionUrlNoForceInstalling")
    public String VersionUrlNoForceInstalling ;
    @SerializedName("versionUrlForceInstalling")
    public String VersionUrlForceInstalling ;
}
