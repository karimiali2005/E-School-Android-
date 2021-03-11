package com.hesabischool.hesabiapp.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.vm_ModelServer.GetDataFromServer;
import com.hesabischool.hesabiapp.vm_ModelServer.LoginUserResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftViewModel;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatRightShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatRightViewModel;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatViewModel;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomLiveViewModel;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class dbQuery {
    dbConnector db;

    public dbQuery(dbConnector db) {
        this.db = db;
    }

    public boolean AddOrUpdateUser(LoginUserResult vmu) {
        try {
            String query = "SELECT * FROM User ";
            Cursor c = null;
            c = db.select(query);
            if (c.moveToNext()) {
                //delete row

                boolean status = db.Delete("User", "");
                if (status == false) {
                    return status;
                }
            }
            //insert
            ContentValues values = new ContentValues();
            values.put("userID", vmu.userID);
            values.put("fullName", vmu.fullName);
            values.put("irNational", vmu.irNational);
            values.put("birthDate", String.valueOf(vmu.birthDate));
            values.put("userTypeID", vmu.userTypeID);
            values.put("userTypeTitle", vmu.userTypeTitle);
            values.put("userActive", vmu.userActive);
            values.put("reasonInactive", vmu.reasonInactive);
            values.put("mobileNumber", vmu.mobileNumber);
            values.put("picName", vmu.picName);
            values.put("password", vmu.password);
            values.put("examAddress", vmu.examAddress == null ? "" : vmu.examAddress);
            values.put("token", vmu.token);

            boolean status = db.insert("User", values);
            return status;


        } catch (Exception ex) {

            throw ex;

        }


    }

    public void addOrUpdateData(GetDataFromServer rcv) {
        if (rcv != null ) {
            RoomChatViewModel r=rcv.value;
                if(r.RoomChatLeftViewModel!=null)
                {
                    fun_roomChatLeft(r.RoomChatLeftViewModel);
                }
                if(r.RoomChatRightViewModel!=null)
                {
                    fun_roomChatRight(r.RoomChatRightViewModel);
                }

        }
    }

    private void fun_roomChatRight(RoomChatRightViewModel rcv) {
        if(rcv.RoomChatRightShowResult!=null&&rcv.RoomChatRightShowResult.size()>0)
        {
            for(RoomChatRightShowResult r:rcv.RoomChatRightShowResult)
            {
                SaveTodatabase_RoomChatRight(r);
                app.Info.checkpage.callForCheangeMainChat.refresh();
            }
        }
        if(rcv.RoomLiveShows!=null&&rcv.RoomLiveShows.size()>0)
        {
            for(RoomLiveViewModel r:rcv.RoomLiveShows)
            {
                //todo save room live show
            }
        }
    }


    private void SaveTodatabase_RoomChatRight(RoomChatRightShowResult r) {

try {

    saveTosqlAddOrUpdate(r,"RoomChatGroupID", String.valueOf(r.RoomChatGroupID));

 /*   ContentValues values = new ContentValues();
    values.put("CourseID",r.CourseID);
    values.put("HomeWorkNewNumber",r.HomeWorkNewNumber);
    values.put("MessageNewNumber",r.MessageNewNumber);
    values.put("PicName",r.PicName);
    values.put("RoomChatDate",r.RoomChatDate);
    values.put("RoomChatGroupID",r.RoomChatGroupID);
    values.put("RoomChatGroupType",r.RoomChatGroupType);
    values.put("RoomChatTitle",r.RoomChatTitle);
    values.put("RoomID",r.RoomID);
    values.put("TeacherID",r.TeacherID);
    values.put("TextChat",r.TextChat);
    String query = "SELECT * FROM RoomChatRightShowResult WHERE RoomChatGroupID ="+r.RoomChatGroupID;
    Cursor c = null;
    c = db.select(query);
    if (c.moveToNext()) {
        //Update
        db.update("RoomChatRightShowResult",values,"RoomChatGroupID ="+r.RoomChatGroupID);
    }else
    {
        //Insert
        db.insert("RoomChatRightShowResult",values);
    }
*/


}catch (Exception ex)
{

    try {
        throw ex;
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    }
}



    }



    private void fun_roomChatLeft(RoomChatLeftViewModel rcv) {
        if(rcv.RoomChatLeftPropertyResult!=null)
        {
      //Todo Save in db  rcv.RoomChatLeftPropertyResult
         //   SaveTodatabase_RoomChatRight(rcv.RoomChatLeftPropertyResult);
            try {
                saveTosqlAddOrUpdate(rcv.RoomChatLeftPropertyResult,"RoomChatGroupID",String.valueOf(rcv.RoomChatLeftPropertyResult.RoomChatGroupID));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(rcv.RoomChatLeftShowResult!=null &&rcv.RoomChatLeftShowResult.size()>0)
        {
            for(RoomChatLeftShowResult r:rcv.RoomChatLeftShowResult)
            {
                try {

                  saveTosqlAddOrUpdate(r,"RoomChatID", String.valueOf(r.RoomChatID));

                } catch (IllegalAccessException e) {

                    e.printStackTrace();
                }
            }
        }
    }


    public <T> void saveTosqlAddOrUpdate(T c,String namewhere,String valuewhere) throws IllegalArgumentException, IllegalAccessException {
        try
        {
            //  Class<?> aClass = this.getClass();
            Class<?> aClass = c.getClass();
            String nametabel =c.getClass().getSimpleName();
            Field[] declaredFields = aClass.getDeclaredFields();
            ContentValues values = new ContentValues();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if(field.getType().getSimpleName().equals("String"))
                {
                    values.put(field.getName(),String.valueOf(field.get(c)));
                }else
                {
                 String tf= field.getType().getSimpleName();
                 String tfn= field.getName();
                 if(tf.equals("boolean"))
                 {
                     if(field.get(c)!=null)
                     {
                         boolean vf= (boolean) field.get(c);
                         int v= (vf)?1:0;
                         values.put(field.getName(),v);
                     }else
                     {
                         boolean vf= false;
                         int v= (vf)?1:0;
                         values.put(field.getName(),v);
                     }

                 }else
                 {
                     int v= Integer.parseInt(String.valueOf(field.get(c)));
                     values.put(field.getName(),v);
                 }

                }

            }
            String where=namewhere+" = "+valuewhere;
            String query = "SELECT * FROM "+nametabel+" WHERE "+ where;
            Cursor cu = null;
            cu = db.select(query);
            if (cu.moveToNext()) {
                //Update
                db.update(nametabel,values,where);
            }else
            {
                //Insert
                db.insert(nametabel,values);
            }

        }catch (Exception ex)
        {
            throw ex;
        }

    }
    public <T> void saveTosqlAdd(T c) throws IllegalArgumentException, IllegalAccessException {
        try
        {
            //  Class<?> aClass = this.getClass();
            Class<?> aClass = c.getClass();
            String nametabel =c.getClass().getSimpleName();
            Field[] declaredFields = aClass.getDeclaredFields();
            ContentValues values = new ContentValues();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if(field.getType().getSimpleName().equals("String"))
                {
                    values.put(field.getName(),String.valueOf(field.get(c)));
                }else
                {
                    String tf= field.getType().getSimpleName();
                    String tfn= field.getName();
                    if(tf.equals("boolean"))
                    {
                        if(field.get(c)!=null)
                        {
                            boolean vf= (boolean) field.get(c);
                            int v= (vf)?1:0;
                            values.put(field.getName(),v);
                        }else
                        {
                            boolean vf= false;
                            int v= (vf)?1:0;
                            values.put(field.getName(),v);
                        }

                    }else
                    {
                        int v= Integer.parseInt(String.valueOf(field.get(c)));
                        values.put(field.getName(),v);
                    }

                }

            }
                //Insert
                db.insert(nametabel,values);

        }catch (Exception ex)
        {
            throw ex;
        }

    }
    public <T> void saveTosqlAddByDelete(T c) throws IllegalArgumentException, IllegalAccessException {
        try
        {
            //  Class<?> aClass = this.getClass();
            Class<?> aClass = c.getClass();
            String nametabel =c.getClass().getSimpleName();
            Field[] declaredFields = aClass.getDeclaredFields();
            ContentValues values = new ContentValues();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if(field.getType().getSimpleName().equals("String"))
                {
                    values.put(field.getName(),String.valueOf(field.get(c)));
                }else
                {
                    String tf= field.getType().getSimpleName();
                    String tfn= field.getName();
                    if(tf.equals("boolean"))
                    {
                        if(field.get(c)!=null)
                        {
                            boolean vf= (boolean) field.get(c);
                            int v= (vf)?1:0;
                            values.put(field.getName(),v);
                        }else
                        {
                            boolean vf= false;
                            int v= (vf)?1:0;
                            values.put(field.getName(),v);
                        }

                    }else
                    {
                        int v= Integer.parseInt(String.valueOf(field.get(c)));
                        values.put(field.getName(),v);
                    }

                }

            }
          db.Delete(nametabel,"");
            db.insert(nametabel,values);

        }catch (Exception ex)
        {
            throw ex;
        }

    }
    public <T> void saveTosqlUpdate(T c,String whereArry) throws IllegalArgumentException, IllegalAccessException {
        try
        {
            //  Class<?> aClass = this.getClass();
            Class<?> aClass = c.getClass();
            String nametabel =c.getClass().getSimpleName();
            Field[] declaredFields = aClass.getDeclaredFields();
            ContentValues values = new ContentValues();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if(field.getType().getSimpleName().equals("String"))
                {
                    values.put(field.getName(),String.valueOf(field.get(c)));
                }else
                {
                    String tf= field.getType().getSimpleName();
                    String tfn= field.getName();
                    if(tf.equals("boolean"))
                    {
                        if(field.get(c)!=null)
                        {
                            boolean vf= (boolean) field.get(c);
                            int v= (vf)?1:0;
                            values.put(field.getName(),v);
                        }else
                        {
                            boolean vf= false;
                            int v= (vf)?1:0;
                            values.put(field.getName(),v);
                        }

                    }else
                    {
                        int v= Integer.parseInt(String.valueOf(field.get(c)));
                        values.put(field.getName(),v);
                    }

                }

            }

            db.update(nametabel,values,whereArry);

        }catch (Exception ex)
        {
            throw ex;
        }

    }
    public <T> void saveTosqlDelete(T c,String whereArry) throws IllegalArgumentException, IllegalAccessException {
        try
        {
            //  Class<?> aClass = this.getClass();
            Class<?> aClass = c.getClass();
            String nametabel =c.getClass().getSimpleName();
            /*String query = "SELECT * FROM "+nametabel+ whereArry;
            Cursor cu = null;
            cu = db.select(query);
            if (cu.moveToNext()) {
                //Update
                db.Delete(nametabel,whereArry);
            }*/
            db.Delete(nametabel,whereArry);
        }catch (Exception ex)
        {
            throw ex;
        }

    }


}
