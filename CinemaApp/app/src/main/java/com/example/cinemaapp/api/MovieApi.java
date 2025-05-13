package com.example.cinemaapp.api;

import com.example.cinemaapp.dto.MovieResponse;
import com.example.cinemaapp.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface MovieApi {
    @GET("/api/movie/getAll")  // Thay thế bằng URL thật của backend
    Call<MovieResponse> getMovies();
}
