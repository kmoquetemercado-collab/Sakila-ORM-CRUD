/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Manager for City entity CRUD operations
 */

package com.sakila.controllers;

import com.sakila.data.DataContext;
import com.sakila.models.City;
import com.sakila.models.Country;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manager class for City entity CRUD operations.
 * Handles city management with foreign key relationships to Country.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class CityManager extends DataContext<City> {
    
    private CountryManager countryManager;
    
    /**
     * Constructor initializing the CityManager.
     * 
     * @throws SQLException if database connection fails
     */
    public CityManager() throws SQLException {
        super("city", City.class);
        this.countryManager = new CountryManager();
    }
    
    @Override
    protected boolean insertRecord(City entity) throws SQLException {
        String sql = "INSERT INTO city (city, country_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getCityName());
            stmt.setInt(2, entity.getCountry() != null ? entity.getCountry().getId() : 0);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected ArrayList<City> retrieveAllRecords() throws SQLException {
        ArrayList<City> cities = new ArrayList<>();
        String sql = "SELECT city_id, city, country_id FROM city";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Country country = countryManager.getById(rs.getInt("country_id"));
                City city = new City(
                    rs.getInt("city_id"),
                    rs.getString("city"),
                    country
                );
                cities.add(city);
            }
        }
        return cities;
    }
    
    @Override
    protected City retrieveById(int id) throws SQLException {
        String sql = "SELECT city_id, city, country_id FROM city WHERE city_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Country country = countryManager.getById(rs.getInt("country_id"));
                return new City(
                    rs.getInt("city_id"),
                    rs.getString("city"),
                    country
                );
            }
        }
        return null;
    }
    
    @Override
    protected ArrayList<City> retrieveByField(String fieldName, Object value) throws SQLException {
        ArrayList<City> cities = new ArrayList<>();
        String sql = "SELECT city_id, city, country_id FROM city WHERE " + fieldName + " LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + value.toString() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Country country = countryManager.getById(rs.getInt("country_id"));
                cities.add(new City(
                    rs.getInt("city_id"),
                    rs.getString("city"),
                    country
                ));
            }
        }
        return cities;
    }
    
    @Override
    protected boolean updateRecord(City entity) throws SQLException {
        String sql = "UPDATE city SET city = ?, country_id = ? WHERE city_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getCityName());
            stmt.setInt(2, entity.getCountry() != null ? entity.getCountry().getId() : 0);
            stmt.setInt(3, entity.getId());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected boolean softDeleteRecord(int id) throws SQLException {
        String sql = "DELETE FROM city WHERE city_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected int countActiveRecords() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM city";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}
