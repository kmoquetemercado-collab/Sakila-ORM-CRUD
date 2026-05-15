/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Rental entity model
 */

package com.sakila.models;

import java.time.LocalDateTime;

/**
 * Rental entity representing film rental transactions.
 * Maintains composition relationships with Inventory, Customer, and Staff.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class Rental extends Entity {
    
    private Inventory inventory;   // Foreign Key: Composition
    private Customer customer;     // Foreign Key: Composition
    private LocalDateTime rentalDate;  // Date rental started
    private LocalDateTime returnDate;  // Date rental due back
    private LocalDateTime actualReturn; // Date actually returned
    private Staff staff;           // Foreign Key: Composition (Staff who processed rental)
    private double rentalCost;     // Cost of this rental
    private String status;         // ACTIVE, RETURNED, OVERDUE, CANCELLED
    
    /**
     * Default constructor.
     */
    public Rental() {
        super();
        this.status = "ACTIVE";
        this.rentalDate = LocalDateTime.now();
    }
    
    /**
     * Constructor with basic rental information.
     * 
     * @param inventory The inventory item being rented
     * @param customer The customer renting
     * @param staff The staff member processing
     */
    public Rental(Inventory inventory, Customer customer, Staff staff) {
        super();
        this.inventory = inventory;
        this.customer = customer;
        this.staff = staff;
        this.status = "ACTIVE";
        this.rentalDate = LocalDateTime.now();
    }
    
    /**
     * Constructor with all parameters.
     */
    public Rental(int id, Inventory inventory, Customer customer, LocalDateTime rentalDate,
                  LocalDateTime returnDate, LocalDateTime actualReturn, Staff staff,
                  double rentalCost, String status) {
        super(id);
        this.inventory = inventory;
        this.customer = customer;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.actualReturn = actualReturn;
        this.staff = staff;
        this.rentalCost = rentalCost;
        this.status = status;
    }
    
    // ==================== GETTERS AND SETTERS ====================
    
    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public LocalDateTime getRentalDate() { return rentalDate; }
    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public LocalDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public LocalDateTime getActualReturn() { return actualReturn; }
    public void setActualReturn(LocalDateTime actualReturn) {
        this.actualReturn = actualReturn;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) {
        this.staff = staff;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public double getRentalCost() { return rentalCost; }
    public void setRentalCost(double rentalCost) {
        this.rentalCost = rentalCost;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    /**
     * Calculates late fees if rental is overdue.
     * 
     * @return Late fee amount
     */
    public double calculateLateFees() {
        if (actualReturn != null && actualReturn.isAfter(returnDate)) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(returnDate, actualReturn);
            return daysLate * (rentalCost / 3); // Example: 1/3 of rental cost per day
        }
        return 0.0;
    }
    
    /**
     * Marks the rental as returned.
     */
    public void returnRental() {
        this.actualReturn = LocalDateTime.now();
        this.status = "RETURNED";
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    /**
     * Checks if rental is overdue.
     * 
     * @return true if overdue, false otherwise
     */
    public boolean isOverdue() {
        if (status.equals("RETURNED")) {
            return false;
        }
        return LocalDateTime.now().isAfter(returnDate);
    }
    
    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", film=" + (inventory != null && inventory.getFilm() != null ? inventory.getFilm().getTitle() : "null") +
                ", customer=" + (customer != null ? customer.getFirstName() + " " + customer.getLastName() : "null") +
                ", rentalDate=" + rentalDate +
                ", returnDate=" + returnDate +
                ", status='" + status + '\'' +
                ", cost=" + rentalCost +
                ", active=" + active +
                '}';
    }
}
