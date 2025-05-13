package com.example.cinemaapp.dto;

public class MovieDetailDTO {
    private String genre;
    private String description;
    private String director;
    private String actor;

    public MovieDetailDTO(String genre, String description, String director, String actor) {
        this.genre = genre;
        this.description = description;
        this.director = director;
        this.actor = actor;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
