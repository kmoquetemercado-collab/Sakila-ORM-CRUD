/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Manager for Address entity CRUD operations
 */

package com.sakila.controllers;

import com.sakila.data.DataContext;
import com.sakila.models.Address;
import com.sakila.models.City;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manager class for Address entity CRUD operations.
 * Handles address management with city relationships.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class AddressManager extends DataContext<Address> {
    
    private CityManager cityManager;
    
    /**
     * Constructor initializing the AddressManager.
     * 
     * @throws SQLException if database connection fails
     */
    public AddressManager() throws SQLException {
        super("address", Address.class);
        this.cityManager = new CityManager();
    }
    
    @Override
    protected boolean insertRecord(Address entity) throws SQLException {
        String sql = "INSERT INTO address (address, address2, district, city_id, postal_code, phone) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getAddress());
            stmt.setString(2, entity.getAddress2());
            stmt.setString(3, entity.getDistrict());
            stmt.setInt(4, entity.getCity() != null ? entity.getCity().getId() : 0);
            stmt.setString(5, entity.getPostalCode());
            stmt.setString(6, entity.getPhone());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected ArrayList<Address> retrieveAllRecords() throws SQLException {
        ArrayList<Address> addresses = new ArrayList<>();
        String sql = "SELECT address_id, address, address2, district, city_id, postal_code, phone FROM address";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                City city = cityManager.getById(rs.getInt("city_id"));
                Address address = new Address(
                    rs.getInt("address_id"),
                    rs.getString("address"),
                    rs.getString("address2"),
                    rs.getString("district"),
                    city,
                    rs.getString("postal_code"),
                    rs.getString("phone")
                );
                addresses.add(address);
            }
        }
        return addresses;
    }
    
    @Override
    protected Address retrieveById(int id) throws SQLException {
        String sql = "SELECT address_id, address, address2, district, city_id, postal_code, phone FROM address WHERE address_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                City city = cityManager.getById(rs.getInt("city_id"));
                return new Address(
                    rs.getInt("address_id"),
                    rs.getString("address"),
                    rs.getString("address2"),
                    rs.getString("district"),
                    city,
                    rs.getString("postal_code"),
                    rs.getString("phone")
                );
            }
        }
        return null;
    }
    
    @Override
    protected ArrayList<Address> retrieveByField(String fieldName, Object value) throws SQLException {
        ArrayList<Address> addresses = new ArrayList<>();
        String sql = "SELECT address_id, address, address2, district, city_id, postal_code, phone FROM address WHERE " + fieldName + " LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + value.toString() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                City city = cityManager.getById(rs.getInt("city_id"));
                addresses.add(new Address(
                    rs.getInt("address_id"),
                    rs.getString("address"),
                    rs.getString("address2"),
                    rs.getString("district"),
                    city,
                    rs.getString("postal_code"),
                    rs.getString("phone")
                ));
            }
        }
        return addresses;
    }
    
    @Override
    protected boolean updateRecord(Address entity) throws SQLException {
        String sql = "UPDATE address SET address = ?, address2 = ?, district = ?, city_id = ?, postal_code = ?, phone = ? WHERE address_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getAddress());
            stmt.setString(2, entity.getAddress2());
            stmt.setString(3, entity.getDistrict());
            stmt.setInt(4, entity.getCity() != null ? entity.getCity().getId() : 0);
            stmt.setString(5, entity.getPostalCode());
            stmt.setString(6, entity.getPhone());
            stmt.setInt(7, entity.getId());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected boolean softDeleteRecord(int id) throws SQLException {
        String sql = "DELETE FROM address WHERE address_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected int countActiveRecords() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM address";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}
