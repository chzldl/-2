package com.example.redbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 文件名：PinglunHelper
 * 作  者： 唐辉
 * 描述：TOOD
 */
public class PinglunHelper extends SQLiteOpenHelper {

    public static final String CREATE_USER = "create table Pinglun ("
            + "id integer primary key autoincrement, "
            + "luntan_id text, "
            + "username text, "
            + "content text, "
            + "time text, "
            + "head_url text)";

    public PinglunHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
