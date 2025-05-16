package com.example.cinemaapp.dto;

import java.util.List;

public class MovieShowDTO {
    private Integer id;
    private String imageUrl;
    private String movieName;
    private List<ShowtimeDTO> showtimes;

    public MovieShowDTO(Integer id, String imageUrl, String movieName, List<ShowtimeDTO> showtimes) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.movieName = movieName;
        this.showtimes = showtimes;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
