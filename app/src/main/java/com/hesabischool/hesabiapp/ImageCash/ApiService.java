package com.hesabischool.hesabiapp.ImageCash;

import com.hesabischool.hesabiapp.vm_ModelServer.UserPicViewModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Administrator on 12/7/2017.
 */

public interface ApiService {
    @Multipart
    @POST("/api1/upload")
    Call<ServerResponseUpload> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @GET()
    Call<String> getStringResponse(@Url String url);
    @GET("GetUserPicName")
    Call<UserPicViewModel> GetUserPicName(@Query("userId") int userId);
}