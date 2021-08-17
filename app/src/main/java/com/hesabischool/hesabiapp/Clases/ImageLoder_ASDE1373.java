package com.hesabischool.hesabiapp.Clases;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import com.hesabischool.hesabiapp.Image.CacheStore;
import com.hesabischool.hesabiapp.Image.MemoryCache;
import com.hesabischool.hesabiapp.database.dbConnector;
import com.hesabischool.hesabiapp.database.dbQuerySelect;
import com.hesabischool.hesabiapp.vm_ModelServer.UserPicViewModel;

import java.io.ByteArrayOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageLoder_ASDE1373 {
    Context context;
    dbConnector db;
    dbQuerySelect dbqs;
    MemoryCache memoryCache;
    public ImageLoder_ASDE1373(Context context) {
        this.context = context;
        db=new dbConnector(context);
        dbqs=new dbQuerySelect(context);
        memoryCache=new MemoryCache();
    }

    public void LoadImage(CircleImageView circleImageView)
    {
runFist(circleImageView);
        if(app.net.isNetworkConnected(context))
        {
            //todo  get pic name
            app.retrofit.retrofit().GetUserPicName(app.Info.User.userID).enqueue(new Callback<UserPicViewModel>() {
                @Override
                public void onResponse(Call<UserPicViewModel> call, Response<UserPicViewModel> response) {

                    if(response.isSuccessful())
                    {
                        UserPicViewModel userPicViewModel=response.body();
                        UserPicViewModel userPicViewModel2=getUSerById(userPicViewModel.UserId);
                        if(userPicViewModel2.UserPicName!=null&&userPicViewModel.UserPicName.equals(userPicViewModel2.UserPicName))
                        {
                            showImage(userPicViewModel.UserPicName,circleImageView);
                        }else
                        {

                            try {
                                // save new image name
                                db.dq.saveTosqlAddOrUpdate(userPicViewModel,"UserId",String.valueOf(userPicViewModel.UserId));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            //todo Dowanload image
                           app.retrofit.retrofit().GetUserPic(userPicViewModel.UserPicName).enqueue(new Callback<String>() {
                               @Override
                               public void onResponse(Call<String> call, Response<String> response) {
                                   if(response.isSuccessful())
                                   {
                                       savetoMydevice(response.body(),userPicViewModel.UserPicName);
                                   }
                               }

                               @Override
                               public void onFailure(Call<String> call, Throwable t) {

                               }
                           });

                        }



                    }
                }

                @Override
                public void onFailure(Call<UserPicViewModel> call, Throwable t) {

                }
            });






        }

    }

    private void runFist(CircleImageView circleImageView) {
        UserPicViewModel userPicViewModel=getUSerById(app.Info.User.userID);
        if(userPicViewModel!=null)
        {
        showImage(userPicViewModel.UserPicName,circleImageView);

        }
    }

    private void showImage(String userPicName,CircleImageView circleImageView) {
        if(userPicName==null)
        {
            userPicName="nopic";
        }
        Bitmap b=memoryCache.get(userPicName);
        circleImageView.setImageBitmap(b);
    }

    private void savetoMydevice(String body,String name) {
     if(name==null)
     {
         name="nopic";
     }
        byte[] decodedString = Base64.decode(body, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
      //  memoryCache.put(name,decodedByte);
        CacheStore c=CacheStore.getInstance();
        c.saveCacheFile(getImageUri(context,decodedByte).toString(),decodedByte);
    }

    private UserPicViewModel getUSerById(int id)
    {
        try {
           List<UserPicViewModel> userPicViewModels = (List<UserPicViewModel>) dbqs.SelesctListWhere(new UserPicViewModel(),"UserId",String.valueOf(id));
           if(userPicViewModels==null||userPicViewModels.size()==0)
           {
               return new UserPicViewModel();
           }
        return  userPicViewModels.get(0);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new UserPicViewModel();
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
