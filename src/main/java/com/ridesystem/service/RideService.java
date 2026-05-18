package com.ridesystem.service;

import com.ridesystem.model.*;
import com.ridesystem.strategy.FareStrategy;
import com.ridesystem.strategy.RideMatchingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RideService {
    private Map<String, Ride> rides;
    private Map<String, FareReceipt> fareReceipts;
    private RiderService riderService;
    private DriverService driverService;
    private RideMatchingStrategy rideMatchingStrategy;
    private FareStrategy fareStrategy;

    public RideService(RiderService riderService, DriverService driverService, 
                       RideMatchingStrategy rideMatchingStrategy, FareStrategy fareStrategy) {
        this.rides = new HashMap<>();
        this.fareReceipts = new HashMap<>();
        this.riderService = riderService;
        this.driverService = driverService;
        this.rideMatchingStrategy = rideMatchingStrategy;
        this.fareStrategy = fareStrategy;
    }

    public Ride requestRide(String riderId, double distance) {
        Rider rider = riderService.getRiderById(riderId);
        if (rider == null) {
            throw new IllegalArgumentException("Rider not found");
        }

        String rideId = UUID.randomUUID().toString();
        Ride ride = new Ride(rideId, rider);
        ride.setDistance(distance);
        rides.put(rideId, ride);

        return ride;
    }

    public Ride assignDriver(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("Ride not found");
        }

        if (ride.getStatus() != RideStatus.REQUESTED) {
            throw new IllegalStateException("Ride is not in REQUESTED status");
        }

        List<Driver> availableDrivers = driverService.getAvailableDrivers();
        if (availableDrivers.isEmpty()) {
            throw new IllegalStateException("No available drivers");
        }

        Driver driver = rideMatchingStrategy.findDriver(ride.getRider(), availableDrivers);
        if (driver == null) {
            throw new IllegalStateException("No driver found matching the strategy");
        }

        ride.setDriver(driver);
        ride.setStatus(RideStatus.ASSIGNED);
        driver.setAvailable(false);

        return ride;
    }

    public FareReceipt completeRide(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("Ride not found");
        }

        if (ride.getStatus() != RideStatus.ASSIGNED) {
            throw new IllegalStateException("Ride is not in ASSIGNED status");
        }

        double fare = fareStrategy.calculateFare(ride);
        FareReceipt receipt = new FareReceipt(rideId, fare);
        fareReceipts.put(rideId, receipt);

        ride.setStatus(RideStatus.COMPLETED);
        Driver driver = ride.getDriver();
        if (driver != null) {
            driver.setAvailable(true);
            driver.incrementCompletedRides();
        }

        return receipt;
    }

    public void cancelRide(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("Ride not found");
        }

        if (ride.getStatus() == RideStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed ride");
        }

        ride.setStatus(RideStatus.CANCELLED);
        
        Driver driver = ride.getDriver();
        if (driver != null) {
            driver.setAvailable(true);
        }
    }

    public Ride getRideById(String rideId) {
        return rides.get(rideId);
    }

    public List<Ride> getAllRides() {
        return new ArrayList<>(rides.values());
    }

    public FareReceipt getFareReceipt(String rideId) {
        return fareReceipts.get(rideId);
    }
}
