package com.example.redbook.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.redbook.entity.Zan;


/**
 * @authors: 唐辉
 * @description:数据库操作类
 **/
public class DBUtils {
    public static final String DB_NAME = "db_dbname";
    public static final int VERSION = 1;
    private static DBUtils sqliteDB;
    private SQLiteDatabase db;
    private DBUtils(Context context) {
        ZanHelper dbHelper = new ZanHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }
    /**
     * 单例模式
     * @param context
     */
    public synchronized static DBUtils getInstance(Context context) {
        if (sqliteDB == null) {
            sqliteDB = new DBUtils(context);
        }
        return sqliteDB;
    }


    public void update(Context context, Zan comment) {
        ZanHelper dbHelper = new ZanHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", comment.getId());
        values.put("user_id", comment.getUser_id());
        values.put("comments", comment.getComments());
        values.put("comments_id", comment.getComments_id());
        db.update("Zan", values, "id=?", new String[]{comment.getId()+""});
    }

    public int insertZan(Zan zan) {
        try {
            db.execSQL("insert into Zan(user_id,comments,comments_id) values(?,?,?) ", new String[]{
                    zan.getUser_id(),
                    zan.getComments(),
                    zan.getComments_id(),
            });
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    @SuppressLint("Range")
    public boolean isZan(String user_id, String comments_id ){
        boolean commengs = false;
        Cursor cursor = db
                .query("Zan", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if(user_id.equals(cursor.getString(cursor.getColumnIndex("user_id")))
                        && comments_id.equals(cursor.getString(cursor.getColumnIndex("comments_id")))){
                    if ("1".equals( cursor.getString(cursor.getColumnIndex("comments")))){
                        return  true;
                    }
                }
            } while (cursor.moveToNext());
        }

        return commengs;
    }

    @SuppressLint("Range")
    public int hasZan(String user_id, String comments_id ){
        int commengs = -1;
        Cursor cursor = db
                .query("Zan", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if(user_id.equals(cursor.getString(cursor.getColumnIndex("user_id")))
                        && comments_id.equals(cursor.getString(cursor.getColumnIndex("comments_id")))){
                    return Integer.parseInt( cursor.getString(cursor.getColumnIndex("comments")));
                }
            } while (cursor.moveToNext());
        }
        return commengs;
    }
    @SuppressLint("Range")
    public Zan getZan(String user_id, String comments_id ){
        Zan commengs = null;
        Cursor cursor = db
                .query("Zan", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if(user_id.equals(cursor.getString(cursor.getColumnIndex("user_id")))
                        && comments_id.equals(cursor.getString(cursor.getColumnIndex("comments_id")))){
                    commengs = new Zan();
                    commengs.setComments(cursor.getString(cursor.getColumnIndex("comments")));
                    commengs.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
                    commengs.setComments_id(cursor.getString(cursor.getColumnIndex("comments_id")));
                }
            } while (cursor.moveToNext());
        }
        return commengs;
    }
    /**
     * 获取评论数量
     * @return
     */
    @SuppressLint("Range")
    public int getZanTotal(int id){
        int total = 0;
        Cursor cursor = db
                .query("Zan", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if((id+"").equals(cursor.getString(cursor.getColumnIndex("comments_id")))){
                    ++total;
                }

            } while (cursor.moveToNext());
        }
        return total;
    }
}
