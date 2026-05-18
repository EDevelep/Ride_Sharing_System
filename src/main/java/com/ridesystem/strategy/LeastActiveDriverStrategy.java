package com.ridesystem.strategy;

import com.ridesystem.model.Driver;
import com.ridesystem.model.Rider;

import java.util.List;

public class LeastActiveDriverStrategy implements RideMatchingStrategy {
    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        Driver leastActiveDriver = null;
        int minRides = Integer.MAX_VALUE;

        for (Driver driver : drivers) {
            if (driver.isAvailable()) {
                if (driver.getCompletedRides() < minRides) {
                    minRides = driver.getCompletedRides();
                    leastActiveDriver = driver;
                }
            }
        }

        return leastActiveDriver;
    }
}
