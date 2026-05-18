package com.ridesystem.strategy;

import com.ridesystem.model.Driver;
import com.ridesystem.model.Rider;

import java.util.List;

public class NearestDriverStrategy implements RideMatchingStrategy {
    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        Driver nearestDriver = null;
        double minDistance = Double.MAX_VALUE;

        for (Driver driver : drivers) {
            if (driver.isAvailable()) {
                double distance = calculateDistance(rider.getLocation(), driver.getCurrentLocation());
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestDriver = driver;
                }
            }
        }

        return nearestDriver;
    }

    private double calculateDistance(String location1, String location2) {
        // Simplified distance calculation based on location string
        // In a real system, this would use GPS coordinates
        return Math.abs(location1.hashCode() - location2.hashCode()) % 100;
    }
}
