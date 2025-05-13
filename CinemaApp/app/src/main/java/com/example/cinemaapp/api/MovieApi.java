package com.example.cinemaapp.api;

import com.example.cinemaapp.dto.MovieDetailDTO;
import com.example.cinemaapp.dto.MovieResponse;
import com.example.cinemaapp.factory.GeneralResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieApi {
    @GET("/api/movie/getAll")  // Thay thế bằng URL thật của backend
    Call<MovieResponse> getMovies();
    @GET("api/movie/get-detail")
    Call<GeneralResponse<MovieDetailDTO>> sendMovieId(@Query("movieId")Integer movieId);
}
