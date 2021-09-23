package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


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
    @SerializedName("sendStringtime")
    public String SendStringtime ;
    @SerializedName("homeworkAnswerSartString")
    public String HomeworkAnswerSartString ;
    @SerializedName("homeworkAnswerFinishString")
    public String HomeworkAnswerFinishString ;
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
    @SerializedName("visitStringTime")
    public String VisitStringTime ;
    @SerializedName("editStringTime")
    public String EditStringTime ;
    @SerializedName("finalScore")
    public String FinalScore ;
    @SerializedName("homeworkAnswerStatusTitle")
    public String HomeworkAnswerStatusTitle ;
    @SerializedName("homeworkAnswerTeacherSee")
    public boolean HomeworkAnswerTeacherSee ;
}

