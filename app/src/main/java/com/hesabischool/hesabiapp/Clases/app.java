package com.hesabischool.hesabiapp.Clases;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hesabischool.hesabiapp.R;
import com.hesabischool.hesabiapp.Retrofit.ApiServies;
import com.hesabischool.hesabiapp.Retrofit.RguestApi;
import com.hesabischool.hesabiapp.viewmodel.vm_checkPage;
import com.hesabischool.hesabiapp.viewmodel.vm_getmessage;
import com.hesabischool.hesabiapp.vm_ModelServer.LoginUserResult;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Response;

public class app {
//this is a teast ok
    public static class Info
    {
        public static LoginUserResult User=new LoginUserResult();
        public static vm_checkPage checkpage=new vm_checkPage();


        public static String Path1 ="https://hesabidownload.ir/hesabischoolfiles/";
        public static String LearnFile ="Learn/";
        public static String NormalFile ="Normal/";

        public static String Filename ="";
        public static String Fileadress ="";
        public static View Fileview =null;
        public static boolean isAllowDowanload =true;
        public static ArrayList<Integer> forwardList =new ArrayList<>();




    }
    public static <T> List<T> copyList(List<T> source) {
        List<T> dest = new ArrayList<T>();
        for (T item : source) { dest.add(item); }
        return dest;
    }
    public static class baseUrl
    {

        public static String signalr ="http://192.168.80.143:45457/chatGroupHub";
    //    public static String signalr ="https://chat.hesabischool.com/chatGroupHub";
       //public static String retrofit ="http://192.168.234.143:4277/";

     //  public static String picurl ="api1/Accounts/GetUserPic";
       public static String retrofit ="http://192.168.80.143:45455/";
      // public static String retrofit ="https://api.hesabischool.com/";
      public static String picUrl ="http://192.168.80.143:45459/";
     //  public static String picUrl ="https://pic.hesabischool.com/";

