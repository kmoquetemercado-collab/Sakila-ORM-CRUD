/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Actor entity model
 */

package com.sakila.models;

import java.util.ArrayList;

/**
 * Actor entity representing actors in the Sakila database.
 * Actors can participate in multiple films.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class Actor extends Entity {
    
    private String firstName;
    private String lastName;
    private ArrayList<Integer> filmIds;  // IDs of films the actor participated in
    
    /**
     * Default constructor.
     */
    public Actor() {
        super();
        this.filmIds = new ArrayList<>();
    }
    
    /**
     * Constructor with first and last name.
     * 
     * @param firstName The actor's first name
     * @param lastName The actor's last name
     */
    public Actor(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.filmIds = new ArrayList<>();
    }
    
    /**
     * Constructor with all parameters.
     * 
     * @param id The actor ID
     * @param firstName The actor's first name
     * @param lastName The actor's last name
     */
    public Actor(int id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.filmIds = new ArrayList<>();
    }
    
    // ==================== GETTERS AND SETTERS ====================
    
    /**
     * Gets the actor's first name.
     * 
     * @return The first name
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Sets the actor's first name.
     * 
     * @param firstName The first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    /**
     * Gets the actor's last name.
     * 
     * @return The last name
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Sets the actor's last name.
     * 
     * @param lastName The last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    /**
     * Gets the list of film IDs for this actor.
     * 
     * @return ArrayList of film IDs
     */
    public ArrayList<Integer> getFilmIds() {
        return filmIds;
    }
    
    /**
     * Adds a film ID to the actor's film list.
     * 
     * @param filmId The film ID to add
     */
    public void addFilmId(int filmId) {
        if (!filmIds.contains(filmId)) {
            filmIds.add(filmId);
            this.updatedAt = java.time.LocalDateTime.now();
        }
    }
    
    /**
     * Removes a film ID from the actor's film list.
     * 
     * @param filmId The film ID to remove
     */
    public void removeFilmId(int filmId) {
        if (filmIds.contains(filmId)) {
            filmIds.remove((Integer) filmId);
            this.updatedAt = java.time.LocalDateTime.now();
        }
    }
    
    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", filmCount=" + filmIds.size() +
                ", active=" + active +
                '}';
    }
}
