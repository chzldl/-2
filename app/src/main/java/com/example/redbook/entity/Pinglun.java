package com.example.redbook.entity;

import java.io.Serializable;

/**
 * 文件名：Pinglun
 * 作  者： 唐辉
 * 描述：评论
 */
public class Pinglun implements Serializable {
    private int id;
    private String luntan_id;
    private String username;
    private String head_url;
    private String content;
    private String time;
    private String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLuntan_id() {
        return luntan_id;
    }

    public void setLuntan_id(String luntan_id) {
        this.luntan_id = luntan_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
