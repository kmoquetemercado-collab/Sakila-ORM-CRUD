/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Manager for Language entity CRUD operations
 */

package com.sakila.controllers;

import com.sakila.data.DataContext;
import com.sakila.models.Language;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manager class for Language entity CRUD operations.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class LanguageManager extends DataContext<Language> {
    
    /**
     * Constructor initializing the LanguageManager.
     * 
     * @throws SQLException if database connection fails
     */
    public LanguageManager() throws SQLException {
        super("language", Language.class);
    }
    
    @Override
    protected boolean insertRecord(Language entity) throws SQLException {
        String sql = "INSERT INTO language (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getLanguageName());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected ArrayList<Language> retrieveAllRecords() throws SQLException {
        ArrayList<Language> languages = new ArrayList<>();
        String sql = "SELECT language_id, name FROM language";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Language language = new Language(
                    rs.getInt("language_id"),
                    rs.getString("name")
                );
                languages.add(language);
            }
        }
        return languages;
    }
    
    @Override
    protected Language retrieveById(int id) throws SQLException {
        String sql = "SELECT language_id, name FROM language WHERE language_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Language(
                    rs.getInt("language_id"),
                    rs.getString("name")
                );
            }
        }
        return null;
    }
    
    @Override
    protected ArrayList<Language> retrieveByField(String fieldName, Object value) throws SQLException {
        ArrayList<Language> languages = new ArrayList<>();
        String sql = "SELECT language_id, name FROM language WHERE " + fieldName + " LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + value.toString() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                languages.add(new Language(
                    rs.getInt("language_id"),
                    rs.getString("name")
                ));
            }
        }
        return languages;
    }
    
    @Override
    protected boolean updateRecord(Language entity) throws SQLException {
        String sql = "UPDATE language SET name = ? WHERE language_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getLanguageName());
            stmt.setInt(2, entity.getId());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected boolean softDeleteRecord(int id) throws SQLException {
        String sql = "DELETE FROM language WHERE language_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected int countActiveRecords() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM language";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}
