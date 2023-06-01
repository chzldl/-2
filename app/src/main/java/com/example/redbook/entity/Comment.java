package com.example.redbook.entity;

/**
 * @authors: 唐辉
 * @date: 2022/9/13
 * @description:评论数量
 **/
public class Comment {
    private int id;
    private String user_id;
    private String comments;//0 未评论，1 已评论
    private String comments_id;//动态id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments_id() {
        return comments_id;
    }

    public void setComments_id(String comments_id) {
        this.comments_id = comments_id;
    }
}
