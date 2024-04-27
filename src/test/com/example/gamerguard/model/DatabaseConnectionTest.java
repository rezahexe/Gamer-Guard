package com.example.gamerguard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteConnection;

import java.sql.Connection;


import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {

/*    @BeforeEach
    void setUp() {
    }*/

/*    @Test
    void getInstance() {
    }*/
    
    @Test
    public void  testConnection() {
        Connection conn = DatabaseConnection.getInstance();
        assertEquals(true, conn != null);
    }
}