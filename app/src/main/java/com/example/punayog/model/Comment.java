package com.example.punayog.model;

import com.google.firebase.database.DataSnapshot;

public class Comment {
    private String productId;
    private String content;
    private String uid;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public Comment() {
    }

    public Comment(String content, String uid, String productId) {
        this.content = content;
        this.uid = uid;
        this.productId = productId;
    }
}
