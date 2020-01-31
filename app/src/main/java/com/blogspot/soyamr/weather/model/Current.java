package com.blogspot.soyamr.weather.model;

public class Current {

    private int temperature;
    private int wind_speed;
    private int feelslike;
    private String[] weather_descriptions;
    private String observation_time;
    private String weather_icons[];
    private String pressure;
    private String humidity;
    private String visibility;
    private String precip;
    private String is_day;    //possible values: "yes", "no"

    public int getTemperature() {
        return temperature;
    }

    public int getWind_speed() {
        return wind_speed;
    }

    public int getFeelslike() {
        return feelslike;
    }

    public String[] getWeather_descriptions() {
        return weather_descriptions;
    }

    public String getObservation_time() {
        return observation_time;
    }

    public String[] getWeather_Icons() {
        return weather_icons;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getPrecip() {
        return precip;
    }

    public String getIs_day() {
        return is_day;
    }
}
