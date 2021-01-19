package com.example.tothelooclient;

import android.graphics.Color;

public class MarkerLocation {
    private double latitude;
    private double longitude;
    private String title;
    private int id;
    private String navi;
    private Color tag;

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
