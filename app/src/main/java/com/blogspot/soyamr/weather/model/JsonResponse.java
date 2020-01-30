package com.blogspot.soyamr.weather.model;

import com.blogspot.soyamr.weather.model.Current;
import com.blogspot.soyamr.weather.model.Location;

public class JsonResponse {
    private Current current;
    private Location location;

    public Current getCurrent() {
        return current;
    }

    public Location getLocation() {
        return location;
    }
}
