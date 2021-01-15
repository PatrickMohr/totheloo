package com.example.tothelooclient;

public class MarkerLocation {
    private double latitude;
    private double longitude;
    private String title;

    public MarkerLocation (double Latitude, double Longitude, String Title)
    {
        latitude = Latitude;
        longitude = Longitude;
        title = Title;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTitle() {
        return title;
    }
}
