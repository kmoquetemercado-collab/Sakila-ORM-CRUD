/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Manager for Customer entity CRUD operations
 */

package com.sakila.controllers;

import com.sakila.data.DataContext;
import com.sakila.models.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manager class for Customer entity CRUD operations.
 * Handles customer management including payment tracking.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class CustomerManager extends DataContext<Customer> {
    
    /**
     * Constructor initializing the CustomerManager.
     * 
     * @throws SQLException if database connection fails
     */
    public CustomerManager() throws SQLException {
        super("customer", Customer.class);
    }
    
    @Override
    protected boolean insertRecord(Customer entity) throws SQLException {
        String sql = "INSERT INTO customer (first_name, last_name, email, address_id, " +
                     "store_id, active) VALUES (?, ?, ?, ?, ?, 1)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setString(3, entity.getEmail());
            stmt.setInt(4, entity.getAddress() != null ? entity.getAddress().getId() : 0);
            stmt.setInt(5, entity.getStore() != null ? entity.getStore().getId() : 0);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected ArrayList<Customer> retrieveAllRecords() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, first_name, last_name, email, active FROM customer";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    null, null, null
                );
                customer.setActive(rs.getInt("active"));
                customers.add(customer);
            }
        }
        return customers;
    }
    
    @Override
    protected Customer retrieveById(int id) throws SQLException {
        String sql = "SELECT customer_id, first_name, last_name, email, active FROM customer WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    null, null, null
                );
                customer.setActive(rs.getInt("active"));
                return customer;
            }
        }
        return null;
    }
    
    @Override
    protected ArrayList<Customer> retrieveByField(String fieldName, Object value) throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, first_name, last_name, email, active FROM customer WHERE " + fieldName + " LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + value.toString() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    null, null, null
                );
                customer.setActive(rs.getInt("active"));
                customers.add(customer);
            }
        }
        return customers;
    }
    
    @Override
    protected boolean updateRecord(Customer entity) throws SQLException {
        String sql = "UPDATE customer SET first_name = ?, last_name = ?, email = ?, active = ? WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setString(3, entity.getEmail());
            stmt.setInt(4, entity.getActive());
            stmt.setInt(5, entity.getId());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected boolean softDeleteRecord(int id) throws SQLException {
        String sql = "UPDATE customer SET active = 0 WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected int countActiveRecords() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM customer WHERE active = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
    
    /**
     * Gets total rentals amount for a customer.
     * 
     * @param customerId The customer ID
     * @return Total rental amount
     * @throws SQLException if database error occurs
     */
    public double getCustomerTotalRentals(int customerId) throws SQLException {
        String sql = "SELECT SUM(p.amount) as total FROM payment p WHERE p.customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }
}
