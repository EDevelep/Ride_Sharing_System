package com.ridesystem.service;

import com.ridesystem.model.Rider;

import java.util.HashMap;
import java.util.Map;

public class RiderService {
    private Map<String, Rider> riders;

    public RiderService() {
        this.riders = new HashMap<>();
    }

    public void registerRider(String id, String name, String location) {
        Rider rider = new Rider(id, name, location);
        riders.put(id, rider);
    }

    public Rider getRiderById(String id) {
        return riders.get(id);
    }

    public Map<String, Rider> getAllRiders() {
        return new HashMap<>(riders);
    }
}
