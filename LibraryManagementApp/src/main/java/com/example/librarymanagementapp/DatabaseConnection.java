package com.example.librarymanagementapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/library";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Wahab0572";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DATABASE_URL,USERNAME,PASSWORD);
    }
}





