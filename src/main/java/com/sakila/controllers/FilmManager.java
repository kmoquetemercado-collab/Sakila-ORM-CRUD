/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Manager for Film entity CRUD operations
 */

package com.sakila.controllers;

import com.sakila.data.DataContext;
import com.sakila.models.Film;
import com.sakila.models.Language;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Manager class for Film entity CRUD operations.
 * Handles film management with language relationships.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class FilmManager extends DataContext<Film> {
    
    private LanguageManager languageManager;
    
    /**
     * Constructor initializing the FilmManager.
     * 
     * @throws SQLException if database connection fails
     */
    public FilmManager() throws SQLException {
        super("film", Film.class);
        this.languageManager = new LanguageManager();
    }
    
    @Override
    protected boolean insertRecord(Film entity) throws SQLException {
        String sql = "INSERT INTO film (title, description, release_year, language_id, " +
                     "original_language_id, rental_duration, rental_rate, length, " +
                     "replacement_cost, rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getTitle());
            stmt.setString(2, entity.getDescription());
            stmt.setInt(3, entity.getReleaseYear() != null ? entity.getReleaseYear().getYear() : 0);
            stmt.setInt(4, entity.getLanguage() != null ? entity.getLanguage().getId() : 0);
            stmt.setInt(5, entity.getOriginalLanguage() != null ? entity.getOriginalLanguage().getId() : 0);
            stmt.setInt(6, entity.getRentalDuration());
            stmt.setDouble(7, entity.getRentalRate());
            stmt.setInt(8, entity.getLength());
            stmt.setDouble(9, entity.getReplacementCost());
            stmt.setString(10, entity.getRating());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected ArrayList<Film> retrieveAllRecords() throws SQLException {
        ArrayList<Film> films = new ArrayList<>();
        String sql = "SELECT film_id, title, description, release_year, language_id, " +
                     "original_language_id, rental_duration, rental_rate, length, " +
                     "replacement_cost, rating FROM film";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Language language = languageManager.getById(rs.getInt("language_id"));
                Language originalLanguage = languageManager.getById(rs.getInt("original_language_id"));
                
                Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    LocalDate.of(rs.getInt("release_year"), 1, 1),
                    language,
                    originalLanguage,
                    rs.getInt("rental_duration"),
                    rs.getDouble("rental_rate"),
                    rs.getInt("length"),
                    rs.getDouble("replacement_cost"),
                    rs.getString("rating")
                );
                loadFilmActors(film);
                films.add(film);
            }
        }
        return films;
    }
    
    @Override
    protected Film retrieveById(int id) throws SQLException {
        String sql = "SELECT film_id, title, description, release_year, language_id, " +
                     "original_language_id, rental_duration, rental_rate, length, " +
                     "replacement_cost, rating FROM film WHERE film_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Language language = languageManager.getById(rs.getInt("language_id"));
                Language originalLanguage = languageManager.getById(rs.getInt("original_language_id"));
                
                Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    LocalDate.of(rs.getInt("release_year"), 1, 1),
                    language,
                    originalLanguage,
                    rs.getInt("rental_duration"),
                    rs.getDouble("rental_rate"),
                    rs.getInt("length"),
                    rs.getDouble("replacement_cost"),
                    rs.getString("rating")
                );
                loadFilmActors(film);
                return film;
            }
        }
        return null;
    }
    
    @Override
    protected ArrayList<Film> retrieveByField(String fieldName, Object value) throws SQLException {
        ArrayList<Film> films = new ArrayList<>();
        String sql = "SELECT film_id, title, description, release_year, language_id, " +
                     "original_language_id, rental_duration, rental_rate, length, " +
                     "replacement_cost, rating FROM film WHERE " + fieldName + " LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + value.toString() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Language language = languageManager.getById(rs.getInt("language_id"));
                Language originalLanguage = languageManager.getById(rs.getInt("original_language_id"));
                
                Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    LocalDate.of(rs.getInt("release_year"), 1, 1),
                    language,
                    originalLanguage,
                    rs.getInt("rental_duration"),
                    rs.getDouble("rental_rate"),
                    rs.getInt("length"),
                    rs.getDouble("replacement_cost"),
                    rs.getString("rating")
                );
                loadFilmActors(film);
                films.add(film);
            }
        }
        return films;
    }
    
    @Override
    protected boolean updateRecord(Film entity) throws SQLException {
        String sql = "UPDATE film SET title = ?, description = ?, release_year = ?, " +
                     "language_id = ?, original_language_id = ?, rental_duration = ?, " +
                     "rental_rate = ?, length = ?, replacement_cost = ?, rating = ? " +
                     "WHERE film_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getTitle());
            stmt.setString(2, entity.getDescription());
            stmt.setInt(3, entity.getReleaseYear() != null ? entity.getReleaseYear().getYear() : 0);
            stmt.setInt(4, entity.getLanguage() != null ? entity.getLanguage().getId() : 0);
            stmt.setInt(5, entity.getOriginalLanguage() != null ? entity.getOriginalLanguage().getId() : 0);
            stmt.setInt(6, entity.getRentalDuration());
            stmt.setDouble(7, entity.getRentalRate());
            stmt.setInt(8, entity.getLength());
            stmt.setDouble(9, entity.getReplacementCost());
            stmt.setString(10, entity.getRating());
            stmt.setInt(11, entity.getId());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected boolean softDeleteRecord(int id) throws SQLException {
        String sql = "DELETE FROM film WHERE film_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected int countActiveRecords() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM film";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
    
    /**
     * Loads all actors for a film.
     * 
     * @param film The film to load actors for
     * @throws SQLException if database error occurs
     */
    private void loadFilmActors(Film film) throws SQLException {
        String sql = "SELECT actor_id FROM film_actor WHERE film_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, film.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                film.addActorId(rs.getInt("actor_id"));
            }
        }
    }
    
    /**
     * Gets total rental revenue for a film.
     * 
     * @param filmId The film ID
     * @return Total rental revenue
     * @throws SQLException if database error occurs
     */
    public double getFilmRevenue(int filmId) throws SQLException {
        String sql = "SELECT SUM(p.amount) as total FROM payment p " +
                     "JOIN rental r ON p.rental_id = r.rental_id " +
                     "JOIN inventory i ON r.inventory_id = i.inventory_id " +
                     "WHERE i.film_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, filmId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }
}
