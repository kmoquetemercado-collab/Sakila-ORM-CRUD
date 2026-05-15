/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Regex validation utilities for common data formats
 */

package com.sakila.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * Utility class for validating various data formats using regular expressions.
 * Provides static methods for validating dates, SSN, phone numbers, emails, etc.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class RegexValidator {
    
    // Regex patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^(\\+\\d{1,3})?[-.\\s]?(\\(\\d{1,4}\\))?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$"
    );
    
    private static final Pattern SSN_PATTERN = Pattern.compile(
        "^(\\d{3}-?\\d{2}-?\\d{4})$"
    );
    
    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile(
        "^[A-Z0-9]{3,10}$"
    );
    
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9\\s\\-\\_]{1,255}$"
    );
    
    /**
     * Validates an email address format.
     * 
     * @param email The email to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validates a phone number format.
     * 
     * @param phone The phone number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * Validates a Social Security Number (SSN) format.
     * Accepts formats: XXX-XX-XXXX or XXXXXXXXX
     * 
     * @param ssn The SSN to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidSSN(String ssn) {
        if (ssn == null || ssn.trim().isEmpty()) {
            return false;
        }
        return SSN_PATTERN.matcher(ssn).matches();
    }
    
    /**
     * Validates a date in YYYY-MM-DD format.
     * 
     * @param date The date string to validate
     * @return true if valid date, false otherwise
     */
    public static boolean isValidDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    /**
     * Validates a date in YYYY-MM-DD HH:mm:ss format.
     * 
     * @param dateTime The datetime string to validate
     * @return true if valid datetime, false otherwise
     */
    public static boolean isValidDateTime(String dateTime) {
        if (dateTime == null || dateTime.trim().isEmpty()) {
            return false;
        }
        // Simplified validation - checks format pattern
        return dateTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
    }
    
    /**
     * Validates a postal code format.
     * 
     * @param postalCode The postal code to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPostalCode(String postalCode) {
        if (postalCode == null || postalCode.trim().isEmpty()) {
            return false;
        }
        return POSTAL_CODE_PATTERN.matcher(postalCode.toUpperCase()).matches();
    }
    
    /**
     * Validates alphanumeric string (letters, numbers, spaces, hyphens, underscores).
     * 
     * @param text The text to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidAlphanumeric(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        return ALPHANUMERIC_PATTERN.matcher(text).matches();
    }
    
    /**
     * Validates if a string is a valid positive integer.
     * 
     * @param value The value to validate
     * @return true if valid positive integer, false otherwise
     */
    public static boolean isValidPositiveInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            int num = Integer.parseInt(value);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validates if a string is a valid decimal number.
     * 
     * @param value The value to validate
     * @return true if valid decimal, false otherwise
     */
    public static boolean isValidDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
