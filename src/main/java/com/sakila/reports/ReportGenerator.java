/*
 * Copyright (c) 2026 Sakila ORM/CRUD Manager
 * Author: kmoquetemercado-collab
 * Purpose: Report generator for statistics and export functionality
 */

package com.sakila.reports;

import com.sakila.controllers.*;
import com.sakila.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Report generator for creating statistical reports and exporting data.
 * Supports CSV and JSON export formats.
 * 
 * @author kmoquetemercado-collab
 * @version 1.0
 * @since 2026-05-15
 */
public class ReportGenerator {
    
    private ActorManager actorManager;
    private FilmManager filmManager;
    private CustomerManager customerManager;
    private PaymentManager paymentManager;
    private RentalManager rentalManager;
    private InventoryManager inventoryManager;
    private StoreManager storeManager;
    private Gson gson;
    
    /**
     * Constructor initializing the report generator.
     * 
     * @throws SQLException if database connection fails
     */
    public ReportGenerator() throws SQLException {
        this.actorManager = new ActorManager();
        this.filmManager = new FilmManager();
        this.customerManager = new CustomerManager();
        this.paymentManager = new PaymentManager();
        this.rentalManager = new RentalManager();
        this.inventoryManager = new InventoryManager();
        this.storeManager = new StoreManager();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    /**
     * Generates a summary report of all entities.
     * 
     * @return A map containing summary statistics
     */
    public Map<String, Object> generateSummaryReport() {
        Map<String, Object> summary = new HashMap<>();
        
        try {
            summary.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            summary.put("totalActors", actorManager.count());
            summary.put("totalFilms", filmManager.count());
            summary.put("totalCustomers", customerManager.count());
            summary.put("totalPayments", paymentManager.count());
            summary.put("totalRentals", rentalManager.count());
            summary.put("totalInventoryItems", inventoryManager.count());
            
            // Calculate financial metrics
            ArrayList<Payment> allPayments = paymentManager.getAll();
            double totalRevenue = allPayments.stream()
                .mapToDouble(Payment::getAmount).sum();
            double avgPayment = allPayments.isEmpty() ? 0 : 
                allPayments.stream().mapToDouble(Payment::getAmount).average().orElse(0);
            
            summary.put("totalRevenue", totalRevenue);
            summary.put("averagePayment", avgPayment);
            
        } catch (SQLException e) {
            System.err.println("Error generating summary: " + e.getMessage());
        }
        
        return summary;
    }
    
    /**
     * Exports actors to CSV file.
     * 
     * @param filename The output CSV filename
     * @throws IOException if file write fails
     * @throws SQLException if database error occurs
     */
    public void exportActorsToCSV(String filename) throws IOException, SQLException {
        ArrayList<Actor> actors = actorManager.getAll();
        
        try (FileWriter writer = new FileWriter(filename);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                 .withHeader("ID", "First Name", "Last Name", "Film Count", "Active"))) {
            
            for (Actor actor : actors) {
                csvPrinter.printRecord(
                    actor.getId(),
                    actor.getFirstName(),
                    actor.getLastName(),
                    actor.getFilmIds().size(),
                    actor.getActive()
                );
            }
            csvPrinter.flush();
            System.out.println("✓ Actors exported to " + filename);
        }
    }
    
