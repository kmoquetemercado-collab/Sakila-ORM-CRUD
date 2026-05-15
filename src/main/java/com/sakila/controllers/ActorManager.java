/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Manager for Actor entity CRUD operations
 */

package com.sakila.controllers;

import com.sakila.data.DataContext;
import com.sakila.models.Actor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manager class for Actor entity CRUD operations.
 * Handles actor management and film associations.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class ActorManager extends DataContext<Actor> {
    
    /**
     * Constructor initializing the ActorManager.
     * 
     * @throws SQLException if database connection fails
     */
    public ActorManager() throws SQLException {
        super("actor", Actor.class);
    }
    
    @Override
    protected boolean insertRecord(Actor entity) throws SQLException {
        String sql = "INSERT INTO actor (first_name, last_name) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected ArrayList<Actor> retrieveAllRecords() throws SQLException {
        ArrayList<Actor> actors = new ArrayList<>();
        String sql = "SELECT actor_id, first_name, last_name FROM actor ORDER BY first_name, last_name";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Actor actor = new Actor(
                    rs.getInt("actor_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name")
                );
                loadActorFilms(actor);
                actors.add(actor);
            }
        }
        return actors;
    }
    
    @Override
    protected Actor retrieveById(int id) throws SQLException {
        String sql = "SELECT actor_id, first_name, last_name FROM actor WHERE actor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Actor actor = new Actor(
                    rs.getInt("actor_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name")
                );
                loadActorFilms(actor);
                return actor;
            }
        }
        return null;
    }
    
    @Override
    protected ArrayList<Actor> retrieveByField(String fieldName, Object value) throws SQLException {
        ArrayList<Actor> actors = new ArrayList<>();
        String sql = "SELECT actor_id, first_name, last_name FROM actor WHERE " + fieldName + " LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + value.toString() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Actor actor = new Actor(
                    rs.getInt("actor_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name")
                );
                loadActorFilms(actor);
                actors.add(actor);
            }
        }
        return actors;
    }
    
    @Override
    protected boolean updateRecord(Actor entity) throws SQLException {
        String sql = "UPDATE actor SET first_name = ?, last_name = ? WHERE actor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setInt(3, entity.getId());
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected boolean softDeleteRecord(int id) throws SQLException {
        // Sakila actor doesn't have active flag, so we delete the record
        String sql = "DELETE FROM actor WHERE actor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
    
    @Override
    protected int countActiveRecords() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM actor";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
    
    /**
     * Loads all films associated with an actor.
     * 
     * @param actor The actor to load films for
     * @throws SQLException if database error occurs
     */
    private void loadActorFilms(Actor actor) throws SQLException {
        String sql = "SELECT film_id FROM film_actor WHERE actor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, actor.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                actor.addFilmId(rs.getInt("film_id"));
            }
        }
    }
    
    /**
     * Gets all films for a specific actor.
     * 
     * @param actorId The actor ID
     * @return ArrayList of film IDs
     * @throws SQLException if database error occurs
     */
    public ArrayList<Integer> getActorFilms(int actorId) throws SQLException {
        ArrayList<Integer> filmIds = new ArrayList<>();
        String sql = "SELECT film_id FROM film_actor WHERE actor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, actorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                filmIds.add(rs.getInt("film_id"));
            }
        }
        return filmIds;
    }
}
