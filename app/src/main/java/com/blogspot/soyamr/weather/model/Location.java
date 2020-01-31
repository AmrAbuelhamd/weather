package com.blogspot.soyamr.weather.model;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("name")
    private String cityName;

    @SerializedName("country")
    private String countryName;

    private String localtime;

    public String getCityName() {
        return cityName;
    }

    public String getCountryName() {
        return countryName;
    }


    public String getLocaltime() {
        return localtime;
    }

}
