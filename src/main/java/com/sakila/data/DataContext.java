/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Abstract base class for all data access operations
 */

package com.sakila.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Abstract hybrid parent class that implements common CRUD operations.
 * This class contains concrete methods that child classes cannot override,
 * ensuring consistency across all data access layers.
 * 
 * <p>Key Features:</p>
 * <ul>
 *   <li>Manages database connection lifecycle</li>
 *   <li>Provides final implementations of PUT, GET, POST, DELETE</li>
 *   <li>Respects primary keys (autoincrement) and foreign keys (composition)</li>
 *   <li>Enforces database rules and constraints</li>
 * </ul>
 * 
 * @param <T> The type of entity being managed
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public abstract class DataContext<T> implements IDataPost<T> {
    
    protected Connection connection;
    protected String tableName;
    protected Class<T> entityClass;
    
    /**
     * Constructor that initializes the database connection.
     * 
     * @param tableName The name of the database table
     * @param entityClass The class type of the entity
     * @throws SQLException if database connection fails
     */
    public DataContext(String tableName, Class<T> entityClass) throws SQLException {
        this.tableName = tableName;
        this.entityClass = entityClass;
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Final method for creating a new record.
     * Cannot be overridden by child classes.
     * 
     * @param entity The entity to create
     * @return true if successful, false otherwise
     */
    @Override
    public final boolean post(T entity) {
        try {
            if (entity == null) {
                System.err.println("Error: Cannot insert null entity.");
                return false;
            }
            return insertRecord(entity);
        } catch (SQLException e) {
            System.err.println("Error inserting record: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Final method for retrieving all records.
     * Cannot be overridden by child classes.
     * 
     * @return ArrayList of all entities
     */
    @Override
    public final ArrayList<T> getAll() {
        try {
            return retrieveAllRecords();
        } catch (SQLException e) {
            System.err.println("Error retrieving records: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Final method for retrieving a record by primary key.
     * Cannot be overridden by child classes.
     * 
     * @param id The primary key value
     * @return The entity if found, null otherwise
     */
    @Override
    public final T getById(int id) {
        try {
            return retrieveById(id);
        } catch (SQLException e) {
            System.err.println("Error retrieving record by ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Final method for searching records by field value.
     * Cannot be overridden by child classes.
     * 
     * @param fieldName The field to search
     * @param value The value to match
     * @return ArrayList of matching entities
     */
    @Override
    public final ArrayList<T> getByField(String fieldName, Object value) {
        try {
            return retrieveByField(fieldName, value);
        } catch (SQLException e) {
            System.err.println("Error retrieving records by field: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Final method for updating an existing record.
     * Cannot be overridden by child classes.
     * Respects primary keys and enforces database constraints.
     * 
     * @param entity The entity with updated values
     * @return true if successful, false otherwise
     */
    @Override
    public final boolean put(T entity) {
        try {
            if (entity == null) {
                System.err.println("Error: Cannot update null entity.");
                return false;
            }
            return updateRecord(entity);
        } catch (SQLException e) {
            System.err.println("Error updating record: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Final method for soft-deleting a record (marking as inactive).
     * Cannot be overridden by child classes.
     * Does not physically remove the record to maintain referential integrity.
     * 
     * @param id The primary key of the record to delete
     * @return true if successful, false otherwise
     */
    @Override
    public final boolean delete(int id) {
        try {
            if (id <= 0) {
                System.err.println("Error: Invalid ID for deletion.");
                return false;
            }
            return softDeleteRecord(id);
        } catch (SQLException e) {
            System.err.println("Error deleting record: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Final method that counts active records.
     * Cannot be overridden by child classes.
     * 
     * @return The count of active records
     */
    @Override
    public final int count() {
        try {
            return countActiveRecords();
        } catch (SQLException e) {
            System.err.println("Error counting records: " + e.getMessage());
            return 0;
        }
    }
    
    // ==================== ABSTRACT METHODS FOR CHILD CLASSES ====================
    
    /**
     * Abstract method to implement record insertion logic.
     * Must be implemented by child classes.
     * 
     * @param entity The entity to insert
     * @return true if successful, false otherwise
     * @throws SQLException if database error occurs
     */
    protected abstract boolean insertRecord(T entity) throws SQLException;
    
    /**
     * Abstract method to implement retrieve all logic.
     * Must be implemented by child classes.
     * 
     * @return ArrayList of all entities
     * @throws SQLException if database error occurs
     */
    protected abstract ArrayList<T> retrieveAllRecords() throws SQLException;
    
    /**
     * Abstract method to implement retrieve by ID logic.
     * Must be implemented by child classes.
     * 
     * @param id The primary key value
     * @return The entity if found, null otherwise
     * @throws SQLException if database error occurs
     */
    protected abstract T retrieveById(int id) throws SQLException;
    
    /**
     * Abstract method to implement retrieve by field logic.
     * Must be implemented by child classes.
     * 
     * @param fieldName The field to search
     * @param value The value to match
     * @return ArrayList of matching entities
     * @throws SQLException if database error occurs
     */
    protected abstract ArrayList<T> retrieveByField(String fieldName, Object value) throws SQLException;
    
    /**
     * Abstract method to implement record update logic.
     * Must be implemented by child classes.
     * 
     * @param entity The entity with updated values
     * @return true if successful, false otherwise
     * @throws SQLException if database error occurs
     */
    protected abstract boolean updateRecord(T entity) throws SQLException;
    
    /**
     * Abstract method to implement soft delete logic.
     * Must be implemented by child classes.
     * 
     * @param id The primary key of the record to delete
     * @return true if successful, false otherwise
     * @throws SQLException if database error occurs
     */
    protected abstract boolean softDeleteRecord(int id) throws SQLException;
    
    /**
     * Abstract method to implement count logic.
     * Must be implemented by child classes.
     * 
     * @return The count of active records
     * @throws SQLException if database error occurs
     */
    protected abstract int countActiveRecords() throws SQLException;
    
    /**
     * Closes the database connection.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
