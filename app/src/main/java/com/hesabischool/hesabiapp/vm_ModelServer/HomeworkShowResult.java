package com.hesabischool.hesabiapp.vm_ModelServer;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class HomeworkShowResult
{
    @SerializedName("homeworkID")
    public int HomeworkID;
    @SerializedName("homeworkTile")
    public String HomeworkTile;
    @SerializedName("homeworkDescription")
    public String HomeworkDescription;
    @SerializedName("homeworkTypeID")
    public int HomeworkTypeID;
    @SerializedName("scoreTypeID")
    public int ScoreTypeID;
    @SerializedName("scoreTypeTitle")
    public String ScoreTypeTitle;
    @SerializedName("homeworkStartDate")
    public String HomeworkStartDate;
    @SerializedName("homeworkFinishDate")
    public String HomeworkFinishDate;
    @SerializedName("studentAllNumber")
    public int StudentAllNumber;
    @SerializedName("studentAnswerNumber")
    public int StudentAnswerNumber;
    @SerializedName("recordCount")
    public int RecordCount;
}
