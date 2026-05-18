package com.ridesystem.strategy;

import com.ridesystem.model.Ride;

public interface FareStrategy {
    double calculateFare(Ride ride);
}
