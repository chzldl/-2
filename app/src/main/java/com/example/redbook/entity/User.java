package com.example.redbook.entity;


import java.io.Serializable;


/**
 * @authors: 唐辉
 * @date:
 * @description:
 **/
public class User implements Serializable {
    private int id;//主键
    private String user_id;//用户ID
    private String name;//姓名
    private String password;//密码
    private String head_url;//头像

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }
}
