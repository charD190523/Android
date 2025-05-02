package com.example.cinemaapp.model;

public class Theater {
    public String name;
    public String address;
    public int imageResource; // Ảnh thumbnail
    public int coverImageResource; // Ảnh bìa

    public Theater(String name, String address, int imageResource, int coverImageResource) {
        this.name = name;
        this.address = address;
        this.imageResource = imageResource;
        this.coverImageResource = coverImageResource;
    }
}