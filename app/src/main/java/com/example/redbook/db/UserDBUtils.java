package com.example.redbook.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.redbook.entity.User;

import java.util.ArrayList;
import java.util.List;


/**
 * @description:
 **/
public class UserDBUtils {
    public static final String DB_NAME = "user_dbname";

    public static final int VERSION = 1;

    private static UserDBUtils sqliteDB;

    private SQLiteDatabase db;

    private UserDBUtils(Context context) {
        UserHelper dbHelper = new UserHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }
    /**
     * 单例模式
     * @param context
     */
    public synchronized static UserDBUtils getInstance(Context context) {
        if (sqliteDB == null) {
            sqliteDB = new UserDBUtils(context);
        }
        return sqliteDB;
    }
    //删除用户
    public void delete(Context context,String id) {
        UserHelper dbHelper = new UserHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getReadableDatabase();
        db.delete("User", "id=?", new String[] { id });
    }

    /**
     * 更新用户信息
     * @param context
     * @param userBean
     */
    public void update(Context context, User userBean) {
        UserHelper dbHelper = new UserHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", userBean.getId());
        values.put("user_id", userBean.getUser_id());
        values.put("name", userBean.getName());
        values.put("password", userBean.getPassword());
        values.put("head_url", userBean.getHead_url());
        db.update("User", values, "id=?", new String[]{userBean.getId()+""});
    }

    public int insert(User user) {
        if (user != null) {

            Cursor cursor = db.rawQuery("select * from User where name=?", new String[]{user.getName().toString()});
            if (cursor.getCount() > 0) {
                return -1;
            } else {
                try {
                    db.execSQL("insert into User(user_id,name,password,head_url) values(?,?,?,?) ", new String[]{
                            user.getUser_id(),
                            user.getName(),
                            user.getPassword(),
                            user.getHead_url(),
                    });
                } catch (Exception e) {
                }
                return 1;
            }
        }
        else {
            return 0;
        }
    }

    @SuppressLint("Range")
    public User select(String user_id){
        User user = null;
        Cursor cursor = db
                .query("User", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if(user_id.equals(cursor.getString(cursor.getColumnIndex("user_id")))){
                    user = new User();
                    user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    user.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
                    user.setName(cursor.getString(cursor.getColumnIndex("name")));
                    user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                    user.setHead_url(cursor.getString(cursor.getColumnIndex("head_url")));
                    return user;
                }
            } while (cursor.moveToNext());
        }
        return user;
    }
    @SuppressLint("Range")
    public User selectByName(String name){
        User user = null;
        Cursor cursor = db
                .query("User", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if(name.equals(cursor.getString(cursor.getColumnIndex("name")))){
                    user = new User();
                    user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    user.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
                    user.setName(cursor.getString(cursor.getColumnIndex("name")));
                    user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                    user.setHead_url(cursor.getString(cursor.getColumnIndex("head_url")));
                    return user;
                }
            } while (cursor.moveToNext());
        }
        return user;
    }

    @SuppressLint("Range")
    public List<User> getUserList(){
        List<User> userList = new ArrayList<>();
        Cursor cursor = db
                .query("User", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                   User user = new User();
                    user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    user.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
                    user.setName(cursor.getString(cursor.getColumnIndex("name")));
                    user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                    user.setHead_url(cursor.getString(cursor.getColumnIndex("head_url")));
                    userList.add(user);
            } while (cursor.moveToNext());
        }
        return userList;
    }
    public int Quer(String pwd,String name)
    {

        Cursor cursor =db.rawQuery("select * from User where name=?", new String[]{name});
        if (cursor.getCount()>0)
        {
            Cursor pwdcursor =db.rawQuery("select * from User where name=? and password=?",new String[]{name,pwd});
            if (pwdcursor.getCount()>0)
            {
                return 1;
            }
            else {
                return -1;
            }
        }
        else {
            return 0;
        }
    }


    /**
     * 获取头像
     * @param user_id
     * @return
     */
    @SuppressLint("Range")
    public String getHeadUrlBy(String user_id){
        String user = null;
        Cursor cursor = db
                .query("User", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if(user_id.equals(cursor.getString(cursor.getColumnIndex("user_id")))){
                    return cursor.getString(cursor.getColumnIndex("head_url"));
                }
            } while (cursor.moveToNext());
        }
        return user;
    }
    @SuppressLint("Range")
    public String getNameByUserId(String user_id){
        String user = null;
        Cursor cursor = db
                .query("User", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if(user_id.equals(cursor.getString(cursor.getColumnIndex("user_id")))){
                    return cursor.getString(cursor.getColumnIndex("name"));
                }
            } while (cursor.moveToNext());
        }
        return user;
    }
    @SuppressLint("Range")
    public String getStudentIdByUserId(String user_id){
        String user = null;
        Cursor cursor = db
                .query("User", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if(user_id.equals(cursor.getString(cursor.getColumnIndex("user_id")))){
                    return cursor.getString(cursor.getColumnIndex("student_id"));
                }
            } while (cursor.moveToNext());
        }
        return user;
    }
}
