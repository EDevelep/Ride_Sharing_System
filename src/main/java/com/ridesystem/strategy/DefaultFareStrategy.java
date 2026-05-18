package com.ridesystem.strategy;

import com.ridesystem.model.Ride;

public class DefaultFareStrategy implements FareStrategy {
    private static final double BASE_FARE = 5.0;
    private static final double PER_KM_RATE = 2.0;

    @Override
    public double calculateFare(Ride ride) {
        return BASE_FARE + (ride.getDistance() * PER_KM_RATE);
    }
}
