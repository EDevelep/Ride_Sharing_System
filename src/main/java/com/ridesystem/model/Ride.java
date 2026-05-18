package com.ridesystem.model;

public class Ride {
    private String id;
    private Rider rider;
    private Driver driver;
    private double distance;
    private RideStatus status;

    public Ride(String id, Rider rider) {
        this.id = id;
        this.rider = rider;
        this.status = RideStatus.REQUESTED;
    }

    public String getId() {
        return id;
    }

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ride{id='" + id + "', rider=" + rider.getName() + ", driver=" + (driver != null ? driver.getName() : "null") + ", distance=" + distance + ", status=" + status + "}";
    }
}
