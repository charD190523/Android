package com.example.cinemaapp.model;

import java.sql.Time;

public class Movie {
    private String imageUrl;
    private String movieName;
    private String duration;
    private int requiredAge;
    private boolean isAvailable;

    // Getters
    public String getImage_url() {
        return imageUrl;
    }

    public String getMovie_name() {
        return movieName;
    }

    public String getDuration() {
        return duration;
    }

    public int getRequired_age() {
        return requiredAge;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
