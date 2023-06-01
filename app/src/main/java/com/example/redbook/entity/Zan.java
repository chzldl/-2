package com.example.redbook.entity;

import java.io.Serializable;

/**
 * @authors: 唐辉

 * @description:点赞
 **/
public class Zan implements Serializable {
    private int id;
    private String user_id;
    private String comments;//0 未点赞，1 已点赞
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
