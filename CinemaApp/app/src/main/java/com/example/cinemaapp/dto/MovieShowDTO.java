package com.example.cinemaapp.dto;

import java.util.List;

public class MovieShowDTO {
    private Integer id;
    private String movieName;
    private List<ShowtimeDTO> showtimes;

    public MovieShowDTO(Integer id, String movieName, List<ShowtimeDTO> showtimes) {
        this.id = id;
        this.movieName = movieName;
        this.showtimes = showtimes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public List<ShowtimeDTO> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<ShowtimeDTO> showtimes) {
        this.showtimes = showtimes;
    }
}
