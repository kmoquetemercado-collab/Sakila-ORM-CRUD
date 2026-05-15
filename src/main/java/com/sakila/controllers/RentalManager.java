/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Manager for Rental entity CRUD operations
 */

package com.sakila.controllers;

import com.sakila.data.DataContext;
import com.sakila.models.Rental;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Manager class for Rental entity CRUD operations.
 * Manages film rental transactions.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class RentalManager extends DataContext<Rental> {
    
    /**
     * Constructor initializing the RentalManager.
     * 
     * @throws SQLException if database connection fails
     */
    public RentalManager() throws SQLException {
        super("rental", Rental.class);
    }
    
    @Override
    protected boolean insertRecord(Rental entity) throws SQLException {
        String sql = "INSERT INTO rental (rental_date, inventory_id, customer_id, return_date, staff_id) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(entity.getRentalDate()));
            stmt.setInt(2, entity.getInventory() != null ? entity.getInventory().getId() : 0);
            stmt.setInt(3, entity.getCustomer() != null ? entity.getCustomer().getId() : 0);
            stmt.setTimestamp(4, entity.getReturnDate() != null ? Timestamp.valueOf(entity.getReturnDate()) : null);
            stmt.setInt(5, entity.getStaff() != null ? entity.getStaff().getId() : 0);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected ArrayList<Rental> retrieveAllRecords() throws SQLException {
        ArrayList<Rental> rentals = new ArrayList<>();
        String sql = "SELECT rental_id, rental_date, inventory_id, customer_id, return_date, " +
                     "rental_date as actual_return, staff_id FROM rental";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Rental rental = new Rental(
                    rs.getInt("rental_id"),
                    null, null,
                    rs.getTimestamp("rental_date").toLocalDateTime(),
                    rs.getTimestamp("return_date") != null ? rs.getTimestamp("return_date").toLocalDateTime() : null,
                    rs.getTimestamp("actual_return") != null ? rs.getTimestamp("actual_return").toLocalDateTime() : null,
                    null, 0.0, "ACTIVE"
                );
                rentals.add(rental);
            }
        }
        return rentals;
    }
    
    @Override
    protected Rental retrieveById(int id) throws SQLException {
        String sql = "SELECT rental_id, rental_date, inventory_id, customer_id, return_date, staff_id FROM rental WHERE rental_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Rental(
                    rs.getInt("rental_id"),
                    null, null,
                    rs.getTimestamp("rental_date").toLocalDateTime(),
                    rs.getTimestamp("return_date") != null ? rs.getTimestamp("return_date").toLocalDateTime() : null,
                    null, null, 0.0, "ACTIVE"
                );
            }
        }
        return null;
    }
    
    @Override
    protected ArrayList<Rental> retrieveByField(String fieldName, Object value) throws SQLException {
        ArrayList<Rental> rentals = new ArrayList<>();
        String sql = "SELECT rental_id, rental_date, inventory_id, customer_id, return_date, staff_id FROM rental WHERE " + fieldName + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(value.toString()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rentals.add(new Rental(
                    rs.getInt("rental_id"),
                    null, null,
                    rs.getTimestamp("rental_date").toLocalDateTime(),
                    rs.getTimestamp("return_date") != null ? rs.getTimestamp("return_date").toLocalDateTime() : null,
                    null, null, 0.0, "ACTIVE"
                ));
            }
        }
        return rentals;
    }
    
    @Override
    protected boolean updateRecord(Rental entity) throws SQLException {
        String sql = "UPDATE rental SET return_date = ? WHERE rental_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, entity.getReturnDate() != null ? Timestamp.valueOf(entity.getReturnDate()) : null);
            stmt.setInt(2, entity.getId());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected boolean softDeleteRecord(int id) throws SQLException {
        String sql = "DELETE FROM rental WHERE rental_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected int countActiveRecords() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM rental";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
    
    /**
     * Gets all rentals for a customer.
     * 
     * @param customerId The customer ID
     * @return ArrayList of rental IDs
     * @throws SQLException if database error occurs
     */
    public ArrayList<Integer> getCustomerRentals(int customerId) throws SQLException {
        ArrayList<Integer> rentalIds = new ArrayList<>();
        String sql = "SELECT rental_id FROM rental WHERE customer_id = ? ORDER BY rental_date DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rentalIds.add(rs.getInt("rental_id"));
            }
        }
        return rentalIds;
    }
    
    /**
     * Gets all overdue rentals.
     * 
     * @return ArrayList of overdue rental IDs
     * @throws SQLException if database error occurs
     */
    public ArrayList<Integer> getOverdueRentals() throws SQLException {
        ArrayList<Integer> rentalIds = new ArrayList<>();
        String sql = "SELECT rental_id FROM rental WHERE return_date < NOW() AND return_date IS NOT NULL";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rentalIds.add(rs.getInt("rental_id"));
            }
        }
        return rentalIds;
    }
}
