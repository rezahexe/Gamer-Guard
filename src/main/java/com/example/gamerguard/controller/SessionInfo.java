package com.example.gamerguard.controller;

public class SessionInfo {
    private static int userId;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int id) {
        userId = id;
    }
}

