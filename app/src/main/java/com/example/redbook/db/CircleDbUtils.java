package com.example.redbook.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.redbook.entity.CircleEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * @authors: 唐辉
 * @date: 2022/9/13
 * @description:
 **/
public class CircleDbUtils {
    public static final String DB_NAME = "circle_dbname";

    public static final int VERSION = 1;

    private static CircleDbUtils sqliteDB;

    private SQLiteDatabase db;

    private CircleDbUtils(Context context) {
        CircleHelper dbHelper = new CircleHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }
    /**
     * 单例模式
     * @param context
     */
    public synchronized static CircleDbUtils getInstance(Context context) {
        if (sqliteDB == null) {
            sqliteDB = new CircleDbUtils(context);
        }
        return sqliteDB;
    }
    //删除
    public void delete(Context context,String id) {
        CircleHelper dbHelper = new CircleHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getReadableDatabase();
        db.delete("Circle", "id=?", new String[] { id });
    }
    public int insert(CircleEntity circleEntity) {
        try {
            db.execSQL("insert into Circle(user_id,path,circle_id) values(?,?,?) ", new String[]{
                    circleEntity.getUser_id(),
                    circleEntity.getPath(),
                    circleEntity.getCircle_id(),
            });
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
    @SuppressLint("Range")
    public List<String> findDiaryListByCircleId(String circle_id){
        List<String> diaryList = new ArrayList<>();
        Cursor cursor = db
                .query("Circle", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if(circle_id.equals(cursor.getString(cursor.getColumnIndex("circle_id")))){
                    diaryList.add(cursor.getString(cursor.getColumnIndex("path")));
                }
            } while (cursor.moveToNext());
        }
        return diaryList;
    }

}
