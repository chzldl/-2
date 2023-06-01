package com.example.redbook.entity;

import java.io.Serializable;

public class Dongman implements Serializable {
    private Integer img;

    public Dongman(Integer img, String content) {
        this.img = img;
        this.content = content;
    }

    public Dongman() {
    }

    private String content;

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
