/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Address entity model
 */

package com.sakila.models;

/**
 * Address entity representing addresses in the Sakila database.
 * Maintains a composition relationship with City.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class Address extends Entity {
    
    private String address;
    private String address2;
    private String district;
    private City city;  // Foreign Key: Composition
    private String postalCode;
    private String phone;
    
    /**
     * Default constructor.
     */
    public Address() {
        super();
    }
    
    /**
     * Constructor with basic information.
     * 
     * @param address The street address
     * @param city The city object
     */
    public Address(String address, City city) {
        super();
        this.address = address;
        this.city = city;
    }
    
    /**
     * Constructor with all parameters.
     */
    public Address(int id, String address, String address2, String district,
                   City city, String postalCode, String phone) {
        super(id);
        this.address = address;
        this.address2 = address2;
        this.district = district;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
    }
    
    // ==================== GETTERS AND SETTERS ====================
    
    public String getAddress() { return address; }
    public void setAddress(String address) {
        this.address = address;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public String getAddress2() { return address2; }
    public void setAddress2(String address2) {
        this.address2 = address2;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public String getDistrict() { return district; }
    public void setDistrict(String district) {
        this.district = district;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public City getCity() { return city; }
    public void setCity(City city) {
        this.city = city;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) {
        this.phone = phone;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", district='" + district + '\'' +
                ", city=" + (city != null ? city.getCityName() : "null") +
                ", postalCode='" + postalCode + '\'' +
                ", active=" + active +
                '}';
    }
}
