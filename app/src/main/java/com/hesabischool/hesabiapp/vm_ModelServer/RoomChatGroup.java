package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

public  class RoomChatGroup
{

    @SerializedName("roomChatGroupId")
    public int RoomChatGroupId ;

    @SerializedName("roomChatGroupTitle")
    public String RoomChatGroupTitle ;
    @SerializedName("roomId")
    public int RoomId ;
    @SerializedName("teacherId")
    public int TeacherId ;
    @SerializedName("courseId")
    public int CourseId ;
    @SerializedName("roomChatGroupCreateDateTime")
    public String RoomChatGroupCreateDateTime ;
    @SerializedName("roomChatGroupDelete")
    public boolean RoomChatGroupDelete ;
    @SerializedName("roomChatGroupVisible")
    public boolean RoomChatGroupVisible ;
    @SerializedName("studentId")
    public int StudentId ;
    @SerializedName("roomChatGroupType")
    public int RoomChatGroupType ;
    @SerializedName("closeChat")
    public boolean CloseChat ;
    @SerializedName("roomChatCount")
    public int RoomChatCount ;
    @SerializedName("roomChatLast")
    public int RoomChatLast ;
    @SerializedName("roomChatGroupDeleteStudent")
    public boolean RoomChatGroupDeleteStudent ;
    @SerializedName("roomChatGroupDeleteTeacher")
    public boolean RoomChatGroupDeleteTeacher ;

}
