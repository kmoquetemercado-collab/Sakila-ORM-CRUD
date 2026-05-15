/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Main entry point for the Sakila Database Management Application
 */

package com.sakila;

import com.sakila.views.ConsoleUI;

/**
 * Main class for the Sakila ORM/CRUD Application.
 * This is the entry point of the application that initializes the console UI.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class Main {
    /**
     * Main method that starts the application.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("   SAKILA DATABASE - ORM/CRUD MANAGER");
        System.out.println("   Java Programming Final Project (JP INF514)");
        System.out.println("=".repeat(70) + "\n");

        try {
            ConsoleUI ui = new ConsoleUI();
            ui.start();
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
