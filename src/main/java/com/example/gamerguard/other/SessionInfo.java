package com.example.gamerguard.other;

public class SessionInfo {
    private static int userId;
    private static String userName;
    private static String userEmail;

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

    public static String getUserEmail() {
        return userEmail;
    }
    public static void setUserEmail(String name) {
        userEmail = name;
    }
}
