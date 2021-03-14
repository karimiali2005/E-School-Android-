package com.hesabischool.hesabiapp.Retrofit;


import com.google.gson.JsonObject;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer2;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer3;
import com.hesabischool.hesabiapp.vm_ModelServer.LoginUserResult;
import com.hesabischool.hesabiapp.vm_ModelServer.LoginViewModel;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatViewModel;

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
    ////============================Factors============================
//@POST(api2+"Factor")
//Call<vm_factor> Factor(@Query("Id") int orderId);
//    //..................... EndFactors.............................
////===========================Addpeyments==========================
//@POST(api2+"AddPeyment")
//Call<Boolean> AddPeyment(@Query("orderId") int orderId, @Query("refId") int refId, @Query("status") int status, @Query("authority") String authority);
//    //........................EndPeyments............................
////=========================Chart================================
//@POST(api2+"VizitChart")
//Call<List<vm_chart>> VizitChart(@Query("id") String idadvert);
////============================================================
//@POST(api2+"promote")
//Call<Long> promote(@Body vm_promote vm_promote);
////============================================================
//@POST(api2+"PresentCity")
//Call<Boolean> PresentCity(@Body vm_PresentCity vm_presentCity);
////========================================================
//@FormUrlEncoded
//@POST(api2+"SuggestAdvert")
//Call<List<vmKala>> SuggestAdvert(@Field("cateid") int CityId);
////===================================================
//@POST(api2+"GetLastSeenAdvertsByUserId")
//Call<List<vmKala>> GetLastSeenAdvertsByUserId(@Body vm_CheckExistLastSeen vm_checkExistLastSeen);
////=================================================
//@POST(api2+"CheckExistLastSeen")
//Call<Boolean> CheckExistLastSeen(@Body vm_CheckExistLastSeen vm_checkExistLastSeen);
//@POST(api2+"AddLastSeen")
//Call<Integer> AddLastSeen(@Body vm_CheckExistLastSeen vm_checkExistLastSeen);
////==========================================
//@FormUrlEncoded
//@POST(api2+"SearchAdvanceApi")
//Call<List<vmKala>> SearchAdvanceApi(@Field("CityId") int CityId, @Field("fvAdverts") String fvAdverts, @Field("fvTahators") String fvTahators, @Field("mmAdverts") String mmAdverts, @Field("mmTahators") String mmTahators, @Field("qAdvert") String qAdvert, @Field("qTahator") String qTahator,
//                                    @Field("CategoryAdvert") int CategoryAdvert, @Field("CategoryTahator") String CategoryTahator, @Field("k2kId") Boolean k2kId, @Field("k2sId") Boolean k2sId,
//                                    @Field("s2sId") Boolean s2sId, @Field("s2kId") Boolean s2kId, @Field("isFovriId") Boolean isFovriId, @Field("hasPicId") Boolean hasPicId, @Field("noPriceId") Boolean noPriceId,
//                                    @Field("isFreeId") Boolean isFreeId);
//
//
//    //=========================================
//@GET(api2+"Pelans")
//Call<List<vm_pelan>> Pelans();
//
//    //=============================================
//
//    @GET(api+"GetStates")
//    Call<List<vm_state>> Get_Sate();
//    @GET(api+"getcities")
//    Call<List<vm_cites>> Get_City();
//
//    @GET(api+"getcategories")
//    Call<List<vm_categories>> GetCategories();
//
// @GET(api+"getfields")
//    Call<List<vm_fields>> GetFields();
//
//@GET(api+"getcategoryfields")
//    Call<List<vm_categoryfields>> GetCategoryFields();
//
//@GET(api+"getfieldlistvalues")
//    Call<List<vm_fieldlistvalues>> GetFieldListValues();
//
//    @POST(api2+"AddTahator")
//    Call<String> AddTahator(@Body TahatorMobileViewModel tahator);
//
// /* @POST(api2+"EditTahator")
//    Call<String> EditTahator(@Body TahatorMobileViewModel tahator);*/
// @FormUrlEncoded
// @POST(api2+"EditTahator")
// Call<String> EditTahator(@Field("CategoryId") int CategoryId, @Field("GroupCode") byte GroupCode
//         , @Field("Title") String Title,
//                          @Field("Des") String Des, @Field("FieldId") List<Integer> FieldId,
//                          @Field("FieldValue") List<String> FieldValue, @Field("AdvertId") Long AdvertId, @Field("TahatorId") Long TahatorId);
//
//  @POST(api2+"Delt")
//    Call<String> DelTahator(@Query("id") String id);
//
//    @POST(api2+"DelAds")
//    Call<String> DelAds(@Query("id") long id);
//
//    @FormUrlEncoded
//    @POST(api2+"addadvert")
//    Call<String> AddAdvert(@Field("CategoryId") int CategoryId, @Field("UserId") Long UserId
//            , @Field("CityId") int CityId, @Field("GroupCode") byte GroupCode
//            , @Field("Title") String Title,
//                           @Field("Des") String Des, @Field("FieldId") List<Integer> FieldId,
//                           @Field("FieldValue") List<String> FieldValue, @Field("Pic") List<String> Pic, @Field("IsFree") boolean isfree, @Field("Chat") boolean Chat, @Field("ShowMobile") boolean ShowMobile, @Field("Lat") String Lat, @Field("Lng") String Lng);
//
//    @FormUrlEncoded
//    @POST(api2+"EditAdvert")
//    Call<String> EditAdvert(@Field("AdvertId") Long AdvertId, @Field("CategoryId") int CategoryId, @Field("UserId") Long UserId
//            , @Field("CityId") int CityId, @Field("GroupCode") byte GroupCode
//            , @Field("Title") String Title,
//                            @Field("Des") String Des, @Field("FieldId") List<Integer> FieldId,
//                            @Field("FieldValue") List<String> FieldValue, @Field("Pic") List<String> Pic, @Field("IsFree") boolean isfree
//            , @Field("Chat") boolean Chat, @Field("ShowMobile") boolean ShowMobile, @Field("Lat") String Lat, @Field("Lng") String Lng);
//
//    @POST(api2+"GetMyAdvertApi")
//    Call<List<vmKala>> GetMyAdvertApi(@Query("mobile") String mobile);
//
//
// //   @HTTP(method = "GET", path = api2+"search", hasBody = true)
//  @POST(api2+"search")
//    Call<List<vmKala>> search(@Body SearchSimpleViewModel searchSimpleViewModel);
//
// @GET(api2+"EditAdvert")
//    Call<EditeAdvertViewMobileModel> EditAdvert(@Query("id") int idadvert);
//
//    @POST(api2+"getadvertbycityapi")
//    Call<List<vmKala>> GetAdvertbyCityapi(@Query("id") int id);
//
//    @POST(api2+"adsdetailapi")
//    Call<vmKala> AdsDetailapi(@Query("id") int id);
//
//    @POST(api2+"LoginApi")
//    Call<String> LoginApi(@Body LoginViewModel loginVm);
//  @POST(api2+"ConfirmApi")
//    Call<String> ConfirmApi(@Body ConfirmVmApi loginVm);
//
//    @POST(api2+"ReportAdvertApi")
//    Call<String> ReportAdvertApi(@Body ReportsAdvert reportsAdvert);
//
//    @POST(api2+"addnote")
//    Call<String> addnote(@Body vm_note vm_note);
//    @POST(api2+"delnote")
//    Call<String> delnote(@Body vm_note vm_note);
//
//  @POST(api2+"GetAdvertsNoteByUserId")
//    Call<List<vmKala>> GetAdvertsNoteByUserId(@Query("id") int id);
//
// @POST(api2+"GetAdvertsNoteByUserIdAdvertId")
//    Call<String> GetAdvertsNoteByUserIdAdvertId(@Body vm_note vm_note);
//
//
//    @POST(api2+"addbookmark")
//    Call<String> setbookmark(@Body BookMarkViewModelApi reportsAdvert);
//    @POST(api2+"deletemark")
//    Call<String> deletemark(@Body BookMarkViewModelApi reportsAdvert);
//    @POST(api2+"checkmark")
//    Call<Boolean> checkbookmark(@Body BookMarkViewModelApi reportsAdvert);
//    @POST(api2+"GetAdvertsBookMarkByUserId")
//    Call<List<vmKala>> GetAdvertsBookMarkByUserId(@Query("id") int id);
//
//    @POST(api+"UploadFile")
//    Call<String> upload(@Body vm_test fi);


}
