package com.ridesystem.strategy;

import com.ridesystem.model.Driver;
import com.ridesystem.model.Rider;

import java.util.List;

public interface RideMatchingStrategy {
    Driver findDriver(Rider rider, List<Driver> drivers);
}
