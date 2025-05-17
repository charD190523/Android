package com.example.cinemaapp.model;

import com.example.cinemaapp.dto.ShowTime;

import java.util.ArrayList;
import java.util.List;

public class Theater1 {
    private String name;
    private Integer movieId;
    private String movieName;
    private String movieImageUrl;
    private String date;
    private ArrayList<String> showTimes;

    public Theater1(String name, Integer movieId, String movieName, String movieImageUrl, String date, ArrayList<String> showTimes) {
        this.name = name;
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieImageUrl = movieImageUrl;
        this.date = date;
        this.showTimes = showTimes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieImageUrl() {
        return movieImageUrl;
    }

    public void setMovieImageUrl(String movieImageUrl) {
        this.movieImageUrl = movieImageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(ArrayList<String> showTimes) {
        this.showTimes = showTimes;
    }
}