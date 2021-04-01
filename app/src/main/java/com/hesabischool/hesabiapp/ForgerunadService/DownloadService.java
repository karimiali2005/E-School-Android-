package com.hesabischool.hesabiapp.ForgerunadService;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hesabischool.hesabiapp.Clases.Download;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.R;
import com.hesabischool.hesabiapp.Retrofit.RetrofitInterface;
import com.hesabischool.hesabiapp.adaptor.Adaptor_detailsChat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class DownloadService extends IntentService {

    public DownloadService() {
        super("Download Service");
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;

    @Override
    protected void onHandleIntent(Intent intent) {

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.bg_chat)
                .setContentTitle("دانلود")
                .setContentText(" درحال دانلود فایل")
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());
        Toast.makeText(this, "شروع دانلود", Toast.LENGTH_SHORT).show();
        initDownload();

    }

 /*   private void initDownload( ){
        String adress=app.Info.Fileadress;
        final String filename=app.Info.Filename;

        app.retrofit.retrofit().downloadFile(adress).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    downloadFile(response.body(),filename);

                } catch (Exception e) {

                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }*/
    private void initDownload(){
        String adress=app.Info.Fileadress;
        final String filename=app.Info.Filename;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(app.Info.Path1)
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ResponseBody> request = retrofitInterface.downloadFile(adress);
        try {

            downloadFile(request.execute().body(),filename);

        } catch (IOException e) {

            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }
    private void downloadFile(final ResponseBody body, final String filename) {

        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    try
                    {
                        int count;
                        byte data[] = new byte[1024 * 4];
                        long fileSize = body.contentLength();
                        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
                        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
                        OutputStream output = new FileOutputStream(outputFile);
                        long total = 0;
                        long startTime = System.currentTimeMillis();
                        int timeCount = 1;
                        // count = bis.read(data);
                        while ((count = bis.read(data)) != -1) {

                            total += count;
                            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
                            double current = Math.round(total / (Math.pow(1024, 2)));

                            int progress = (int) ((total * 100) / fileSize);

                            long currentTime = System.currentTimeMillis() - startTime;

                            Download download = new Download();
                            download.setTotalFileSize(totalFileSize);

                            if (currentTime > 1000 * timeCount) {

                                download.setCurrentFileSize((int) current);
                                download.setProgress(progress);
                                sendNotification(download);
                                timeCount++;
                            }

                            output.write(data, 0, count);
                        }
                        onDownloadComplete();
                        output.flush();
                        output.close();
                        bis.close();


                    }catch (Exception ex)
                    {
                        Toast.makeText(DownloadService.this, " دانلود با خطا روبرو شده است ", Toast.LENGTH_SHORT).show();
                        onDestroy();
                        try {
                            throw ex;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    private void sendNotification(Download download){

        sendIntent(download);
        notificationBuilder.setProgress(100,download.getProgress(),false);
        notificationBuilder.setContentText("در حال دانلود فایل "+ download.getCurrentFileSize() +"/"+totalFileSize +" MB");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendIntent(Download download){

        Intent intent = new Intent(Adaptor_detailsChat.MESSAGE_PROGRESS);
        intent.putExtra("download",download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(){

        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0,0,false);
        notificationBuilder.setContentText("فایل دانلود شد");
        notificationManager.notify(0, notificationBuilder.build());


        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
              notificationManager.cancel(0);
              onDestroy();
            }
        }, 5000);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

}