package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RoomChatLeftShowResult {
    @SerializedName("roomChatID")
    public int RoomChatID ;
    @SerializedName("roomChatDate")
    public String RoomChatDate ;
    @SerializedName("senderID")
    public int SenderID ;
    @SerializedName("senderName")
    public String SenderName ;
    @SerializedName("recieverID")
    public int RecieverID ;
    @SerializedName("recieverName")
    public String RecieverName ;
    @SerializedName("roomID")
    public int RoomID ;
    @SerializedName("teacherID")
    public int TeacherID ;
    @SerializedName("courseID")
    public int CourseID ;
    @SerializedName("textChat")
    public String TextChat ;
    @SerializedName("filename")
    public String Filename ;
    @SerializedName("tagLearn")
    public boolean TagLearn ;
    @SerializedName("roomChatDelete")
    public boolean RoomChatDelete ;
    @SerializedName("roomChatUpdate")
    public String RoomChatUpdate ;
    @SerializedName("roomChatParentID")
    public int RoomChatParentID ;
    @SerializedName("attachMsg")
    public boolean AttachMsg ;
    @SerializedName("roomChatGroupID")
    public int RoomChatGroupID ;
    @SerializedName("parentSenderName")
    public String ParentSenderName ;
    @SerializedName("parentTextChat")
    public String ParentTextChat ;
    @SerializedName("roomChatViewNumber")
    public int RoomChatViewNumber ;
    @SerializedName("mimeType")
    public String MimeType ;
    @SerializedName("roomChatDateString")
    public String RoomChatDateString;
    @SerializedName("isSqlLite")
    public boolean IsSqlLite=false;
    @SerializedName("roomChatGroupTitle")
    public String RoomChatGroupTitle="-";
    @SerializedName("roomChatFolder")
    public String RoomChatFolder="";
    @SerializedName("roomChatGroupType")
    public int RoomChatGroupType=1;
  public  String mainAdress;
    public RoomChatLeftShowResult() {
    }

    public RoomChatLeftShowResult(int roomChatID, String roomChatDate, int senderID, String senderName, int recieverID, String recieverName, int roomID, int teacherID, int courseID, String textChat, String filename, boolean tagLearn, boolean roomChatDelete, String roomChatUpdate, int roomChatParentID, boolean attachMsg, int roomChatGroupID, String parentSenderName, String parentTextChat, int roomChatViewNumber, String mimeType, String roomChatDateString, boolean isSqlLite,String roomChatGroupTitle,int roomChatGroupType,String roomChatFolder) {
        RoomChatID = roomChatID;
        RoomChatDate = roomChatDate;
        SenderID = senderID;
        SenderName = senderName;
        RecieverID = recieverID;
        RecieverName = recieverName;
        RoomID = roomID;
        TeacherID = teacherID;
        CourseID = courseID;
        TextChat = textChat;
        Filename = filename;
        TagLearn = tagLearn;
        RoomChatDelete = roomChatDelete;
        RoomChatUpdate = roomChatUpdate;
        RoomChatParentID = roomChatParentID;
        AttachMsg = attachMsg;
        RoomChatGroupID = roomChatGroupID;
        ParentSenderName = parentSenderName;
        ParentTextChat = parentTextChat;
        RoomChatViewNumber = roomChatViewNumber;
        MimeType = mimeType;
        RoomChatDateString = roomChatDateString;
        IsSqlLite = isSqlLite;
        RoomChatGroupTitle=roomChatGroupTitle;
        RoomChatGroupType=roomChatGroupType;
        RoomChatFolder=roomChatFolder;
    }
}
