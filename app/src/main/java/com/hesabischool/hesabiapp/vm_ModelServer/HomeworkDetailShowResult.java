package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class HomeworkDetailShowResult
{
    @SerializedName("userID")
    public int UserID ;
    @SerializedName("picName")
    public String PicName ;
    @SerializedName("fullName")
    public String FullName ;
    @SerializedName("homeworkID")
    public int HomeworkID ;
    @SerializedName("homeworkAnswerID")
    public int HomeworkAnswerID ;
    @SerializedName("homeworkAnswerStatusID")
    public int HomeworkAnswerStatusID ;
    @SerializedName("sendDatetime")
    public Date SendDatetime ;
    @SerializedName("homeworkAnswerSartDate")
    public Date HomeworkAnswerSartDate ;
    @SerializedName("homeworkAnswerFinishDate")
    public Date HomeworkAnswerFinishDate ;
    @SerializedName("teacherComment")
    public String TeacherComment ;
    @SerializedName("homeworkAnswerScore")
    public int HomeworkAnswerScore ;
    @SerializedName("homeworkAnswerDescriptiveID")
    public int HomeworkAnswerDescriptiveID ;
    @SerializedName("homeworkResponse")
    public String HomeworkResponse ;
    @SerializedName("homeworkAnswerComment")
    public String HomeworkAnswerComment ;
    @SerializedName("visitDateTime")
    public Date VisitDateTime ;
    @SerializedName("editDateTime")
    public Date EditDateTime ;
    @SerializedName("finalScore")
    public String FinalScore ;
    @SerializedName("homeworkAnswerStatusTitle")
    public String HomeworkAnswerStatusTitle ;
    @SerializedName("homeworkAnswerTeacherSee")
    public boolean HomeworkAnswerTeacherSee ;
}

