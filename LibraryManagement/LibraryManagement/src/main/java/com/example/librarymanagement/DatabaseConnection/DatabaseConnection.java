package com.example.librarymanagement.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/LibraryManagement";
    private final static String USER_NAME = "root";
    private final static String PASSWORD = "Talha@786";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
    }
}
