/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Language entity model
 */

package com.sakila.models;

/**
 * Language entity representing languages in the Sakila database.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class Language extends Entity {
    
    private String languageName;
    
    /**
     * Default constructor.
     */
    public Language() {
        super();
    }
    
    /**
     * Constructor with language name.
     * 
     * @param languageName The name of the language
     */
    public Language(String languageName) {
        super();
        this.languageName = languageName;
    }
    
    /**
     * Constructor with ID and language name.
     * 
     * @param id The language ID
     * @param languageName The name of the language
     */
    public Language(int id, String languageName) {
        super(id);
        this.languageName = languageName;
    }
    
    // ==================== GETTERS AND SETTERS ====================
    
    /**
     * Gets the language name.
     * 
     * @return The language name
     */
    public String getLanguageName() {
        return languageName;
    }
    
    /**
     * Sets the language name.
     * 
     * @param languageName The language name to set
     */
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", languageName='" + languageName + '\'' +
                ", active=" + active +
                '}';
    }
}
