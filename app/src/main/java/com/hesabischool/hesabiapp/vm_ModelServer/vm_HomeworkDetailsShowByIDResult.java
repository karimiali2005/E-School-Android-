package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class vm_HomeworkDetailsShowByIDResult
{
    @SerializedName("homeworkAnswerID")
    public int HomeworkAnswerID ;
    @SerializedName("homeworkAnswerStatusID")
    public int HomeworkAnswerStatusID ;
    @SerializedName("homeworkAnswerStatusTitle")
    public String HomeworkAnswerStatusTitle ;
    @SerializedName("VisitDateTime")
    public Date VisitDateTime ;
    @SerializedName("editDateTime")
    public Date EditDateTime ;
    @SerializedName("sendDatetime")
    public Date SendDatetime ;
    @SerializedName("userID")
    public int UserID ;
    @SerializedName("picName")
    public String PicName ;
    @SerializedName("FullName")
    public String FullName ;
    @SerializedName("homeworkResponse")
    public String HomeworkResponse ;
    @SerializedName("homeworkAnswerComment")
    public String HomeworkAnswerComment ;
    @SerializedName("homeworkAnswerScore")
    public int HomeworkAnswerScore ;
    @SerializedName("homeworkAnswerDescriptiveID")
    public int HomeworkAnswerDescriptiveID ;
    @SerializedName("teacherComment")
    public String TeacherComment ;
    @SerializedName("homeworkAnswerSartDate")
    public Date HomeworkAnswerSartDate ;
    @SerializedName("homeworkAnswerFinishDate")
    public Date HomeworkAnswerFinishDate ;
    @SerializedName("fileAnswers")
    public ArrayList<FileAnswer> FileAnswers ;
    @SerializedName("scoreTypeID")
    public int ScoreTypeID ;
    @SerializedName("scoreTypes")
    public ArrayList<ScoreTypes> ScoreTypes ;
    @SerializedName("isNumber")
    public boolean IsNumber ;

}
