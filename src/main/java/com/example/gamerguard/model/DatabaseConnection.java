package com.example.gamerguard.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class represents a singleton database connection instance using JDBC.
 * It ensures that only one connection to the database is established throughout
 * the application's lifecycle.
 *
 * @author Serene Coders
 * @version 1.0.0
 */
public class DatabaseConnection {
    /** Constant <code>instance</code> */
    public static Connection instance = null;


    /**
     * Private constructor to prevent external instantiation.
     * Initializes the database connection upon construction.
     */
    private DatabaseConnection() {

        String url = "jdbc:sqlite:database.db"; // Database driver

        try {
            instance = DriverManager.getConnection(url);
        }catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }


    /**
     * Retrieves the singleton instance of the database connection.
     * If the instance is null, creates a new instance.
     *
     * @return The singleton instance of the database connection.
     */
    public static Connection getInstance() {
        if (instance == null) {
            new DatabaseConnection();
        }
        return instance;
    }
}