        //===========================Karimy================
     //   public static String signalr ="http://192.168.1.3:45455/chatGroupHub";
      //  public static String retrofit ="http://192.168.1.3:45459/";
    }
    public static class retrofit
    {
        public static RguestApi retrofit() {
            RguestApi rguestApi=null;

            rguestApi = ApiServies.getAPIService();
            return  rguestApi;
        }
        public static void erorRetrofit(Response response, Context context)  {
            try
            {
                app.progress.tryDismiss();
                if(!response.isSuccessful())
                {
                    if(!app.check.EpmtyOrNull(response.errorBody().toString()))
                    {

                        Gson gson = new Gson();
                        Type listType = new TypeToken<vm_getmessage>(){}.getType();
                        vm_getmessage user1 = gson.fromJson(response.errorBody().string(), listType);
                        final String message=user1.message;
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "مشکلی پشی آمده است", Toast.LENGTH_SHORT).show();

                    }
                }

            }catch (Exception ex)
            {

            }


        }
        public static void erorRetrofitForService(Response response, Context context)  {
            try
            {

                if(!response.isSuccessful())
                {
                    if(!app.check.EpmtyOrNull(response.errorBody().toString()))
                    {

                        Gson gson = new Gson();
                        Type listType = new TypeToken<vm_getmessage>(){}.getType();
                        vm_getmessage user1 = gson.fromJson(response.errorBody().string(), listType);
                        final String message=user1.message;
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "مشکلی پشی آمده است", Toast.LENGTH_SHORT).show();

                    }
                }

            }catch (Exception ex)
            {

            }


        }
        public static void FailRetrofit(Throwable t,Context context)
        {
            app.progress.tryDismiss();
                Toast.makeText(context, "   خطا در برقرای ارتباط با سرور", Toast.LENGTH_SHORT).show();
        }
        public static void FailRetrofitForService(Throwable t,Context context)
        {

            Toast.makeText(context, "   خطا در برقرای ارتباط با سرور", Toast.LENGTH_SHORT).show();
        }


    }
    public static class store
    {
        public static void SaveInt(String key, int value, Context mContext2){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext2.getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            editor.commit();
        }
        public static int LoadInt(String key, Context mContext2){
            SharedPreferences  sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext2.getApplicationContext());
            return sharedPreferences.getInt(key, -1);
        }
        public static void SaveString(String key,String value, Context mContext2){
            SharedPreferences  sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext2.getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }
        public static String LoadString(String key, Context mContext2){
            SharedPreferences  sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext2.getApplicationContext());
            return sharedPreferences.getString(key, "empyty");
        }

    }
    public static class net
    {
        static public boolean isNetworkConnected(Context mContext) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    mContext.getSystemService(Context.CONNECTIVITY_SERVICE); // 1
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); // 2
            return networkInfo != null && networkInfo.isConnected(); // 3
        }

        public static boolean CheckCommunication(Context mContext)
        {
            if (net.isNetworkConnected(mContext)) {
                return true;
            }
            else
            {

                new AlertDialog.Builder(mContext)
                        .setTitle(mContext.getString(R.string.NoInternetConnectionTitle))//"No Internet Connection")
                        //.setMessage("It looks like your internet connection is off. Please turn it " +
                        //"on and try again")
                        .setMessage(mContext.getString(R.string.NoInternetConnectionBody))
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
            return false;
        }

        public static class  Dialog
        {
            private static ProgressDialog mProgressDialog;

            public static android.app.Dialog onCreateDialog(Context mContext) {
                mProgressDialog = new ProgressDialog(mContext);
                mProgressDialog.setMessage(mContext.getString(R.string.wait));
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                return mProgressDialog;

            }
            public static void tryDismiss() {

                try {
//                    mProgressDialog.dismiss();
                } catch(IllegalArgumentException ex) {

                }
            }


        }
    }
    //نمایش دیالوگ
    public static class Dialog_
    {
        //ایجاد دیالوگ
        public static View dialog_creat(final Context context, int R_id) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            final View promptView = layoutInflater.inflate(R_id, null);


            return promptView;
        }
        //نمایش دیالوگ
        public static AlertDialog show_dialog(final Context context, final View _view)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setView(_view);
            final AlertDialog alert = alertDialogBuilder.create();
            alert.setCancelable(false);
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alert.show();

            try{
             //   ImageView img_close=_view.findViewById(R.id.img_close);
                ImageView img_close = null;
                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dimos_dialog(alert);
                    }
                });
            }catch (Exception ex)
            {

            }
         /*       ImageView img_close=_view.findViewById(R.id.img_close);
                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dimos_dialog(alert);
                    }
                });*/


            return alert;
        }
        public static AlertDialog show_dialog(final Context context, final View _view,boolean cancel)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setView(_view);
            final AlertDialog alert = alertDialogBuilder.create();
            alert.setCancelable(cancel);
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alert.show();

            try{
                //   ImageView img_close=_view.findViewById(R.id.img_close);
                ImageView img_close = null;
                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dimos_dialog(alert);
                    }
                });
            }catch (Exception ex)
            {

            }
         /*       ImageView img_close=_view.findViewById(R.id.img_close);
                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dimos_dialog(alert);
                    }
                });*/


            return alert;
        }
        public static void dimos_dialog(AlertDialog alert)
        {

            alert.dismiss();
        }

    }
    public static class Database
    {

        public static final String dbName = "Hesabi";
    }
    public static class Convert
    {

        public static String priceMode(String fi)
        {
            try {
                DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                String formattedValue = decimalFormat.format(Double.parseDouble(fi));
                return formattedValue;
            }catch (Exception ex)
            {
                return fi;
            }

        }

        static public Date CovertStringToDate(String dateMiladi)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            String dateInString = dateMiladi;

            try {

                Date date = formatter.parse(dateInString);
                return date;

            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        static public String CovertToPersianDate(Date date)
        {
            if(null == date) {
                return "";
            }

            PersianCalendar persianCalendar = new PersianCalendar();
            persianCalendar.setTime(date);

            String dateTime="";

            dateTime+=persianCalendar.get(Calendar.YEAR)+"/";
            if((persianCalendar.get(Calendar.MONTH) + 1)<10)
            {
                dateTime+="0"+(persianCalendar.get(Calendar.MONTH) + 1)+"/";
            }
            else
            {
                dateTime+=(persianCalendar.get(Calendar.MONTH) + 1)+"/";
            }

            if(persianCalendar.get(Calendar.DAY_OF_MONTH)<10)
            {
                dateTime+="0"+persianCalendar.get(Calendar.DAY_OF_MONTH)+" ";
            }
            else
            {
                dateTime+=persianCalendar.get(Calendar.DAY_OF_MONTH)+" ";
            }





            return dateTime;
        }
        static public String CovertToPersianDate(String date)
        {
            Date d=CovertStringToDate(date);
            String dp=CovertToPersianDate(d);
            return  dp;
        }
        public static String ConvertFAToEN(String faNumbers) {
            String[][] mChars = new String[][]{
                    {"0", "۰"},
                    {"1", "۱"},
                    {"2", "۲"},
                    {"3", "۳"},
                    {"4", "۴"},
                    {"5", "۵"},
                    {"6", "۶"},
                    {"7", "۷"},
                    {"8", "۸"},
                    {"9", "۹"}
            };

            for (String[] num : mChars) {

                faNumbers = faNumbers.replace( num[1],num[0]);
            }

            return faNumbers;
        }


    }
    public static class check
    {
        public static boolean Mobile(String mobile)
        {
            String pattern = "^09[0-9]{9}";
            //برای دریافت فارسی
//String pattern="[ا-ی آ]*";
            Boolean result = Pattern.matches(pattern,mobile);
            return result;
        }

        public static boolean EpmtyOrNull(String str)
        {
            if(str==null||str.trim().equals(""))
            {
                return true;
            }
            return false;
        }


    }
   /* public static class  progress
    {
        private static AlertDialog alert;

        public static void onCreateDialog(Context mContext) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            final View promptView = layoutInflater.inflate(R.layout.item_progressbar, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setView(promptView);
            alert = alertDialogBuilder.create();
            alert.setCancelable(false);
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alert.show();

        }
        public static void tryDismiss() {

            try {
                alert.dismiss();
            } catch(IllegalArgumentException ex) {

            }
        }


    }*/
    public static class  progress
    {
        private static ProgressDialog mProgressDialog;

        public static android.app.Dialog onCreateDialog(Context mContext) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(mContext.getString(R.string.wait));
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            return mProgressDialog;

        }
        public static void tryDismiss() {

            try {
                mProgressDialog.dismiss();
            } catch(IllegalArgumentException ex) {

            }
        }


    }
    public static class  linProgress
    {

        public static void showProgress(Context context)
        {
            LinearLayout linprogress=((Activity)context).findViewById(R.id.lin_progress);
            linprogress.setVisibility(View.VISIBLE);

        }
        public static void showProgress(Context context,String text)
        {
            LinearLayout linprogress=((Activity)context).findViewById(R.id.lin_progress);
            TextView txtProgress=((Activity)context).findViewById(R.id.txt_progress);
            txtProgress.setText(text);
            linprogress.setVisibility(View.VISIBLE);

        }
        public static void hideProgress(Context context)
        {
            LinearLayout linprogress=((Activity)context).findViewById(R.id.lin_progress);
            linprogress.setVisibility(View.GONE);

        }




    }




}
