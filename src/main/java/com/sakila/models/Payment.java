/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Payment entity model
 */

package com.sakila.models;

import java.time.LocalDateTime;

/**
 * Payment entity representing payment transactions for rentals.
 * Maintains composition relationships with Customer, Rental, and Staff.
 * Tracks all monetary transactions in the system.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class Payment extends Entity {
    
    private Customer customer;     // Foreign Key: Composition
    private Rental rental;         // Foreign Key: Composition
    private double amount;         // Payment amount
    private LocalDateTime paymentDate;  // When payment was made
    private String paymentMethod;  // CASH, CREDIT_CARD, DEBIT_CARD, CHECK
    private Staff staff;           // Foreign Key: Composition (Staff who processed)
    private String status;         // PENDING, COMPLETED, CANCELLED
    private String notes;          // Additional notes
    
    /**
     * Default constructor.
     */
    public Payment() {
        super();
        this.status = "PENDING";
        this.paymentDate = LocalDateTime.now();
    }
    
    /**
     * Constructor with basic payment information.
     * 
     * @param customer The customer making payment
     * @param rental The rental being paid
     * @param amount The payment amount
     */
    public Payment(Customer customer, Rental rental, double amount) {
        super();
        this.customer = customer;
        this.rental = rental;
        this.amount = amount;
        this.status = "COMPLETED";
        this.paymentDate = LocalDateTime.now();
    }
    
    /**
     * Constructor with all parameters.
     */
    public Payment(int id, Customer customer, Rental rental, double amount,
                   LocalDateTime paymentDate, String paymentMethod, Staff staff,
                   String status, String notes) {
        super(id);
        this.customer = customer;
        this.rental = rental;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.staff = staff;
        this.status = status;
        this.notes = notes;
    }
    
    // ==================== GETTERS AND SETTERS ====================
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public Rental getRental() { return rental; }
    public void setRental(Rental rental) {
        this.rental = rental;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) {
        this.amount = amount;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) {
        this.staff = staff;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) {
        this.notes = notes;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    /**
     * Completes the payment (changes status to COMPLETED).
     */
    public void completePayment() {
        this.status = "COMPLETED";
        this.paymentDate = LocalDateTime.now();
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    /**
     * Cancels the payment.
     */
    public void cancelPayment() {
        this.status = "CANCELLED";
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    /**
     * Checks if payment is completed.
     * 
     * @return true if completed, false otherwise
     */
    public boolean isCompleted() {
        return status.equals("COMPLETED");
    }
    
    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", customer=" + (customer != null ? customer.getFirstName() + " " + customer.getLastName() : "null") +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", method='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                ", active=" + active +
                '}';
    }
}
