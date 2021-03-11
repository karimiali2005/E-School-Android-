package com.hesabischool.hesabiapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import com.hesabischool.hesabiapp.vm_ModelServer.RoomChatRightShowResult;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dbQuerySelect {
    private dbConnector db;
    Context context;

    public dbQuerySelect(Context context) {
        this.context = context;
        db = new dbConnector(context);
    }

    public <T> T SelesctList(T c) throws ClassNotFoundException, IllegalAccessException {
        try {
            String nametabel = c.getClass().getSimpleName();
            String query = "SELECT * FROM " + nametabel;
            return SelectList(c, query);
        } catch (Exception ex) {
            throw ex;
        }
    }
    public <T> T SelesctListWhere(T c,String whereName,String whereValue) throws ClassNotFoundException, IllegalAccessException {
        try {
            String nametabel = c.getClass().getSimpleName();
            String query = "SELECT * FROM " + nametabel + " WHERE "+whereName+" = "+whereValue;
            return SelectList(c, query);
        } catch (Exception ex) {
            throw ex;
        }
    }
    public <T> T SelesctListArryWhere(T c,String WhereArry) throws ClassNotFoundException, IllegalAccessException {
        try {
            String nametabel = c.getClass().getSimpleName();
            String query = "SELECT * FROM " + nametabel + WhereArry;
            return SelectList(c, query);
        } catch (Exception ex) {
            throw ex;
        }
    }
    public <T> T SelesctListTakeAndOfcet(T c,int take,int ofcet) throws ClassNotFoundException, IllegalAccessException {
        try {
            String nametabel = c.getClass().getSimpleName();
            String query = "SELECT * FROM " + nametabel +" LIMIT "+take +" OFFSET "+ofcet;
            return SelectList(c, query);
        } catch (Exception ex) {
            throw ex;
        }
    }


    public <T> T SelesctListOrderByDesending(T c, String culnmeDes) throws ClassNotFoundException, IllegalAccessException {
        try {
            String nametabel = c.getClass().getSimpleName();
            String query = "SELECT * FROM " + nametabel + " ORDER BY " + culnmeDes + " DESC";
            return SelectList(c, query);
        } catch (Exception ex) {
            throw ex;
        }
    }
    public <T> T SelesctListOrderByDesendingTakeandOfcet(T c, String culnmeDes,int take,int ofcet) throws ClassNotFoundException, IllegalAccessException {
        try {
            String nametabel = c.getClass().getSimpleName();
            String query = "SELECT * FROM " + nametabel + " ORDER BY " + culnmeDes + " DESC"+" LIMIT "+take +" OFFSET "+ofcet;
            return SelectList(c, query);
        } catch (Exception ex) {
            throw ex;
        }
    }


    public <T> T SelesctListOrderByAscnding(T c, String culnmeAsc) throws ClassNotFoundException, IllegalAccessException {
        try {
            String nametabel = c.getClass().getSimpleName();
            String query = "SELECT * FROM " + nametabel + " ORDER BY " + culnmeAsc + " ASC";
            return SelectList(c, query);
        } catch (Exception ex) {
            throw ex;
        }
    }
    public <T> T SelesctListOrderByAscndingTakeAndOfcet(T c, String culnmeAsc,int take,int ofcet) throws ClassNotFoundException, IllegalAccessException {
        try {
            String nametabel = c.getClass().getSimpleName();
            String query = "SELECT * FROM " + nametabel + " ORDER BY " + culnmeAsc + " ASC"+" LIMIT "+take +" OFFSET "+ofcet;
            return SelectList(c, query);
        } catch (Exception ex) {
            throw ex;
        }
    }


    public <T> T SelesctListOrderByDesendingAndWhere(T c, String culnmeDes,String whereName,String whereValue) throws ClassNotFoundException, IllegalAccessException {
        try {
            String nametabel = c.getClass().getSimpleName();
            String query = "SELECT * FROM " + nametabel + " WHERE "+whereName+" = "+whereValue+" ORDER BY " + culnmeDes + " DESC";
            return SelectList(c, query);
        } catch (Exception ex) {
            throw ex;
        }
    }
    public <T> T SelesctListOrderByDesendingAndWhereTakeAndOfcet(T c, String culnmeDes,String whereName,String whereValue,int take,int ofcet) throws ClassNotFoundException, IllegalAccessException {
        try {
            String nametabel = c.getClass().getSimpleName();
            String query = "SELECT * FROM " + nametabel + " WHERE "+whereName+" = "+whereValue+" ORDER BY " + culnmeDes + " DESC"+" LIMIT "+take +" OFFSET "+ofcet;
            return SelectList(c, query);
        } catch (Exception ex) {
            throw ex;
        }
    }
    public <T> T SelesctListOrderByDesendingAndWhereArryTakeAndOfcet(T c, String culnmeDes,String whereArry,int take,int ofcet) throws ClassNotFoundException, IllegalAccessException {
        try {
            String nametabel = c.getClass().getSimpleName();
            String query = "SELECT * FROM " + nametabel + whereArry+" ORDER BY " + culnmeDes + " DESC"+" LIMIT "+take +" OFFSET "+ofcet;
            return SelectList(c, query);
        } catch (Exception ex) {
            throw ex;
        }
    }


    public <T> T SelesctListOrderByAscndingAndWhere(T c, String culnmeAsc,String whereName,String whereValue) throws ClassNotFoundException, IllegalAccessException {
        try {
            String nametabel = c.getClass().getSimpleName();
            String query = "SELECT * FROM " + nametabel + " WHERE "+whereName+" = "+whereValue+" ORDER BY " + culnmeAsc + " ASC";
            return SelectList(c, query);
        } catch (Exception ex) {
            throw ex;
        }
    }
    public <T> T SelesctListOrderByAscndingAndWhereTakeAndOfcet(T c, String culnmeAsc,String whereName,String whereValue,int take,int ofcet) throws ClassNotFoundException, IllegalAccessException {
        try {
            String nametabel = c.getClass().getSimpleName();
            String query = "SELECT * FROM " + nametabel + " WHERE "+whereName+" = "+whereValue+" ORDER BY " + culnmeAsc + " ASC"+" LIMIT "+take +" OFFSET "+ofcet;
            return SelectList(c, query);
        } catch (Exception ex) {
            throw ex;
        }
    }







    private <T> T SelectList(final T c, String query) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
        try {

            List<String> list = getnamefildeAndType(c);

            Cursor cu = null;
            cu = db.select(query);
            //List<String> json = new ArrayList<>();
            String json = "[";
            Gson gson = new Gson();
            int i = 0;
            if (cu.moveToNext()) {
                do {
                    Map<String, Object> map = new HashMap<String, Object>();
                    for (String ss : list) {
                        String[] v = ss.split("#");
                        int j = Integer.parseInt(v[0]);
                        String type = v[2];
                        String fildName = v[1];
                        String str = fildName;
                        //For firstchar leter
                        String cap = str.substring(0, 1).toLowerCase() + str.substring(1);

                        if (type.equals("String")) {
                            map.put(cap, cu.getString(cu.getColumnIndex(fildName)));
                        }else if(type.equals("bool"))
                        {
                            int boolvalue=cu.getInt(cu.getColumnIndex(fildName));
                            boolean bv=(boolvalue==1)?true:false;
                                    map.put(cap,bv);
                        }
                        else {
                            map.put(cap, cu.getInt(cu.getColumnIndex(fildName)));
                        }


                    }
                    String rowvalue = gson.toJson(map);
                    //  json.add(rowvalue);
                    json += rowvalue + ",";
                    i++;
                } while (cu.moveToNext());

            }

            if(json.endsWith(","))
            {
                int ii = json.lastIndexOf(",");

                json = json.substring(0, ii) + json.substring(ii + 1);
            }
            json += "]";

            Gson gson2 = new Gson();
            TypeToken listType = TypeToken.getParameterized(List.class, Class.forName(c.getClass().getName()));
            return gson2.fromJson(json, listType.getType());
            //   return gson2.fromJson(bodyInStringFormat, TypeToken.get(new ArrayList<T>().getClass()).getType());

        } catch (Exception ex) {
            throw ex;
        }

    }

    private <T> List<String> getnamefildeAndType(T c) {
        Class<?> aClass = c.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        List<String> list = new ArrayList<>();
        Integer j = 0;
        for (Field field : declaredFields) {
            try {
                field.setAccessible(true);
                if (field.getType().getSimpleName().equals("String")) {

                    String s = String.valueOf(j) + "#" + field.getName() + "#" + "String";
                    list.add(s);
                } else {
                    String tf= field.getType().getSimpleName();
                    String tfn= field.getName();
                    if(tf.equals("boolean"))
                    {
                        String s = String.valueOf(j) + "#" + field.getName() + "#" + "bool";
                        list.add(s);
                    }else
                    {
                        String s = String.valueOf(j) + "#" + field.getName() + "#" + "int";
                        list.add(s);
                    }



                }
                j++;
            } catch (Exception ex) {

            }

        }
        return list;
    }

}