    /**
     * Exports films to CSV file.
     * 
     * @param filename The output CSV filename
     * @throws IOException if file write fails
     * @throws SQLException if database error occurs
     */
    public void exportFilmsToCSV(String filename) throws IOException, SQLException {
        ArrayList<Film> films = filmManager.getAll();
        
        try (FileWriter writer = new FileWriter(filename);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                 .withHeader("ID", "Title", "Rating", "Length", "Rental Rate", "Replacement Cost", "Active"))) {
            
            for (Film film : films) {
                csvPrinter.printRecord(
                    film.getId(),
                    film.getTitle(),
                    film.getRating(),
                    film.getLength(),
                    film.getRentalRate(),
                    film.getReplacementCost(),
                    film.getActive()
                );
            }
            csvPrinter.flush();
            System.out.println("✓ Films exported to " + filename);
        }
    }
    
    /**
     * Exports customers to CSV file.
     * 
     * @param filename The output CSV filename
     * @throws IOException if file write fails
     * @throws SQLException if database error occurs
     */
    public void exportCustomersToCSV(String filename) throws IOException, SQLException {
        ArrayList<Customer> customers = customerManager.getAll();
        
        try (FileWriter writer = new FileWriter(filename);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                 .withHeader("ID", "First Name", "Last Name", "Email", "Total Rentals", "Active"))) {
            
            for (Customer customer : customers) {
                csvPrinter.printRecord(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getTotalRentals(),
                    customer.getActive()
                );
            }
            csvPrinter.flush();
            System.out.println("✓ Customers exported to " + filename);
        }
    }
    
    /**
     * Exports payments to CSV file.
     * 
     * @param filename The output CSV filename
     * @throws IOException if file write fails
     * @throws SQLException if database error occurs
     */
    public void exportPaymentsToCSV(String filename) throws IOException, SQLException {
        ArrayList<Payment> payments = paymentManager.getAll();
        
        try (FileWriter writer = new FileWriter(filename);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                 .withHeader("ID", "Amount", "Payment Date", "Method", "Status", "Active"))) {
            
            for (Payment payment : payments) {
                csvPrinter.printRecord(
                    payment.getId(),
                    payment.getAmount(),
                    payment.getPaymentDate(),
                    payment.getPaymentMethod(),
                    payment.getStatus(),
                    payment.getActive()
                );
            }
            csvPrinter.flush();
            System.out.println("✓ Payments exported to " + filename);
        }
    }
    
    /**
     * Exports actors to JSON file.
     * 
     * @param filename The output JSON filename
     * @throws IOException if file write fails
     * @throws SQLException if database error occurs
     */
    public void exportActorsToJSON(String filename) throws IOException, SQLException {
        ArrayList<Actor> actors = actorManager.getAll();
        
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(actors, writer);
            System.out.println("✓ Actors exported to " + filename);
        }
    }
    
    /**
     * Exports films to JSON file.
     * 
     * @param filename The output JSON filename
     * @throws IOException if file write fails
     * @throws SQLException if database error occurs
     */
    public void exportFilmsToJSON(String filename) throws IOException, SQLException {
        ArrayList<Film> films = filmManager.getAll();
        
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(films, writer);
            System.out.println("✓ Films exported to " + filename);
        }
    }
    
    /**
     * Exports customers to JSON file.
     * 
     * @param filename The output JSON filename
     * @throws IOException if file write fails
     * @throws SQLException if database error occurs
     */
    public void exportCustomersToJSON(String filename) throws IOException, SQLException {
        ArrayList<Customer> customers = customerManager.getAll();
        
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(customers, writer);
            System.out.println("✓ Customers exported to " + filename);
        }
    }
    
    /**
     * Exports summary report to JSON file.
     * 
     * @param filename The output JSON filename
     * @throws IOException if file write fails
     */
    public void exportSummaryToJSON(String filename) throws IOException {
        Map<String, Object> summary = generateSummaryReport();
        
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(summary, writer);
            System.out.println("✓ Summary report exported to " + filename);
        }
    }
    
    /**
     * Generates film inventory aging report.
     * 
     * @return List of films with rental counts
     * @throws SQLException if database error occurs
     */
    public ArrayList<Map<String, Object>> generateFilmInventoryReport() throws SQLException {
        ArrayList<Map<String, Object>> report = new ArrayList<>();
        ArrayList<Film> films = filmManager.getAll();
        
        for (Film film : films) {
            Map<String, Object> filmData = new HashMap<>();
            filmData.put("filmId", film.getId());
            filmData.put("title", film.getTitle());
            filmData.put("rentalRate", film.getRentalRate());
            filmData.put("actorCount", film.getActorIds().size());
            // TODO: Add rental count and revenue
            report.add(filmData);
        }
        
        return report;
    }
    
    /**
     * Generates rental statistics by store.
     * 
     * @return Map of store statistics
     * @throws SQLException if database error occurs
     */
    public Map<Integer, Map<String, Object>> generateRentalStatsByStore() throws SQLException {
        Map<Integer, Map<String, Object>> storeStats = new HashMap<>();
        ArrayList<Store> stores = storeManager.getAll();
        
        for (Store store : stores) {
            Map<String, Object> stats = new HashMap<>();
            stats.put("storeId", store.getId());
            stats.put("revenue", storeManager.getStoreRevenue(store.getId()));
            stats.put("inventoryValue", inventoryManager.getStoreInventoryValue(store.getId()));
            storeStats.put(store.getId(), stats);
        }
        
        return storeStats;
    }
    
    /**
     * Closes all database connections.
     */
    public void close() {
        try {
            actorManager.closeConnection();
            filmManager.closeConnection();
            customerManager.closeConnection();
            paymentManager.closeConnection();
            rentalManager.closeConnection();
            inventoryManager.closeConnection();
            storeManager.closeConnection();
        } catch (Exception e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }
}
