package com.ridesystem.service;

import com.ridesystem.model.Driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverService {
    private Map<String, Driver> drivers;

    public DriverService() {
        this.drivers = new HashMap<>();
    }

    public void registerDriver(String id, String name, String currentLocation) {
        Driver driver = new Driver(id, name, currentLocation);
        drivers.put(id, driver);
    }

    public Driver getDriverById(String id) {
        return drivers.get(id);
    }

    public void updateAvailability(String driverId, boolean available) {
        Driver driver = drivers.get(driverId);
        if (driver != null) {
            driver.setAvailable(available);
        }
    }

    public List<Driver> getAvailableDrivers() {
        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : drivers.values()) {
            if (driver.isAvailable()) {
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }

    public Map<String, Driver> getAllDrivers() {
        return new HashMap<>(drivers);
    }
}
