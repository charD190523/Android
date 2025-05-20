package com.example.cinemaapp.model;


// app/src/main/java/your/package/name/TransactionItem.java

public class TransactionItem {

    private Integer id;
    private String movieTitle;
    private String dateTime;
    private String price;
    private String cinemaName;

    public TransactionItem(Integer id, String movieTitle, String dateTime, String price, String cinemaName) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.dateTime = dateTime;
        this.price = price;
        this.cinemaName = cinemaName;
    }

    public TransactionItem(String movieTitle, String dateTime, String price, String cinemaName) {
        this.movieTitle = movieTitle;
        this.dateTime = dateTime;
        this.price = price;
        this.cinemaName = cinemaName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getPrice() {
        return price;
    }

    public String getCinemaName() {
        return cinemaName;
    }
}