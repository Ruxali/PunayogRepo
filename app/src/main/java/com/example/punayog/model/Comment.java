package com.example.punayog.model;

public class Comment {
    private String content;
    private String uid;

    public Comment(String content, String uid, String uname) {
        this.content = content;
        this.uid = uid;
        this.uname = uname;
    }

    private String uname;
    public Comment() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
