package com.hesabischool.hesabiapp.Retrofit;


import com.google.gson.JsonObject;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer2;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer3;
import com.hesabischool.hesabiapp.vm_ModelServer.LoginUserResult;
import com.hesabischool.hesabiapp.vm_ModelServer.LoginViewModel;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatViewModel;
import com.hesabischool.hesabiapp.vm_ModelServer.UserPicViewModel;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RguestApi {

    String api = "api1/";
//===========================Login==========================

    @POST(api + "Accounts/Authenticate")
    Call<LoginUserResult> Login(@Body LoginViewModel loginViewModel);

    // ======================EndLogin=======================================
//=========================RightChat======================================
    @GET(api + "Member/RoomChatRight")
    Call<GetDataFromServer> RoomChatRight();
//==================EndRightChat===============================
    //=================RoomChatLeft============================

    @GET(api + "Member/RoomChatLeft")
    Call<GetDataFromServer2> RoomChatLeft(@Query("roomChatGroupID") int roomChatGroupID, @Query("roomChatGroupType") int roomChatGroupType, @Query("tagLearn") boolean tagLearn, @Query("newChatCount") int newChatCount, @Query("roomId") int roomId, @Query("teacherId") int teacherId, @Query("courseId") int courseId, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize, @Query("picName") String picName, @Query("roomChatGroupTitle") String roomChatGroupTitle);

    //  public async Task<IActionResult> RoomChatLeft2(int roomChatGroupID, bool tagLearn, int pageNumber, int pageSize, bool permissionStudentChatEdit, bool permissionStudentChatDelete)
    @GET(api + "Member/RoomChatLeft")
    Call<GetDataFromServer2> RoomChatLeft2(@Query("roomChatGroupID") int roomChatGroupID, @Query("tagLearn") boolean tagLearn, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize, @Query("permissionStudentChatEdit") boolean permissionStudentChatEdit, @Query("permissionStudentChatDelete") boolean permissionStudentChatDelete);

    //===================InsertRoomChatMessage========================
   //
    @POST(api + "Member/RoomChatInsert")
    Call<GetDataFromServer3> RoomChatInsert(@Query("roomChatGroupID") int roomChatGroupID, @Query("textChat") String textChat, @Query("fileName") String fileName, @Query("tagLearn") boolean tagLearn, @Query("roomChatParentId") int roomChatParentId, @Query("roomId") int roomId, @Query("teacherId") int teacherId, @Query("courseId") int courseId, @Query("parentTextChat") String parentTextChat, @Query("parentSenderName") String parentSenderName);
//============================EditeRoomChatMessage============================

    @POST(api + "Member/RoomChatEdit")
    Call<GetDataFromServer3> RoomChatEdit(@Query("roomChatId") int roomChatId,@Query("textChat") String textChat, @Query("tagLearn") boolean tagLearn, @Query("parentTextChat") String parentTextChat, @Query("parentSenderName") String parentSenderName);
//============================DeleteRoomChatMessage========================================
@POST(api + "Member/RoomChatDelete")
Call<GetDataFromServer3> RoomChatDelete(@Query("roomChatId") int roomChatId);

//==============================DeletemySelf=================================

    @POST(api + "Member/RoomChatViewDelete")
    Call<GetDataFromServer3> RoomChatViewDelete(@Query("roomChatId") int roomChatId);
    //======================================UploadFile===================

@Multipart
@POST(api+"Member/StoreFile")
Call<GetDataFromServer3> StoreFile(@Part MultipartBody.Part file,@Query("tagLearn") String tagLearn,@Query("roomChatGroupId") int roomChatGroupId,@Query("textChat") String textChat,@Query("roomChatParentId") int roomChatParentId
,@Query("roomId")  int roomId,@Query("teacherId")  int teacherId,@Query("courseId")  int courseId,@Query("parentTextChat")  String parentTextChat,@Query("parentSenderName")  String parentSenderName);

    @Multipart
    @POST(api+"Member/SendRecordAudio")
    Call<GetDataFromServer3> SendRecordAudio(@Part MultipartBody.Part file,@Query("tagLearn") String tagLearn,@Query("roomChatGroupId") int roomChatGroupId,@Query("textChat") String textChat,@Query("roomChatParentId") int roomChatParentId
            ,@Query("roomId")  int roomId,@Query("teacherId")  int teacherId,@Query("courseId")  int courseId,@Query("parentTextChat")  String parentTextChat,@Query("parentSenderName")  String parentSenderName);
//=================================================================InsertViewrOOMcHAT=====================================

    @POST(api + "Member/RoomChatViewInsert")
    Call<Object> RoomChatViewInsert(@Query("roomChatId") int roomChatId,@Query("roomChatGroupId") int roomChatGroupId);
//=============================PinAndUnpin======================================
@POST(api + "Member/RoomChatPin")
Call<GetDataFromServer3> RoomChatPin(@Query("roomChatGroupId") int roomChatGroupId,@Query("roomChatId") int roomChatId, @Query("isPin") boolean isPin);
//===================================LockAndUnlock==============================================
//=============================PinAndUnpin======================================
@POST(api + "Member/RoomChatLock")
Call<GetDataFromServer3> RoomChatLock(@Query("roomChatGroupId") int roomChatGroupId, @Query("isLock") boolean isLock);
  //==========================================Pic=============
  @GET(api + "Accounts/GetUserPicName")
  Call<UserPicViewModel> GetUserPicName(@Query("userId") int userId);
    //===================================Get user Pic Name==================================
    @GET(api + "Accounts/GetUserPic")
    Call<String> GetUserPic(@Query("picName") String picName);


}
