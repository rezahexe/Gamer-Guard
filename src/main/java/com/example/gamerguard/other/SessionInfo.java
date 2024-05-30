package com.example.gamerguard.other;


/**
 * The SessionInfo class holds session-related information for a user.
 * This includes the user's ID, username, and email. The class provides
 * static methods to get and set these values.
 *
 * @author Serene Coders
 * @version 1.0.0
 */
public class SessionInfo {
    private static int userId;
    private static String userName;
    private static String userEmail;

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public static int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param id the user ID to set
     */
    public static void setUserId(int id) {
        userId = id;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Sets the username.
     *
     * @param name the username to set
     */
    public static void setUserName(String name) {
        userName = name;
    }

    /**
     * Gets the user email.
     *
     * @return the user email
     */
    public static String getUserEmail() {
        return userEmail;
    }

    /**
     * Sets the user email.
     *
     * @param name the user email to set
     */
    public static void setUserEmail(String name) {
        userEmail = name;
    }

}
