package com.example.redbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @authors: 唐辉
 * @description:
 **/
public class ZanHelper extends SQLiteOpenHelper {
    //声明用户表
    public static final String CREATE_USER = "create table Zan ("
            + "id integer primary key autoincrement, "
            + "user_id text, "
            + "comments text, "
            + "comments_id text)";

    public ZanHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                         int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户表
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
