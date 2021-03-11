package com.hesabischool.hesabiapp.Retrofit;


import com.hesabischool.hesabiapp.Clases.app;

public class ApiServies {

 //  private  static String Base_url="http://gamenet.dgsait.ir/";
  static String Base_url= app.baseUrl.retrofit;
//  static String Base_url="http://192.168.1.2:45456/";
    public static RguestApi getAPIService() {

        return RetrofitClient.getClient(Base_url).create(RguestApi.class);
    }

        ///.................................................................................................

}
