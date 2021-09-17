package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class vm_AnsverhomeWork
{
    @SerializedName("homeworkAnswerId")
    public int HomeworkAnswerId ;
    @SerializedName("homeworkId")
    public int HomeworkId ;
    @SerializedName("roomId")
    public int RoomId ;
    @SerializedName("courseId")
    public int CourseId ;
    @SerializedName("homeworktitel")
    public String Homeworktitel ;
    @SerializedName("homeWorkFileName")
    public String HomeWorkFileName ;
    @SerializedName("homeworkDescription")
    public String HomeworkDescription ;
    @SerializedName("homeworkTypeID")
    public int HomeworkTypeID ;
    @SerializedName("homeworkAnswerStatusId")
    public int HomeworkAnswerStatusId ;
    //پاسخ سوال در صورت متنی بودن
    @SerializedName("homeworkResponse")
    public String HomeworkResponse ;
    @SerializedName("homeworkAnswerComment")
    public String HomeworkAnswerComment ;
    @SerializedName("homeworkAnswerScore")
    public int HomeworkAnswerScore ;
    @SerializedName("homeworkResponseFile")
    public ArrayList<vmResponsFile> HomeworkResponseFile ;

}
