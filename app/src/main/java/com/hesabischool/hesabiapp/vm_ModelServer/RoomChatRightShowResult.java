package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RoomChatRightShowResult {
    @SerializedName("roomChatGroupID")
    public int RoomChatGroupID ;
    @SerializedName("roomChatGroupType")
    public int RoomChatGroupType ;
    @SerializedName("courseID")
    public int CourseID ;
    @SerializedName("teacherID")
    public int TeacherID ;
    @SerializedName("roomID")
    public int RoomID ;
    @SerializedName("roomChatTitle")
    public String RoomChatTitle ;
    @SerializedName("roomChatDate")
    public String RoomChatDate ;
    @SerializedName("roomChatDateString")
    public String RoomChatDateString ;
    @SerializedName("textChat")
    public String TextChat ;
    @SerializedName("messageNewNumber")
    public int MessageNewNumber ;
    @SerializedName("homeWorkNewNumber")
    public int HomeWorkNewNumber ;
    @SerializedName("picName")
    public String PicName ;
}
