/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Store entity model
 */

package com.sakila.models;

/**
 * Store entity representing stores in the Sakila database.
 * Maintains a composition relationship with Address and Manager (Staff).
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class Store extends Entity {
    
    private Address address;       // Foreign Key: Composition
    private int managerId;         // Foreign Key to Staff (Manager)
    
    /**
     * Default constructor.
     */
    public Store() {
        super();
    }
    
    /**
     * Constructor with address and manager ID.
     * 
     * @param address The store address
     * @param managerId The ID of the manager (staff)
     */
    public Store(Address address, int managerId) {
        super();
        this.address = address;
        this.managerId = managerId;
    }
    
    /**
     * Constructor with all parameters.
     * 
     * @param id The store ID
     * @param address The store address
     * @param managerId The ID of the manager
     */
    public Store(int id, Address address, int managerId) {
        super(id);
        this.address = address;
        this.managerId = managerId;
    }
    
    // ==================== GETTERS AND SETTERS ====================
    
    /**
     * Gets the store address.
     * 
     * @return The address object
     */
    public Address getAddress() {
        return address;
    }
    
    /**
     * Sets the store address.
     * 
     * @param address The address to set
     */
    public void setAddress(Address address) {
        this.address = address;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    /**
     * Gets the manager ID.
     * 
     * @return The manager ID
     */
    public int getManagerId() {
        return managerId;
    }
    
    /**
     * Sets the manager ID.
     * 
     * @param managerId The manager ID to set
     */
    public void setManagerId(int managerId) {
        this.managerId = managerId;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", address=" + (address != null ? address.getAddress() : "null") +
                ", managerId=" + managerId +
                ", active=" + active +
                '}';
    }
}
