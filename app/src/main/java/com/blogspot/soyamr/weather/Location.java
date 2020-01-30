package com.blogspot.soyamr.weather;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("name")
    private String cityName;

    @SerializedName("country")
    private String countryName;

    private String timezone_id;

    private String localtime_epoch;

    private String utc_offset;

    public String getCityName() {
        return cityName;
    }

    public String getCountryName() {
        return countryName;
    }


    public String getTimezone_id() {
        return timezone_id;
    }

    public String getLocaltime_epoch() {
        return localtime_epoch;
    }

    public String getUtc_offset() {
        return utc_offset;
    }


}
