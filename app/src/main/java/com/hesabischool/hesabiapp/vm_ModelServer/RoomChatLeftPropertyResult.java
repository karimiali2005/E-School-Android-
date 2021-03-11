package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

public class RoomChatLeftPropertyResult {
    @SerializedName("closeOnTime")
    public boolean CloseOnTime ;
    @SerializedName("closeChat")
    public boolean CloseChat ;
    @SerializedName("permissionCloseChat")
    public boolean PermissionCloseChat ;
    @SerializedName("permissionStudentChatEdit")
    public boolean PermissionStudentChatEdit ;
    @SerializedName("permissionStudentChatDelete")
    public boolean PermissionStudentChatDelete ;
    @SerializedName("jitsiLiveAddress")
    public String JitsiLiveAddress ;
    @SerializedName("jitsiLivePassword")
    public String JitsiLivePassword ;
    @SerializedName("adobeLiveAddress")
    public String AdobeLiveAddress ;
    @SerializedName("adobeLiveUsername")
    public String AdobeLiveUsername ;
    @SerializedName("adobeLivePass")
    public String AdobeLivePass ;
    @SerializedName("examAddress")
    public String ExamAddress ;
    @SerializedName("pinRoomChatID")
    public int PinRoomChatID ;
    @SerializedName("pinTextChat")
    public String PinTextChat ;
    @SerializedName("courseID")
    public int CourseID ;
    @SerializedName("roomID")
    public int RoomID ;
    @SerializedName("teacherID")
    public int TeacherID ;
    @SerializedName("roomChatGroupID")
    public int RoomChatGroupID ;
    @SerializedName("picName")
    public String PicName ;
    @SerializedName("roomChatGroupTitle")
    public String RoomChatGroupTitle ;
    @SerializedName("roomChatGroupType")
    public int RoomChatGroupType ;
    @SerializedName("roomChatViewLast")
    public int RoomChatViewLast ;

}
