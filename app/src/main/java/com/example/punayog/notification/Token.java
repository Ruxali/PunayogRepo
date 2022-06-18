package com.example.punayog.notification;

import com.google.firebase.messaging.FirebaseMessaging;

public class Token {
    private String token;

    public Token(String token) {
        this.token = token;
    }

    public Token(FirebaseMessaging refreshToken) {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
