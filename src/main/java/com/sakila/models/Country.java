/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Country entity model
 */

package com.sakila.models;

/**
 * Country entity representing countries in the Sakila database.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class Country extends Entity {
    
    private String countryName;
    
    /**
     * Default constructor.
     */
    public Country() {
        super();
    }
    
    /**
     * Constructor with country name.
     * 
     * @param countryName The name of the country
     */
    public Country(String countryName) {
        super();
        this.countryName = countryName;
    }
    
    /**
     * Constructor with ID and country name.
     * 
     * @param id The country ID
     * @param countryName The name of the country
     */
    public Country(int id, String countryName) {
        super(id);
        this.countryName = countryName;
    }
    
    // ==================== GETTERS AND SETTERS ====================
    
    /**
     * Gets the country name.
     * 
     * @return The country name
     */
    public String getCountryName() {
        return countryName;
    }
    
    /**
     * Sets the country name.
     * 
     * @param countryName The country name to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                ", active=" + active +
                '}';
    }
}
