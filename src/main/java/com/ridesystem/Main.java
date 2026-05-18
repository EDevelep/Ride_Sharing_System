package com.ridesystem;

import com.ridesystem.model.Ride;
import com.ridesystem.service.DriverService;
import com.ridesystem.service.RiderService;
import com.ridesystem.service.RideService;
import com.ridesystem.strategy.DefaultFareStrategy;
import com.ridesystem.strategy.NearestDriverStrategy;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RiderService riderService = new RiderService();
    private static final DriverService driverService = new DriverService();
    private static final RideService rideService = new RideService(
            riderService,
            driverService,
            new NearestDriverStrategy(),
            new DefaultFareStrategy()
    );

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addRider();
                    break;
                case 2:
                    addDriver();
                    break;
                case 3:
                    viewAvailableDrivers();
                    break;
                case 4:
                    requestRide();
                    break;
                case 5:
                    completeRide();
                    break;
                case 6:
                    viewRides();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Ride Sharing System ===");
        System.out.println("1. Add Rider");
        System.out.println("2. Add Driver");
        System.out.println("3. View Available Drivers");
        System.out.println("4. Request Ride");
        System.out.println("5. Complete Ride");
        System.out.println("6. View Rides");
        System.out.println("7. Exit");
    }

    private static void addRider() {
        System.out.println("\n--- Add Rider ---");
        String id = getStringInput("Enter rider ID: ");
        String name = getStringInput("Enter rider name: ");
        String location = getStringInput("Enter rider location: ");

        try {
            riderService.registerRider(id, name, location);
            System.out.println("Rider added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding rider: " + e.getMessage());
        }
    }

    private static void addDriver() {
        System.out.println("\n--- Add Driver ---");
        String id = getStringInput("Enter driver ID: ");
        String name = getStringInput("Enter driver name: ");
        String location = getStringInput("Enter driver current location: ");

        try {
            driverService.registerDriver(id, name, location);
            System.out.println("Driver added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding driver: " + e.getMessage());
        }
    }

    private static void viewAvailableDrivers() {
        System.out.println("\n--- Available Drivers ---");
        var availableDrivers = driverService.getAvailableDrivers();
        
        if (availableDrivers.isEmpty()) {
            System.out.println("No available drivers.");
        } else {
            for (var driver : availableDrivers) {
                System.out.println(driver);
            }
        }
    }

    private static void requestRide() {
        System.out.println("\n--- Request Ride ---");
        String riderId = getStringInput("Enter rider ID: ");
        double distance = getDoubleInput("Enter distance (in km): ");

        try {
            Ride ride = rideService.requestRide(riderId, distance);
            System.out.println("Ride requested successfully!");
            System.out.println("Ride ID: " + ride.getId());
            System.out.println("Status: " + ride.getStatus());

            System.out.print("Assign driver now? (y/n): ");
            String assignChoice = scanner.nextLine().trim().toLowerCase();
            
            if (assignChoice.equals("y")) {
                Ride assignedRide = rideService.assignDriver(ride.getId());
                System.out.println("Driver assigned successfully!");
                System.out.println("Driver: " + assignedRide.getDriver().getName());
                System.out.println("Status: " + assignedRide.getStatus());
            }
        } catch (Exception e) {
            System.out.println("Error requesting ride: " + e.getMessage());
        }
    }

    private static void completeRide() {
        System.out.println("\n--- Complete Ride ---");
        String rideId = getStringInput("Enter ride ID: ");

        try {
            var receipt = rideService.completeRide(rideId);
            System.out.println("Ride completed successfully!");
            System.out.println(receipt);
        } catch (Exception e) {
            System.out.println("Error completing ride: " + e.getMessage());
        }
    }

    private static void viewRides() {
        System.out.println("\n--- All Rides ---");
        var rides = rideService.getAllRides();
        
        if (rides.isEmpty()) {
            System.out.println("No rides found.");
        } else {
            for (var ride : rides) {
                System.out.println(ride);
                var receipt = rideService.getFareReceipt(ride.getId());
                if (receipt != null) {
                    System.out.println("  Receipt: " + receipt);
                }
            }
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value < 0) {
                    System.out.println("Distance cannot be negative. Please enter a valid value.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
