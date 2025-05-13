package com.example.cinemaapp.dto;

import com.example.cinemaapp.model.Movie;

import java.util.List;

public class MovieResponse {
    private List<Movie> data;

    public List<Movie> getData() {
        return data;
    }
}