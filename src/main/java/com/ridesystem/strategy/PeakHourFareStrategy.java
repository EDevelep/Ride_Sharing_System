package com.ridesystem.strategy;

import com.ridesystem.model.Ride;

import java.time.LocalTime;

public class PeakHourFareStrategy implements FareStrategy {
    private static final double BASE_FARE = 5.0;
    private static final double PER_KM_RATE = 2.0;
    private static final double PEAK_MULTIPLIER = 1.5;
    private static final int PEAK_START = 17; // 5 PM
    private static final int PEAK_END = 19;   // 7 PM

    @Override
    public double calculateFare(Ride ride) {
        double baseFare = BASE_FARE + (ride.getDistance() * PER_KM_RATE);
        
        if (isPeakHour()) {
            baseFare *= PEAK_MULTIPLIER;
        }
        
        return baseFare;
    }

    private boolean isPeakHour() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        return hour >= PEAK_START && hour < PEAK_END;
    }
}
