/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: City entity model
 */

package com.sakila.models;

/**
 * City entity representing cities in the Sakila database.
 * Maintains a composition relationship with Country.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class City extends Entity {
    
    private String cityName;
    private Country country;  // Foreign Key: Composition
    
    /**
     * Default constructor.
     */
    public City() {
        super();
    }
    
    /**
     * Constructor with city name and country.
     * 
     * @param cityName The name of the city
     * @param country The country object
     */
    public City(String cityName, Country country) {
        super();
        this.cityName = cityName;
        this.country = country;
    }
    
    /**
     * Constructor with all parameters.
     * 
     * @param id The city ID
     * @param cityName The name of the city
     * @param country The country object
     */
    public City(int id, String cityName, Country country) {
        super(id);
        this.cityName = cityName;
        this.country = country;
    }
    
    // ==================== GETTERS AND SETTERS ====================
    
    /**
     * Gets the city name.
     * 
     * @return The city name
     */
    public String getCityName() {
        return cityName;
    }
    
    /**
     * Sets the city name.
     * 
     * @param cityName The city name to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    /**
     * Gets the associated country.
     * 
     * @return The country object
     */
    public Country getCountry() {
        return country;
    }
    
    /**
     * Sets the associated country.
     * 
     * @param country The country object to set
     */
    public void setCountry(Country country) {
        this.country = country;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", country=" + (country != null ? country.getCountryName() : "null") +
                ", active=" + active +
                '}';
    }
}
