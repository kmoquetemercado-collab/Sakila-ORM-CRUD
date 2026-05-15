/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Console user interface for the Sakila management system
 */

package com.sakila.views;

import java.util.Scanner;

/**
 * Console-based user interface for managing the Sakila database.
 * Provides menu-driven navigation for CRUD operations and reporting.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class ConsoleUI {
    
    private Scanner scanner;
    private boolean running;
    
    /**
     * Constructor that initializes the console UI.
     */
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.running = true;
    }
    
    /**
     * Starts the console UI main loop.
     */
    public void start() {
        displayMainMenu();
        
        while (running) {
            System.out.print("\nSelect an option: ");
            String choice = scanner.nextLine().trim();
            
            handleMainMenuChoice(choice);
        }
        
        close();
    }
    
    /**
     * Displays the main menu.
     */
    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MAIN MENU - Sakila Database Management");
        System.out.println("=".repeat(70));
        System.out.println("1. Manage Actors");
        System.out.println("2. Manage Films");
        System.out.println("3. Manage Inventory");
        System.out.println("4. Manage Rentals");
        System.out.println("5. Manage Payments");
        System.out.println("6. Manage Customers");
        System.out.println("7. Manage Stores");
        System.out.println("8. View Reports");
        System.out.println("9. Exit");
        System.out.println("=".repeat(70));
    }
    
    /**
     * Handles main menu choice.
     * 
     * @param choice The user's menu selection
     */
    private void handleMainMenuChoice(String choice) {
        switch (choice) {
            case "1":
                System.out.println("\n[Actor Management] - Coming Soon");
                break;
            case "2":
                System.out.println("\n[Film Management] - Coming Soon");
                break;
            case "3":
                System.out.println("\n[Inventory Management] - Coming Soon");
                break;
            case "4":
                System.out.println("\n[Rental Management] - Coming Soon");
                break;
            case "5":
                System.out.println("\n[Payment Management] - Coming Soon");
                break;
            case "6":
                System.out.println("\n[Customer Management] - Coming Soon");
                break;
            case "7":
                System.out.println("\n[Store Management] - Coming Soon");
                break;
            case "8":
                System.out.println("\n[Reports] - Coming Soon");
                break;
            case "9":
                running = false;
                System.out.println("\nExiting application...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    
    /**
     * Displays a submenu for CRUD operations.
     * 
     * @param entityName The name of the entity
     */
    public void displayCRUDMenu(String entityName) {
        System.out.println("\n" + "-".repeat(70));
        System.out.println(entityName + " CRUD Operations");
        System.out.println("-".repeat(70));
        System.out.println("1. Create (POST)");
        System.out.println("2. Read (GET)");
        System.out.println("3. Update (PUT)");
        System.out.println("4. Delete (soft delete)");
        System.out.println("5. Back to Main Menu");
        System.out.println("-".repeat(70));
    }
    
    /**
     * Displays a message to the user.
     * 
     * @param message The message to display
     */
    public void displayMessage(String message) {
        System.out.println("\n" + message);
    }
    
    /**
     * Displays a success message.
     * 
     * @param message The success message
     */
    public void displaySuccess(String message) {
        System.out.println("\n✓ " + message);
    }
    
    /**
     * Displays an error message.
     * 
     * @param message The error message
     */
    public void displayError(String message) {
        System.out.println("\n✗ ERROR: " + message);
    }
    
    /**
     * Prompts the user for input.
     * 
     * @param prompt The prompt message
     * @return The user's input
     */
    public String promptInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }
    
    /**
     * Closes the console UI and releases resources.
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
        System.out.println("\n" + "=".repeat(70));
        System.out.println("   Application closed. Thank you for using Sakila ORM/CRUD Manager!");
        System.out.println("=".repeat(70) + "\n");
    }
}
