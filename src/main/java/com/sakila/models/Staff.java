/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Staff entity model
 */

package com.sakila.models;

/**
 * Staff entity representing staff members (employees) in the Sakila database.
 * Can be managers of stores.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class Staff extends Entity {
    
    private String firstName;
    private String lastName;
    private Address address;      // Foreign Key: Composition
    private String email;
    private Store store;          // Foreign Key: Composition
    private boolean isManager;    // Whether this staff member is a manager
    private String password;      // Encrypted password
    
    /**
     * Default constructor.
     */
    public Staff() {
        super();
        this.isManager = false;
    }
    
    /**
     * Constructor with basic staff information.
     * 
     * @param firstName The staff member's first name
     * @param lastName The staff member's last name
     * @param email The staff member's email
     */
    public Staff(String firstName, String lastName, String email) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isManager = false;
    }
    
    /**
     * Constructor with all parameters.
     */
    public Staff(int id, String firstName, String lastName, Address address,
                 String email, Store store, boolean isManager, String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.store = store;
        this.isManager = isManager;
        this.password = password;
    }
    
    // ==================== GETTERS AND SETTERS ====================
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public Address getAddress() { return address; }
    public void setAddress(Address address) {
        this.address = address;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public Store getStore() { return store; }
    public void setStore(Store store) {
        this.store = store;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public boolean isManager() { return isManager; }
    public void setManager(boolean isManager) {
        this.isManager = isManager;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", isManager=" + isManager +
                ", active=" + active +
                '}';
    }
}
