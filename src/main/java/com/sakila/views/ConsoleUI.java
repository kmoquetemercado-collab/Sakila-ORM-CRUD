/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Enhanced console user interface with CRUD operations
 */

package com.sakila.views;

import com.sakila.controllers.*;
import com.sakila.models.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Enhanced console-based user interface for Sakila database management.
 * Provides comprehensive CRUD operations and navigation menus.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class ConsoleUI {
    
    private Scanner scanner;
    private boolean running;
    
    // Manager instances
    private ActorManager actorManager;
    private FilmManager filmManager;
    private CountryManager countryManager;
    private CityManager cityManager;
    private AddressManager addressManager;
    private StoreManager storeManager;
    private CustomerManager customerManager;
    private StaffManager staffManager;
    private InventoryManager inventoryManager;
    private RentalManager rentalManager;
    private PaymentManager paymentManager;
    private LanguageManager languageManager;
    
    /**
     * Constructor that initializes the console UI and database managers.
     */
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.running = true;
        initializeManagers();
    }
    
    /**
     * Initializes all database managers.
     */
    private void initializeManagers() {
        try {
            actorManager = new ActorManager();
            filmManager = new FilmManager();
            countryManager = new CountryManager();
            cityManager = new CityManager();
            addressManager = new AddressManager();
            storeManager = new StoreManager();
            customerManager = new CustomerManager();
            staffManager = new StaffManager();
            inventoryManager = new InventoryManager();
            rentalManager = new RentalManager();
            paymentManager = new PaymentManager();
            languageManager = new LanguageManager();
            displaySuccess("All database connections established");
        } catch (SQLException e) {
            displayError("Failed to initialize managers: " + e.getMessage());
        }
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
        System.out.println("8. Manage Countries/Cities");
        System.out.println("9. View Reports & Statistics");
        System.out.println("0. Exit");
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
                manageActors();
                break;
            case "2":
                manageFilms();
                break;
            case "3":
                manageInventory();
                break;
            case "4":
                manageRentals();
                break;
            case "5":
                managePayments();
                break;
            case "6":
                manageCustomers();
                break;
            case "7":
                manageStores();
                break;
            case "8":
                manageCountriesCities();
                break;
            case "9":
                viewReports();
                break;
            case "0":
                running = false;
                System.out.println("\nExiting application...");
                break;
            default:
                displayError("Invalid option. Please try again.");
        }
    }
    
    /**
     * Manages actor CRUD operations.
     */
    private void manageActors() {
        boolean back = false;
        while (!back) {
            displayCRUDMenu("ACTOR");
            System.out.print("Select operation: ");
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    createActor();
                    break;
                case "2":
                    readActors();
                    break;
                case "3":
                    updateActor();
                    break;
                case "4":
                    deleteActor();
                    break;
                case "5":
                    back = true;
                    displayMainMenu();
                    break;
                default:
                    displayError("Invalid option");
            }
        }
    }
    
    /**
     * Creates a new actor.
     */
    private void createActor() {
        String firstName = promptInput("Enter actor first name");
        String lastName = promptInput("Enter actor last name");
        
        Actor actor = new Actor(firstName, lastName);
        if (actorManager.post(actor)) {
            displaySuccess("Actor created successfully: " + firstName + " " + lastName);
        } else {
            displayError("Failed to create actor");
        }
    }
    
    /**
     * Reads and displays all actors.
     */
    private void readActors() {
        ArrayList<Actor> actors = actorManager.getAll();
        if (actors.isEmpty()) {
            displayMessage("No actors found");
        } else {
            System.out.println("\n" + "-".repeat(70));
            System.out.println(String.format("%-5s %-20s %-20s %-15s", "ID", "First Name", "Last Name", "Films"));
            System.out.println("-".repeat(70));
            for (Actor actor : actors) {
                System.out.printf("%-5d %-20s %-20s %-15d%n",
                    actor.getId(),
                    actor.getFirstName(),
                    actor.getLastName(),
                    actor.getFilmIds().size());
            }
            System.out.println("-".repeat(70));
        }
    }
    
    /**
     * Updates an existing actor.
     */
    private void updateActor() {
        int id = Integer.parseInt(promptInput("Enter actor ID to update"));
        Actor actor = actorManager.getById(id);
        if (actor == null) {
            displayError("Actor not found");
            return;
        }
        
        String firstName = promptInput("Enter new first name (current: " + actor.getFirstName() + ")");
        String lastName = promptInput("Enter new last name (current: " + actor.getLastName() + ")");
        
        actor.setFirstName(firstName);
        actor.setLastName(lastName);
        
        if (actorManager.put(actor)) {
            displaySuccess("Actor updated successfully");
        } else {
            displayError("Failed to update actor");
        }
    }
    
    /**
     * Deletes an actor.
     */
    private void deleteActor() {
        int id = Integer.parseInt(promptInput("Enter actor ID to delete"));
        if (actorManager.delete(id)) {
            displaySuccess("Actor deleted successfully");
        } else {
            displayError("Failed to delete actor");
        }
    }
    
    /**
     * Manages film CRUD operations.
     */
    private void manageFilms() {
        displayMessage("Film Management - Coming Soon with full CRUD");
    }
    
    /**
     * Manages inventory.
     */
    private void manageInventory() {
        displayMessage("Inventory Management - Coming Soon with stock tracking");
    }
    
    /**
     * Manages rentals.
     */
    private void manageRentals() {
        displayMessage("Rental Management - Coming Soon with transaction tracking");
    }
    
    /**
     * Manages payments.
     */
    private void managePayments() {
        displayMessage("Payment Management - Coming Soon with financial tracking");
    }
    
    /**
     * Manages customers.
     */
    private void manageCustomers() {
        displayMessage("Customer Management - Coming Soon");
    }
    
    /**
     * Manages stores.
     */
    private void manageStores() {
        displayMessage("Store Management - Coming Soon");
    }
    
    /**
     * Manages countries and cities.
     */
    private void manageCountriesCities() {
        displayMessage("Countries/Cities Management - Coming Soon");
    }
    
    /**
     * Displays reports and statistics menu.
     */
    private void viewReports() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("REPORTS & STATISTICS");
            System.out.println("=".repeat(70));
            System.out.println("1. Actor Statistics");
            System.out.println("2. Film Statistics");
            System.out.println("3. Rental Statistics");
            System.out.println("4. Payment Statistics");
            System.out.println("5. Inventory Summary");
            System.out.println("6. Export to CSV");
            System.out.println("7. Export to JSON");
            System.out.println("8. Back to Main Menu");
            System.out.println("=".repeat(70));
            
            System.out.print("Select report: ");
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    displayActorStats();
                    break;
                case "2":
                    displayFilmStats();
                    break;
                case "3":
                    displayRentalStats();
                    break;
                case "4":
                    displayPaymentStats();
                    break;
                case "5":
                    displayInventorySummary();
                    break;
                case "6":
                    displayMessage("CSV Export - Coming Soon");
                    break;
                case "7":
                    displayMessage("JSON Export - Coming Soon");
                    break;
                case "8":
                    back = true;
                    displayMainMenu();
                    break;
                default:
                    displayError("Invalid option");
            }
        }
    }
    
    /**
     * Displays actor statistics.
     */
    private void displayActorStats() {
        int totalActors = actorManager.count();
        ArrayList<Actor> actors = actorManager.getAll();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ACTOR STATISTICS");
        System.out.println("=".repeat(70));
        System.out.printf("Total Actors: %d%n", totalActors);
        System.out.printf("Average Films per Actor: %.2f%n", 
            actors.isEmpty() ? 0 : actors.stream()
                .mapToInt(a -> a.getFilmIds().size()).average().orElse(0));
        System.out.println("=".repeat(70));
    }
    
    /**
     * Displays film statistics.
     */
    private void displayFilmStats() {
        int totalFilms = filmManager.count();
        ArrayList<Film> films = filmManager.getAll();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("FILM STATISTICS");
        System.out.println("=".repeat(70));
        System.out.printf("Total Films: %d%n", totalFilms);
        if (!films.isEmpty()) {
            double avgRentalRate = films.stream()
                .mapToDouble(Film::getRentalRate).average().orElse(0);
            double avgLength = films.stream()
                .mapToInt(Film::getLength).average().orElse(0);
            System.out.printf("Average Rental Rate: $%.2f%n", avgRentalRate);
            System.out.printf("Average Film Length: %.0f minutes%n", avgLength);
        }
        System.out.println("=".repeat(70));
    }
    
    /**
     * Displays rental statistics.
     */
    private void displayRentalStats() {
        int totalRentals = rentalManager.count();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("RENTAL STATISTICS");
        System.out.println("=".repeat(70));
        System.out.printf("Total Rentals: %d%n", totalRentals);
        System.out.println("Overdue Rentals: (Coming Soon)");
        System.out.println("Total Rental Revenue: (Coming Soon)");
        System.out.println("=".repeat(70));
    }
    
    /**
     * Displays payment statistics.
     */
    private void displayPaymentStats() {
        int totalPayments = paymentManager.count();
        ArrayList<Payment> payments = paymentManager.getAll();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PAYMENT STATISTICS");
        System.out.println("=".repeat(70));
        System.out.printf("Total Payments: %d%n", totalPayments);
        if (!payments.isEmpty()) {
            double totalAmount = payments.stream()
                .mapToDouble(Payment::getAmount).sum();
            double avgAmount = payments.stream()
                .mapToDouble(Payment::getAmount).average().orElse(0);
            System.out.printf("Total Amount Collected: $%.2f%n", totalAmount);
            System.out.printf("Average Payment: $%.2f%n", avgAmount);
        }
        System.out.println("=".repeat(70));
    }
    
    /**
     * Displays inventory summary.
     */
    private void displayInventorySummary() {
        int totalItems = inventoryManager.count();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INVENTORY SUMMARY");
        System.out.println("=".repeat(70));
        System.out.printf("Total Inventory Items: %d%n", totalItems);
        System.out.println("(Detailed inventory metrics coming soon)");
        System.out.println("=".repeat(70));
    }
    
    /**
     * Displays a CRUD submenu.
     * 
     * @param entityName The name of the entity
     */
    private void displayCRUDMenu(String entityName) {
        System.out.println("\n" + "-".repeat(70));
        System.out.println(entityName + " CRUD OPERATIONS");
        System.out.println("-".repeat(70));
        System.out.println("1. CREATE (POST) - Add new record");
        System.out.println("2. READ (GET) - View all records");
        System.out.println("3. UPDATE (PUT) - Modify existing record");
        System.out.println("4. DELETE - Remove record");
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
        System.out.print("\n" + prompt + ": ");
        return scanner.nextLine().trim();
    }
    
    /**
     * Closes the console UI and releases resources.
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
        
        // Close all managers
        try {
            if (actorManager != null) actorManager.closeConnection();
            if (filmManager != null) filmManager.closeConnection();
            if (customerManager != null) customerManager.closeConnection();
            if (paymentManager != null) paymentManager.closeConnection();
            if (rentalManager != null) rentalManager.closeConnection();
        } catch (Exception e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("   Application closed. Thank you for using Sakila ORM/CRUD Manager!");
        System.out.println("=".repeat(70) + "\n");
    }
}
