/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Film entity model
 */

package com.sakila.models;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Film entity representing films in the Sakila database.
 * Maintains composition relationships with Language.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class Film extends Entity {
    
    private String title;
    private String description;
    private LocalDate releaseYear;
    private Language language;  // Foreign Key: Composition
    private Language originalLanguage;  // Foreign Key: Composition
    private int rentalDuration;  // Days available for rental
    private double rentalRate;   // Cost to rent
    private int length;           // Duration in minutes
    private double replacementCost;  // Cost to replace if damaged
    private String rating;         // G, PG, PG-13, R, NC-17
    private ArrayList<Integer> actorIds;  // IDs of actors in the film
    
    /**
     * Default constructor.
     */
    public Film() {
        super();
        this.actorIds = new ArrayList<>();
    }
    
    /**
     * Constructor with basic film information.
     * 
     * @param title The film title
     * @param language The film language
     */
    public Film(String title, Language language) {
        super();
        this.title = title;
        this.language = language;
        this.actorIds = new ArrayList<>();
    }
    
    /**
     * Constructor with all parameters.
     */
    public Film(int id, String title, String description, LocalDate releaseYear,
                Language language, Language originalLanguage, int rentalDuration,
                double rentalRate, int length, double replacementCost, String rating) {
        super(id);
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.language = language;
        this.originalLanguage = originalLanguage;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.length = length;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.actorIds = new ArrayList<>();
    }
    
    // ==================== GETTERS AND SETTERS ====================
    
    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public LocalDate getReleaseYear() { return releaseYear; }
    public void setReleaseYear(LocalDate releaseYear) {
        this.releaseYear = releaseYear;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public Language getLanguage() { return language; }
    public void setLanguage(Language language) {
        this.language = language;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public Language getOriginalLanguage() { return originalLanguage; }
    public void setOriginalLanguage(Language originalLanguage) {
        this.originalLanguage = originalLanguage;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public int getRentalDuration() { return rentalDuration; }
    public void setRentalDuration(int rentalDuration) {
        this.rentalDuration = rentalDuration;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public double getRentalRate() { return rentalRate; }
    public void setRentalRate(double rentalRate) {
        this.rentalRate = rentalRate;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public int getLength() { return length; }
    public void setLength(int length) {
        this.length = length;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public double getReplacementCost() { return replacementCost; }
    public void setReplacementCost(double replacementCost) {
        this.replacementCost = replacementCost;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public String getRating() { return rating; }
    public void setRating(String rating) {
        this.rating = rating;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public ArrayList<Integer> getActorIds() { return actorIds; }
    
    public void addActorId(int actorId) {
        if (!actorIds.contains(actorId)) {
            actorIds.add(actorId);
            this.updatedAt = java.time.LocalDateTime.now();
        }
    }
    
    public void removeActorId(int actorId) {
        if (actorIds.contains(actorId)) {
            actorIds.remove((Integer) actorId);
            this.updatedAt = java.time.LocalDateTime.now();
        }
    }
    
    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", rentalRate=" + rentalRate +
                ", length=" + length +
                ", rating='" + rating + '\'' +
                ", actorCount=" + actorIds.size() +
                ", active=" + active +
                '}';
    }
}
