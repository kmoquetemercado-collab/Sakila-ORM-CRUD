/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Manager for Country entity CRUD operations
 */

package com.sakila.controllers;

import com.sakila.data.DataContext;
import com.sakila.models.Country;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manager class for Country entity CRUD operations.
 * Implements all abstract methods from DataContext for country management.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class CountryManager extends DataContext<Country> {
    
    /**
     * Constructor initializing the CountryManager.
     * 
     * @throws SQLException if database connection fails
     */
    public CountryManager() throws SQLException {
        super("country", Country.class);
    }
    
    @Override
    protected boolean insertRecord(Country entity) throws SQLException {
        String sql = "INSERT INTO country (country) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getCountryName());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected ArrayList<Country> retrieveAllRecords() throws SQLException {
        ArrayList<Country> countries = new ArrayList<>();
        String sql = "SELECT country_id, country FROM country WHERE 1=1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Country country = new Country(
                    rs.getInt("country_id"),
                    rs.getString("country")
                );
                countries.add(country);
            }
        }
        return countries;
    }
    
    @Override
    protected Country retrieveById(int id) throws SQLException {
        String sql = "SELECT country_id, country FROM country WHERE country_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Country(
                    rs.getInt("country_id"),
                    rs.getString("country")
                );
            }
        }
        return null;
    }
    
    @Override
    protected ArrayList<Country> retrieveByField(String fieldName, Object value) throws SQLException {
        ArrayList<Country> countries = new ArrayList<>();
        String sql = "SELECT country_id, country FROM country WHERE " + fieldName + " LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + value.toString() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                countries.add(new Country(
                    rs.getInt("country_id"),
                    rs.getString("country")
                ));
            }
        }
        return countries;
    }
    
    @Override
    protected boolean updateRecord(Country entity) throws SQLException {
        String sql = "UPDATE country SET country = ? WHERE country_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getCountryName());
            stmt.setInt(2, entity.getId());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected boolean softDeleteRecord(int id) throws SQLException {
        // For Country, we don't have active flag, so we'll implement it if needed
        // For now, just mark as a comment that soft delete is not applicable
        String sql = "DELETE FROM country WHERE country_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected int countActiveRecords() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM country";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}
