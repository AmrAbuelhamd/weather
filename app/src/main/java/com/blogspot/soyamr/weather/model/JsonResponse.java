package com.blogspot.soyamr.weather.model;

public class JsonResponse {
    private Current current;
    private Location location;
    boolean success;
    private Error error;

    public Error getError() {
        return error;
    }

    public boolean isSuccess() {
        return success;
    }

    public Current getCurrent() {
        return current;
    }

    public Location getLocation() {
        return location;
    }
}
