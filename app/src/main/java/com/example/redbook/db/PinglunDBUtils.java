package com.example.redbook.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.redbook.entity.Pinglun;

import java.util.ArrayList;
import java.util.List;



/**
 * 文件名：PinglunDBUtils
 * 作  者： 唐辉
 * 描述：TOOD
 */
public class PinglunDBUtils {
    public static final String DB_NAME = "pinglun_dbname";

    public static final int VERSION = 1;

    private static PinglunDBUtils sqliteDB;

    private SQLiteDatabase db;

    private PinglunDBUtils(Context context) {
        PinglunHelper dbHelper = new PinglunHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }


    public synchronized static PinglunDBUtils getInstance(Context context) {
        if (sqliteDB == null) {
            sqliteDB = new PinglunDBUtils(context);
        }
        return sqliteDB;
    }


    public void delete(Context context,String id) {
        PinglunHelper dbHelper = new PinglunHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getReadableDatabase();
        db.delete("Pinglun", "id=?", new String[] { id });
    }

    /**
     * 修改信息
     * @param context
     * @param userBean
     */
    public void change(Context context, Pinglun userBean) {
        PinglunHelper dbHelper = new PinglunHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", userBean.getId());
        values.put("luntan_id", userBean.getLuntan_id());
        values.put("content", userBean.getContent());
        values.put("time", userBean.getTime());
        values.put("username", userBean.getUsername());
        values.put("head_url", userBean.getHead_url());
        db.update("Pinglun", values, "id=?", new String[]{userBean.getId()+""});
    }

    /**
     * 插入数据
     * @param user
     */
    public void insert(Pinglun user) {
        try {
            db.execSQL("insert into Pinglun(luntan_id,username,content,time,head_url) values(?,?,?,?,?) ", new String[]{
                    user.getLuntan_id(),
                    user.getUsername(),
                    user.getContent(),
                    user.getTime(),
                    user.getHead_url(),
            });
        } catch (Exception e) {
            Log.d("����", e.getMessage().toString());
        }
    }
    /**
     * 查询所有数据
     * @return
     */
    @SuppressLint("Range")
    public List<Pinglun> findAllByLuntanId(String luntan_id){
        List<Pinglun> shopAddressList = new ArrayList<>();
        Cursor cursor = db
                .query("Pinglun", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do{
                if (luntan_id.equals(cursor.getString(cursor.getColumnIndex("luntan_id")))){
                    Pinglun shopAddress = new Pinglun();
                    shopAddress.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    shopAddress.setLuntan_id(cursor.getString(cursor.getColumnIndex("luntan_id")));
                    shopAddress.setContent(cursor.getString(cursor.getColumnIndex("content")));
                    shopAddress.setTime(cursor.getString(cursor.getColumnIndex("time")));
                    shopAddress.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                    shopAddress.setHead_url(cursor.getString(cursor.getColumnIndex("head_url")));
                    shopAddressList.add(shopAddress);
                }

            }while (cursor.moveToNext());
        }
        return shopAddressList;
    }

    @SuppressLint("Range")
    public int getToTalByLuntanId(String luntan_id){
        int TOTAL = 0;
        Cursor cursor = db
                .query("Pinglun", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do{
                if (luntan_id.equals(cursor.getString(cursor.getColumnIndex("luntan_id")))){
                    ++TOTAL;
                }

            }while (cursor.moveToNext());
        }
        return TOTAL;
    }
}
