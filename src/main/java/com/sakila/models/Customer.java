/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Customer entity model
 */

package com.sakila.models;

import java.time.LocalDate;

/**
 * Customer entity representing customers in the Sakila database.
 * Maintains composition relationships with Address and Store.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class Customer extends Entity {
    
    private String firstName;
    private String lastName;
    private String email;
    private Address address;      // Foreign Key: Composition
    private Store store;          // Foreign Key: Composition
    private LocalDate dateOfBirth;
    private double totalRentals;  // Track total amount of rentals
    
    /**
     * Default constructor.
     */
    public Customer() {
        super();
        this.totalRentals = 0.0;
    }
    
    /**
     * Constructor with basic customer information.
     * 
     * @param firstName The customer's first name
     * @param lastName The customer's last name
     * @param email The customer's email
     */
    public Customer(String firstName, String lastName, String email) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.totalRentals = 0.0;
    }
    
    /**
     * Constructor with all parameters.
     */
    public Customer(int id, String firstName, String lastName, String email,
                    Address address, Store store, LocalDate dateOfBirth) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.store = store;
        this.dateOfBirth = dateOfBirth;
        this.totalRentals = 0.0;
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
    
    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public Address getAddress() { return address; }
    public void setAddress(Address address) {
        this.address = address;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public Store getStore() { return store; }
    public void setStore(Store store) {
        this.store = store;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public double getTotalRentals() { return totalRentals; }
    public void setTotalRentals(double totalRentals) {
        this.totalRentals = totalRentals;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public void addRentalAmount(double amount) {
        this.totalRentals += amount;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", totalRentals=" + totalRentals +
                ", active=" + active +
                '}';
    }
}
