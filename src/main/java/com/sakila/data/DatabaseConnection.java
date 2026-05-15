/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: JDBC Database connection manager for MySQL Sakila Database
 */

package com.sakila.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection manager that handles MySQL database connections.
 * Implements Singleton pattern to ensure only one connection pool is created.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class DatabaseConnection {
    
    // Database connection parameters
    private static final String DB_HOST = "localhost";
    private static final int DB_PORT = 3306;
    private static final String DB_NAME = "sakila";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static DatabaseConnection instance;
    private Connection connection;
    
    /**
     * Private constructor to prevent instantiation.
     */
    private DatabaseConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
    }
    
    /**
     * Gets the singleton instance of DatabaseConnection.
     * 
     * @return The singleton instance
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    /**
     * Establishes a connection to the MySQL database.
     * 
     * @return A Connection object to the Sakila database
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + 
                     "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
                System.out.println("✓ Database connection established successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            throw e;
        }
        
        return connection;
    }
    
    /**
     * Closes the database connection.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
    /**
     * Tests the database connection.
     * 
     * @return true if connection is successful, false otherwise
     */
    public boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }
}
