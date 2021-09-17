package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class HomeworkAnswerStudentShowResult
{
    @SerializedName("homeworkID")
    public int HomeworkID ;
    @SerializedName("homeworkTile")
    public String HomeworkTile ;
    @SerializedName("homeworkDescription")
    public String HomeworkDescription ;
    @SerializedName("homeworkStartDate")
    public Date HomeworkStartDate ;
    @SerializedName("homeworkFinishDate")
    public Date HomeworkFinishDate ;
    @SerializedName("homeworkTypeID")
    public int HomeworkTypeID ;
    @SerializedName("userID")
    public int UserID ;
    @SerializedName("courseID")
    public int CourseID ;
    @SerializedName("roomID")
    public int RoomID ;
    @SerializedName("homeworkAnswerStatusID")
    public int HomeworkAnswerStatusID ;
    @SerializedName("homeworkAnswerStatusTitle")
    public String HomeworkAnswerStatusTitle ;
    @SerializedName("homeworkAnswerID")
    public int HomeworkAnswerID ;
    @SerializedName("visitDateTime")
    public Date VisitDateTime ;
    @SerializedName("editDateTime")
    public Date EditDateTime ;
    @SerializedName("sendDatetime")
    public Date SendDatetime ;
    @SerializedName("scoreTypeID")
    public int ScoreTypeID ;
    @SerializedName("homeworkAnswerScore")
    public String HomeworkAnswerScore ;
    @SerializedName("teacherComment")
    public String TeacherComment ;
    @SerializedName("recordCount")
    public int RecordCount ;

}

