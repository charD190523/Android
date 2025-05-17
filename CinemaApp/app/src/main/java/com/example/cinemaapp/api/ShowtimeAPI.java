package com.example.cinemaapp.api;

import com.example.cinemaapp.dto.MovieShowDTO;
import com.example.cinemaapp.factory.GeneralResponse;

import java.time.LocalDate;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ShowtimeAPI {
    @GET("api/movie/find-all-by-date")
    Call<GeneralResponse<List<MovieShowDTO>>> getMovieShowtimes(@Query("showDate") LocalDate showDate);
    @GET("api/movie/find-by-date")
    Call<GeneralResponse<MovieShowDTO>> findByDate(@Query("movieId") Integer movieId,
                                                     @Query("showDate") LocalDate showDate);

}
