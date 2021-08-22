package com.hesabischool.hesabiapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.google.gson.annotations.SerializedName;
import com.hesabischool.hesabiapp.Clases.app;
import com.hesabischool.hesabiapp.viewmodel.vm_sendoflinechat;
import com.hesabischool.hesabiapp.viewmodel.vm_upload;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftPropertyResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatLeftShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatRightShowResult;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomLiveViewModel;
import com.hesabischool.hesabiapp.vm_ModelServer.UserPicViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.Date;


public class dbConnector extends SQLiteOpenHelper {
	Context context;
	public dbQuery dq;
	public dbConnector(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);

		this.context=context;
		dq=new dbQuery(this);
		create_Tables() ;
	}
	public dbConnector(Context context) {
		super(context, app.Database.dbName, null, 1);

		this.context=context;
		dq=new dbQuery(this);
		create_Tables() ;
	}
	public  void backupDatabase(String databaseName) {
		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			String packageName = context.getApplicationInfo().packageName;

			if (sd.canWrite()) {
				String currentDBPath = String.format("//data//%s//databases//%s",
						packageName, databaseName);
				String backupDBPath = String.format("debug_%s.sqlite", packageName);
				File currentDB = new File(data, currentDBPath);
				File backupDB = new File(sd, backupDBPath);

				if (currentDB.exists()) {
					FileChannel src = new FileInputStream(currentDB).getChannel();
					FileChannel dst = new FileOutputStream(backupDB).getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
				}
			}
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	@Override
	public void onCreate(SQLiteDatabase arg0) {
	 
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	public void create_Tables() {
		String query =
				"CREATE TABLE IF NOT EXISTS User (" +
						"userID       INTEGER   , " +
						"fullName       TEXT   , " +
						"irNational       TEXT   , " +
						"birthDate       TEXT   , " +
						"userTypeID       INTEGER   , " +
						"userTypeTitle       TEXT   , " +
						"userActive       INTEGER   , " +
						"reasonInactive       TEXT   , " +
						"mobileNumber       TEXT   , " +
						"picName       TEXT   , " +
						"password       TEXT   , " +
						"mobile       TEXT   , " +
						"examAddress       TEXT   , " +
						"token       TEXT    " +
						"); ";
		this.exec(query);


//				query =
//				"CREATE TABLE IF NOT EXISTS RoomChatRightShowResult (" +
//						"RoomChatGroupID       INTEGER NOT NULL PRIMARY KEY , " +
//						"RoomChatGroupType       INTEGER   , " +
//						"CourseID       INTEGER   , " +
//						"TeacherID       INTEGER   , " +
//						"RoomID       INTEGER   , " +
//						"RoomChatTitle       TEXT   , " +
//						"userTypeID       INTEGER   , " +
//						"userTypeTitle       TEXT   , " +
//						"RoomChatDate       TEXT   , " +
//						"TextChat       TEXT   , " +
//						"MessageNewNumber       INTEGER   , " +
//						"HomeWorkNewNumber       INTEGER   , " +
//						"PicName       TEXT    " +
//						"); ";
//		this.exec(query);

try{
	this.exec(create_TablesbYquery(new RoomChatRightShowResult()));
	this.exec(create_TablesbYquery(new RoomChatLeftPropertyResult()));
	this.exec(create_TablesbYquery(new RoomChatLeftShowResult()));
	this.exec(create_TablesbYquery(new vm_sendoflinechat()));
	this.exec(create_TablesbYquery(new vm_upload()));
	this.exec(create_TablesbYquery(new UserPicViewModel()));
	this.exec(create_TablesbYquery(new RoomLiveViewModel()));
}catch (Exception ex)
{
	throw ex;
}




//		query =
//				"CREATE TABLE IF NOT EXISTS LastSeenResently (" +
//						"advertId       INTEGER   , " +
//						"categoryId       INTEGER   , " +
//						"cityId       INTEGER   , " +
//						"userId       INTEGER   , " +
//						"groupCode       INTEGER   , " +
//						"seen       INTEGER   , " +
//						"status       INTEGER   , " +
//						"categoryName       TEXT   ," +
//						"cityName       TEXT   ," +
//						"mobile       TEXT   ," +
//						"des       TEXT   ," +
//						"title       TEXT   ," +
//						"adress       TEXT   ," +
//						"kind       TEXT   ," +
//						"pic       TEXT   ," +
//						"createDate       TEXT   ," +
//						"createDateMilady       TEXT   ," +
//						"expireDate       TEXT   ," +
//						"fields       TEXT   ," +
//						"tahators       TEXT   ," +
//						"arzeshTaqribi       TEXT   " +
//						"); ";
//		this.exec(query);




	}
	public void DeleteValueAllTable()
	{
		delete_Tables("User");
		delete_TablesbYquery(new RoomChatRightShowResult());
		delete_TablesbYquery(new RoomLiveViewModel());
		delete_TablesbYquery(new RoomChatLeftPropertyResult());
		delete_TablesbYquery(new RoomChatLeftShowResult());
		delete_TablesbYquery(new vm_sendoflinechat());
		delete_TablesbYquery(new vm_upload());
		delete_TablesbYquery(new UserPicViewModel());
	}
	private <T> String create_TablesbYquery(T c)
	{

		Class<?> aClass = c.getClass();
		String nametabel =c.getClass().getSimpleName();
		String qery="CREATE TABLE IF NOT EXISTS ";
		qery+=nametabel+" (";
		Field[] declaredFields = aClass.getDeclaredFields();
		for (Field field : declaredFields) {
			if(field.getType().getSimpleName().equals("String"))
			{
				//values.put(field.getName(),String.valueOf(field.get(c)));
				qery+=field.getName()+"      TEXT   ,";
			}else
			{
				qery+=field.getName()+"      INTEGER   ,";
			}
		}

		if(qery.endsWith(","))
		{
			int ii = qery.lastIndexOf(",");

			qery = qery.substring(0, ii) + qery.substring(ii + 1);
		}
	qery+=");";
		return qery;
	}
	private <T> String delete_TablesbYquery(T c)
	{

		String nametabel =c.getClass().getSimpleName();
		String query ="delete from "+nametabel;
		this.exec(query);
		return null;
	}
	public void  drop_Tables(String table)	{
		//Drop Table
		String query ="Drop Table IF  EXISTS "+table;
		this.exec(query);
	}
	public void delete_Tables(String table){
		//Delete Table
		String query ="delete from "+table;
		this.exec(query);
	}
	public void exec(String query) {

		try {

			this.getWritableDatabase().execSQL(query);
		} catch (Exception e) {

throw e;
		}

	}
	public Boolean insert(String table , ContentValues values) {


		try {
			this.getWritableDatabase().insert(table, null , values);
			return true;
		} catch (Exception e) {
			 return false;
		}
		 
	}
	public Boolean update(String table , ContentValues values, String strFilter) {


		try {
			this.getWritableDatabase().update(table,values,strFilter,null);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	public Boolean Delete(String table, String strFilter) {


		try {
			this.getWritableDatabase().delete(table,strFilter,null);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	public Cursor select (String query) {
		
		Cursor c = null;
		try {
			c =  this.getWritableDatabase().rawQuery(query, null);
		}catch (SQLiteException e)
		{

		}

		
		
		return c;
	}
	public Cursor select (String query, String param1, String param2) {

		Cursor c = null;
		c =  this.getWritableDatabase().rawQuery(query, new String[]{param1, param2});


		return c;
	}
	public boolean DropDatabase(){
		try {
			this.context.deleteDatabase(app.Database.dbName);
			return true;
		}catch (Exception ex)
		{
			return false;
		}

	}

}
