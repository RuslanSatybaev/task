package com.example.task.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBWorker {
    private final String HOST = "jdbc:mysql://localhost:3306/inside_test?serverTimezone=Europe/Minsk&useSSL=false";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";

    public Connection getConnection() {
        return connection;
    }

    private Connection connection;

    public DBWorker() {
        try {
            connection= DriverManager.getConnection(HOST,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
