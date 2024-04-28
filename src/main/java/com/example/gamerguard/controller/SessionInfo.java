package com.example.gamerguard.controller;

public class SessionInfo {
    private static int userId;
    private static String userName;

    public static int getUserId() {
        return userId;
    }
    public static void setUserId(int id) {
        userId = id;
    }

    public static String getUserName() {
        return userName;
    }
    public static void setUserName(String name) {
        userName = name;
    }

}

