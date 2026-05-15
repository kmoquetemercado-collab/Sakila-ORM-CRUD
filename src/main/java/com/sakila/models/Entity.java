/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Abstract base entity class for all model entities
 */

package com.sakila.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Abstract base entity class that all domain models inherit from.
 * Provides common properties and methods for all entities in the Sakila database.
 * 
 * <p>Common Properties:</p>
 * <ul>
 *   <li>id: Primary key identifier</li>
 *   <li>active: Status flag (1 = active, 0 = inactive/deleted)</li>
 *   <li>createdAt: Timestamp of entity creation</li>
 *   <li>updatedAt: Timestamp of last modification</li>
 * </ul>
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public abstract class Entity implements Serializable, Cloneable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Primary key identifier (auto-increment).
     */
    protected int id;
    
    /**
     * Active status flag (1 = active, 0 = inactive/soft deleted).
     */
    protected int active;
    
    /**
     * Timestamp when the entity was created.
     */
    protected LocalDateTime createdAt;
    
    /**
     * Timestamp when the entity was last updated.
     */
    protected LocalDateTime updatedAt;
    
    /**
     * Default constructor.
     */
    public Entity() {
        this.active = 1;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Constructor with ID.
     * 
     * @param id The primary key identifier
     */
    public Entity(int id) {
        this.id = id;
        this.active = 1;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // ==================== GETTERS AND SETTERS ====================
    
    /**
     * Gets the primary key identifier.
     * 
     * @return The id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets the primary key identifier.
     * 
     * @param id The id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Gets the active status.
     * 
     * @return 1 if active, 0 if inactive
     */
    public int getActive() {
        return active;
    }
    
    /**
     * Sets the active status.
     * 
     * @param active 1 for active, 0 for inactive
     */
    public void setActive(int active) {
        this.active = (active == 0 || active == 1) ? active : 1;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Gets the creation timestamp.
     * 
     * @return The creation date and time
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    /**
     * Sets the creation timestamp.
     * 
     * @param createdAt The creation date and time
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * Gets the last update timestamp.
     * 
     * @return The last update date and time
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    /**
     * Sets the last update timestamp.
     * 
     * @param updatedAt The last update date and time
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    /**
     * Checks if the entity is active.
     * 
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return active == 1;
    }
    
    /**
     * Marks the entity as active.
     */
    public void activate() {
        this.active = 1;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Marks the entity as inactive (soft delete).
     */
    public void deactivate() {
        this.active = 0;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Abstract method for string representation.
     * Must be implemented by child classes.
     * 
     * @return String representation of the entity
     */
    @Override
    public abstract String toString();
    
    /**
     * Creates a clone of this entity.
     * 
     * @return A cloned instance of the entity
     */
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Clone not supported: " + e.getMessage());
            return null;
        }
    }
}
