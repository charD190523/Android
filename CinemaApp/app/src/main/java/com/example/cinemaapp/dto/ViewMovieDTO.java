package com.example.cinemaapp.dto;

import java.time.LocalTime;

public class ViewMovieDTO {
    private Integer id;
    private String imageUrl;
    private String movieName;
    private String duration;
    private Integer requiredAge;
    private Boolean isAvailable;

    public ViewMovieDTO(Integer id, String imageUrl, String movieName, String duration, Integer requiredAge, Boolean isAvailable) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.movieName = movieName;
        this.duration = duration;
        this.requiredAge = requiredAge;
        this.isAvailable = isAvailable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getRequiredAge() {
        return requiredAge;
    }

    public void setRequiredAge(Integer requiredAge) {
        this.requiredAge = requiredAge;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
