package com.hesabischool.hesabiapp.Retrofit;


import com.hesabischool.hesabiapp.Clases.app;

public class ApiServies {

 //  private  static String Base_url="";
  static String Base_url= app.baseUrl.retrofit;
//  static String Base_url="";
    public static RguestApi getAPIService() {

        return RetrofitClient.getClient(Base_url).create(RguestApi.class);
    }

        ///.................................................................................................

}
