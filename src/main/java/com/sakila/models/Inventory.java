/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Inventory entity model
 */

package com.sakila.models;

/**
 * Inventory entity representing film copies available for rental.
 * Maintains composition relationships with Film and Store.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class Inventory extends Entity {
    
    private Film film;            // Foreign Key: Composition
    private Store store;          // Foreign Key: Composition
    private int quantityAvailable;  // Number of copies available
    private int quantityRented;     // Number of copies currently rented
    
    /**
     * Default constructor.
     */
    public Inventory() {
        super();
        this.quantityAvailable = 0;
        this.quantityRented = 0;
    }
    
    /**
     * Constructor with film and store.
     * 
     * @param film The film in inventory
     * @param store The store where it's located
     */
    public Inventory(Film film, Store store) {
        super();
        this.film = film;
        this.store = store;
        this.quantityAvailable = 0;
        this.quantityRented = 0;
    }
    
    /**
     * Constructor with all parameters.
     * 
     * @param id The inventory ID
     * @param film The film
     * @param store The store
     * @param quantityAvailable Quantity available
     * @param quantityRented Quantity rented
     */
    public Inventory(int id, Film film, Store store, int quantityAvailable, int quantityRented) {
        super(id);
        this.film = film;
        this.store = store;
        this.quantityAvailable = quantityAvailable;
        this.quantityRented = quantityRented;
    }
    
    // ==================== GETTERS AND SETTERS ====================
    
    public Film getFilm() { return film; }
    public void setFilm(Film film) {
        this.film = film;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public Store getStore() { return store; }
    public void setStore(Store store) {
        this.store = store;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public int getQuantityAvailable() { return quantityAvailable; }
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    public int getQuantityRented() { return quantityRented; }
    public void setQuantityRented(int quantityRented) {
        this.quantityRented = quantityRented;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    /**
     * Gets the total quantity of this film in the store.
     * 
     * @return Total quantity (available + rented)
     */
    public int getTotalQuantity() {
        return quantityAvailable + quantityRented;
    }
    
    /**
     * Rents a copy of this film.
     * 
     * @return true if successful, false if none available
     */
    public boolean rentCopy() {
        if (quantityAvailable > 0) {
            quantityAvailable--;
            quantityRented++;
            this.updatedAt = java.time.LocalDateTime.now();
            return true;
        }
        return false;
    }
    
    /**
     * Returns a rented copy of this film.
     * 
     * @return true if successful, false if error
     */
    public boolean returnCopy() {
        if (quantityRented > 0) {
            quantityRented--;
            quantityAvailable++;
            this.updatedAt = java.time.LocalDateTime.now();
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", film=" + (film != null ? film.getTitle() : "null") +
                ", store=" + (store != null ? "Store#" + store.getId() : "null") +
                ", available=" + quantityAvailable +
                ", rented=" + quantityRented +
                ", total=" + getTotalQuantity() +
                ", active=" + active +
                '}';
    }
}
