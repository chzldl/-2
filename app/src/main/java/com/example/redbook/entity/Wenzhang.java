package com.example.redbook.entity;

import java.io.Serializable;

public class Wenzhang implements Serializable {
    private String title;
    private Integer path;
    private String content;

    public Wenzhang() {
    }

    public Wenzhang(String title, Integer path, String content) {
        this.title = title;
        this.path = path;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPath() {
        return path;
    }

    public void setPath(Integer path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
