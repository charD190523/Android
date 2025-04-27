package com.example.cinemaapp.model;

import java.util.List;

public class LichChieuPhimItem {
    private String tenPhim;
    private List<String> gioChieu;

    public LichChieuPhimItem(String tenPhim, List<String> gioChieu) {
        this.tenPhim = tenPhim;
        this.gioChieu = gioChieu;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public List<String> getGioChieu() {
        return gioChieu;
    }
}