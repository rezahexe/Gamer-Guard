package com.example.gamerguard.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    public static Connection instance = null;

    private DatabaseConnection() {

        String url = "jdbc:sqlite:database.db"; // Database driver

        try {
            instance = DriverManager.getConnection(url);
        }catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    public static Connection getInstance() {
        if (instance == null) {
            new DatabaseConnection();
        }
        return instance;
    }
}