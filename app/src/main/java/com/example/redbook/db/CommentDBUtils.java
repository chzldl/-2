package com.example.redbook.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.redbook.entity.Comment;


/**
 * @authors: 唐辉
 * @date: 2022/9/14
 * @description:
 **/
public class CommentDBUtils {
    public static final String DB_NAME = "comment_dbname";

    public static final int VERSION = 1;

    private static CommentDBUtils sqliteDB;

    private SQLiteDatabase db;

    private CommentDBUtils(Context context) {
        CommentHelper dbHelper = new CommentHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }
    /**
     * 单例模式
     * @param context
     */
    public synchronized static CommentDBUtils getInstance(Context context) {
        if (sqliteDB == null) {
            sqliteDB = new CommentDBUtils(context);
        }
        return sqliteDB;
    }

    public void update(Context context, Comment comment) {
        CommentHelper dbHelper = new CommentHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", comment.getId());
        values.put("user_id", comment.getUser_id());
        values.put("comments", comment.getComments());
        values.put("comments_id", comment.getComments_id());
        db.update("Comment", values, "id=?", new String[]{comment.getId()+""});
    }
    //删除
    public void delete(Context context,String id) {
        CommentHelper dbHelper = new CommentHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getReadableDatabase();
        db.delete("Comment", "id=?", new String[] { id });
    }
    public int insert(Comment comment) {
        try {
            db.execSQL("insert into Comment(user_id,comments,comments_id) values(?,?,?) ", new String[]{
                    comment.getUser_id(),
                    comment.getComments(),
                    comment.getComments_id(),
            });
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 是否评论
     * @param user_id
     * @param comments_id
     * @return
     */
    @SuppressLint("Range")
    public boolean isComment(String user_id, String comments_id ){
        boolean commengs = false;//0 未评论，1 已评论
        Cursor cursor = db
                .query("Comment", null, null, null, null, null, null);
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
    public int hasComment(String user_id, String comments_id ){
        int commengs = -1;//0 未评论，1 已评论
        Cursor cursor = db
                .query("Comment", null, null, null, null, null, null);
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
    public Comment getComment(String user_id, String comments_id ){
        Comment commengs = null;
        Cursor cursor = db
                .query("Comment", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if(user_id.equals(cursor.getString(cursor.getColumnIndex("user_id")))
                        && comments_id.equals(cursor.getString(cursor.getColumnIndex("comments_id")))){
                    commengs = new Comment();
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
    public int getTotal(int id){
        int total = 0;
        Cursor cursor = db
                .query("Comment", null, null, null, null, null, null);
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
