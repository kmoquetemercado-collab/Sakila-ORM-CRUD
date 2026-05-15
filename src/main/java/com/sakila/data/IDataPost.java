/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Standard CRUD interface for database operations
 */

package com.sakila.data;

import java.util.ArrayList;

/**
 * Interface that defines the standard CRUD operations for all data entities.
 * This interface ensures consistency across all data access operations.
 * 
 * <p>Operations defined:</p>
 * <ul>
 *   <li>POST: Create new records</li>
 *   <li>GET: Retrieve records with various overloads</li>
 *   <li>PUT: Update existing records</li>
 *   <li>DELETE: Mark records as inactive</li>
 * </ul>
 * 
 * @param <T> The type of entity being managed
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public interface IDataPost<T> {
    
    /**
     * Creates a new record in the database.
     * 
     * @param entity The entity object to be persisted
     * @return true if the operation was successful, false otherwise
     */
    boolean post(T entity);
    
    /**
     * Retrieves all records from the database.
     * 
     * @return ArrayList containing all entities of type T
     */
    ArrayList<T> getAll();
    
    /**
     * Retrieves a record by its primary key.
     * 
     * @param id The primary key identifier
     * @return The entity if found, null otherwise
     */
    T getById(int id);
    
    /**
     * Searches for records by a specific field and value.
     * 
     * @param fieldName The name of the field to search
     * @param value The value to search for
     * @return ArrayList of entities matching the criteria
     */
    ArrayList<T> getByField(String fieldName, Object value);
    
    /**
     * Updates an existing record in the database.
     * 
     * @param entity The entity with updated values
     * @return true if the operation was successful, false otherwise
     */
    boolean put(T entity);
    
    /**
     * Marks a record as inactive (soft delete).
     * Does not physically remove the record from the database.
     * 
     * @param id The primary key identifier of the record to delete
     * @return true if the operation was successful, false otherwise
     */
    boolean delete(int id);
    
    /**
     * Counts the total number of active records.
     * 
     * @return The count of active records
     */
    int count();
}
