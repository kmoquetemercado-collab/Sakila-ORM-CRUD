/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Manager for Staff entity CRUD operations
 */

package com.sakila.controllers;

import com.sakila.data.DataContext;
import com.sakila.models.Staff;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manager class for Staff entity CRUD operations.
 * Handles staff/employee management.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class StaffManager extends DataContext<Staff> {
    
    /**
     * Constructor initializing the StaffManager.
     * 
     * @throws SQLException if database connection fails
     */
    public StaffManager() throws SQLException {
        super("staff", Staff.class);
    }
    
    @Override
    protected boolean insertRecord(Staff entity) throws SQLException {
        String sql = "INSERT INTO staff (first_name, last_name, address_id, email, store_id, active, username, password) " +
                     "VALUES (?, ?, ?, ?, ?, 1, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setInt(3, entity.getAddress() != null ? entity.getAddress().getId() : 0);
            stmt.setString(4, entity.getEmail());
            stmt.setInt(5, entity.getStore() != null ? entity.getStore().getId() : 0);
            stmt.setString(6, entity.getFirstName().toLowerCase() + "." + entity.getLastName().toLowerCase());
            stmt.setString(7, entity.getPassword());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected ArrayList<Staff> retrieveAllRecords() throws SQLException {
        ArrayList<Staff> staffList = new ArrayList<>();
        String sql = "SELECT staff_id, first_name, last_name, email, store_id, active FROM staff";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Staff staff = new Staff(
                    rs.getInt("staff_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    null,
                    rs.getString("email"),
                    null,
                    false,
                    null
                );
                staff.setActive(rs.getInt("active"));
                staffList.add(staff);
            }
        }
        return staffList;
    }
    
    @Override
    protected Staff retrieveById(int id) throws SQLException {
        String sql = "SELECT staff_id, first_name, last_name, email, store_id, active FROM staff WHERE staff_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Staff staff = new Staff(
                    rs.getInt("staff_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    null,
                    rs.getString("email"),
                    null,
                    false,
                    null
                );
                staff.setActive(rs.getInt("active"));
                return staff;
            }
        }
        return null;
    }
    
    @Override
    protected ArrayList<Staff> retrieveByField(String fieldName, Object value) throws SQLException {
        ArrayList<Staff> staffList = new ArrayList<>();
        String sql = "SELECT staff_id, first_name, last_name, email, store_id, active FROM staff WHERE " + fieldName + " LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + value.toString() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Staff staff = new Staff(
                    rs.getInt("staff_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    null,
                    rs.getString("email"),
                    null,
                    false,
                    null
                );
                staff.setActive(rs.getInt("active"));
                staffList.add(staff);
            }
        }
        return staffList;
    }
    
    @Override
    protected boolean updateRecord(Staff entity) throws SQLException {
        String sql = "UPDATE staff SET first_name = ?, last_name = ?, email = ?, store_id = ?, active = ? WHERE staff_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setString(3, entity.getEmail());
            stmt.setInt(4, entity.getStore() != null ? entity.getStore().getId() : 0);
            stmt.setInt(5, entity.getActive());
            stmt.setInt(6, entity.getId());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected boolean softDeleteRecord(int id) throws SQLException {
        String sql = "UPDATE staff SET active = 0 WHERE staff_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected int countActiveRecords() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM staff WHERE active = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}
