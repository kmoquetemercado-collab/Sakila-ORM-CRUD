/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Manager for Inventory entity CRUD operations
 */

package com.sakila.controllers;

import com.sakila.data.DataContext;
import com.sakila.models.Inventory;
import com.sakila.models.Film;
import com.sakila.models.Store;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manager class for Inventory entity CRUD operations.
 * Manages film inventory across stores.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class InventoryManager extends DataContext<Inventory> {
    
    private FilmManager filmManager;
    private StoreManager storeManager;
    
    /**
     * Constructor initializing the InventoryManager.
     * 
     * @throws SQLException if database connection fails
     */
    public InventoryManager() throws SQLException {
        super("inventory", Inventory.class);
        this.filmManager = new FilmManager();
        this.storeManager = new StoreManager();
    }
    
    @Override
    protected boolean insertRecord(Inventory entity) throws SQLException {
        String sql = "INSERT INTO inventory (film_id, store_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getFilm() != null ? entity.getFilm().getId() : 0);
            stmt.setInt(2, entity.getStore() != null ? entity.getStore().getId() : 0);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected ArrayList<Inventory> retrieveAllRecords() throws SQLException {
        ArrayList<Inventory> inventories = new ArrayList<>();
        String sql = "SELECT inventory_id, film_id, store_id FROM inventory";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Film film = filmManager.getById(rs.getInt("film_id"));
                Store store = storeManager.getById(rs.getInt("store_id"));
                Inventory inventory = new Inventory(
                    rs.getInt("inventory_id"),
                    film,
                    store,
                    0, 0
                );
                inventories.add(inventory);
            }
        }
        return inventories;
    }
    
    @Override
    protected Inventory retrieveById(int id) throws SQLException {
        String sql = "SELECT inventory_id, film_id, store_id FROM inventory WHERE inventory_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Film film = filmManager.getById(rs.getInt("film_id"));
                Store store = storeManager.getById(rs.getInt("store_id"));
                return new Inventory(
                    rs.getInt("inventory_id"),
                    film,
                    store,
                    0, 0
                );
            }
        }
        return null;
    }
    
    @Override
    protected ArrayList<Inventory> retrieveByField(String fieldName, Object value) throws SQLException {
        ArrayList<Inventory> inventories = new ArrayList<>();
        String sql = "SELECT inventory_id, film_id, store_id FROM inventory WHERE " + fieldName + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(value.toString()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Film film = filmManager.getById(rs.getInt("film_id"));
                Store store = storeManager.getById(rs.getInt("store_id"));
                inventories.add(new Inventory(
                    rs.getInt("inventory_id"),
                    film,
                    store,
                    0, 0
                ));
            }
        }
        return inventories;
    }
    
    @Override
    protected boolean updateRecord(Inventory entity) throws SQLException {
        String sql = "UPDATE inventory SET film_id = ?, store_id = ? WHERE inventory_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getFilm() != null ? entity.getFilm().getId() : 0);
            stmt.setInt(2, entity.getStore() != null ? entity.getStore().getId() : 0);
            stmt.setInt(3, entity.getId());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected boolean softDeleteRecord(int id) throws SQLException {
        String sql = "DELETE FROM inventory WHERE inventory_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected int countActiveRecords() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM inventory";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
    
    /**
     * Gets total rental count for an inventory item.
     * 
     * @param inventoryId The inventory ID
     * @return Total rental count
     * @throws SQLException if database error occurs
     */
    public int getInventoryRentalCount(int inventoryId) throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM rental WHERE inventory_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, inventoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
    
    /**
     * Gets total value of inventory for a store.
     * 
     * @param storeId The store ID
     * @return Total inventory value
     * @throws SQLException if database error occurs
     */
    public double getStoreInventoryValue(int storeId) throws SQLException {
        String sql = "SELECT SUM(f.replacement_cost) as total FROM inventory i " +
                     "JOIN film f ON i.film_id = f.film_id WHERE i.store_id = ?";
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
