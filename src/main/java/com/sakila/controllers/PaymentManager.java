/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Manager for Payment entity CRUD operations
 */

package com.sakila.controllers;

import com.sakila.data.DataContext;
import com.sakila.models.Payment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Manager class for Payment entity CRUD operations.
 * Manages payment transactions and collections.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class PaymentManager extends DataContext<Payment> {
    
    /**
     * Constructor initializing the PaymentManager.
     * 
     * @throws SQLException if database connection fails
     */
    public PaymentManager() throws SQLException {
        super("payment", Payment.class);
    }
    
    @Override
    protected boolean insertRecord(Payment entity) throws SQLException {
        String sql = "INSERT INTO payment (customer_id, rental_id, amount, payment_date, staff_id) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getCustomer() != null ? entity.getCustomer().getId() : 0);
            stmt.setInt(2, entity.getRental() != null ? entity.getRental().getId() : 0);
            stmt.setDouble(3, entity.getAmount());
            stmt.setTimestamp(4, Timestamp.valueOf(entity.getPaymentDate()));
            stmt.setInt(5, entity.getStaff() != null ? entity.getStaff().getId() : 0);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected ArrayList<Payment> retrieveAllRecords() throws SQLException {
        ArrayList<Payment> payments = new ArrayList<>();
        String sql = "SELECT payment_id, customer_id, rental_id, amount, payment_date, staff_id FROM payment";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Payment payment = new Payment(
                    rs.getInt("payment_id"),
                    null, null,
                    rs.getDouble("amount"),
                    rs.getTimestamp("payment_date").toLocalDateTime(),
                    "COMPLETED",
                    null,
                    "COMPLETED",
                    null
                );
                payments.add(payment);
            }
        }
        return payments;
    }
    
    @Override
    protected Payment retrieveById(int id) throws SQLException {
        String sql = "SELECT payment_id, customer_id, rental_id, amount, payment_date, staff_id FROM payment WHERE payment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Payment(
                    rs.getInt("payment_id"),
                    null, null,
                    rs.getDouble("amount"),
                    rs.getTimestamp("payment_date").toLocalDateTime(),
                    "COMPLETED",
                    null,
                    "COMPLETED",
                    null
                );
            }
        }
        return null;
    }
    
    @Override
    protected ArrayList<Payment> retrieveByField(String fieldName, Object value) throws SQLException {
        ArrayList<Payment> payments = new ArrayList<>();
        String sql = "SELECT payment_id, customer_id, rental_id, amount, payment_date, staff_id FROM payment WHERE " + fieldName + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(value.toString()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                payments.add(new Payment(
                    rs.getInt("payment_id"),
                    null, null,
                    rs.getDouble("amount"),
                    rs.getTimestamp("payment_date").toLocalDateTime(),
                    "COMPLETED",
                    null,
                    "COMPLETED",
                    null
                ));
            }
        }
        return payments;
    }
    
    @Override
    protected boolean updateRecord(Payment entity) throws SQLException {
        String sql = "UPDATE payment SET amount = ? WHERE payment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, entity.getAmount());
            stmt.setInt(2, entity.getId());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected boolean softDeleteRecord(int id) throws SQLException {
        String sql = "DELETE FROM payment WHERE payment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected int countActiveRecords() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM payment";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
    
    /**
     * Gets total payments for a customer.
     * 
     * @param customerId The customer ID
     * @return Total payment amount
     * @throws SQLException if database error occurs
     */
    public double getCustomerTotalPayments(int customerId) throws SQLException {
        String sql = "SELECT SUM(amount) as total FROM payment WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }
    
    /**
     * Gets total payments for a store.
     * 
     * @param storeId The store ID
     * @return Total payment amount
     * @throws SQLException if database error occurs
     */
    public double getStoreTotalPayments(int storeId) throws SQLException {
        String sql = "SELECT SUM(p.amount) as total FROM payment p " +
                     "JOIN customer c ON p.customer_id = c.customer_id " +
                     "WHERE c.store_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, storeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }
}
