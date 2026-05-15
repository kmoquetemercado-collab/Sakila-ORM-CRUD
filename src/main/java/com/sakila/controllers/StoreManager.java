/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Manager for Store entity CRUD operations
 */

package com.sakila.controllers;

import com.sakila.data.DataContext;
import com.sakila.models.Store;
import com.sakila.models.Address;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manager class for Store entity CRUD operations.
 * Handles store management with address and manager relationships.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class StoreManager extends DataContext<Store> {
    
    private AddressManager addressManager;
    
    /**
     * Constructor initializing the StoreManager.
     * 
     * @throws SQLException if database connection fails
     */
    public StoreManager() throws SQLException {
        super("store", Store.class);
        this.addressManager = new AddressManager();
    }
    
    @Override
    protected boolean insertRecord(Store entity) throws SQLException {
        String sql = "INSERT INTO store (address_id, manager_staff_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getAddress() != null ? entity.getAddress().getId() : 0);
            stmt.setInt(2, entity.getManagerId());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected ArrayList<Store> retrieveAllRecords() throws SQLException {
        ArrayList<Store> stores = new ArrayList<>();
        String sql = "SELECT store_id, address_id, manager_staff_id FROM store";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Address address = addressManager.getById(rs.getInt("address_id"));
                Store store = new Store(
                    rs.getInt("store_id"),
                    address,
                    rs.getInt("manager_staff_id")
                );
                stores.add(store);
            }
        }
        return stores;
    }
    
    @Override
    protected Store retrieveById(int id) throws SQLException {
        String sql = "SELECT store_id, address_id, manager_staff_id FROM store WHERE store_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Address address = addressManager.getById(rs.getInt("address_id"));
                return new Store(
                    rs.getInt("store_id"),
                    address,
                    rs.getInt("manager_staff_id")
                );
            }
        }
        return null;
    }
    
    @Override
    protected ArrayList<Store> retrieveByField(String fieldName, Object value) throws SQLException {
        ArrayList<Store> stores = new ArrayList<>();
        String sql = "SELECT store_id, address_id, manager_staff_id FROM store WHERE " + fieldName + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(value.toString()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Address address = addressManager.getById(rs.getInt("address_id"));
                stores.add(new Store(
                    rs.getInt("store_id"),
                    address,
                    rs.getInt("manager_staff_id")
                ));
            }
        }
        return stores;
    }
    
    @Override
    protected boolean updateRecord(Store entity) throws SQLException {
        String sql = "UPDATE store SET address_id = ?, manager_staff_id = ? WHERE store_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getAddress() != null ? entity.getAddress().getId() : 0);
            stmt.setInt(2, entity.getManagerId());
            stmt.setInt(3, entity.getId());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected boolean softDeleteRecord(int id) throws SQLException {
        String sql = "DELETE FROM store WHERE store_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected int countActiveRecords() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM store";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
    
    /**
     * Gets total revenue for a store.
     * 
     * @param storeId The store ID
     * @return Total revenue
     * @throws SQLException if database error occurs
     */
    public double getStoreRevenue(int storeId) throws SQLException {
        String sql = "SELECT SUM(p.amount) as total FROM payment p " +
                     "JOIN rental r ON p.rental_id = r.rental_id " +
                     "JOIN inventory i ON r.inventory_id = i.inventory_id " +
                     "WHERE i.store_id = ?";
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
