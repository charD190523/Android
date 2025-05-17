package com.example.cinemaapp.Schedule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPagerAdapterForActivity extends FragmentStateAdapter {
    private ArrayList<String> GioChieu;
    private String tenPhim;
    private int idPhim;
    private String imageUrl;
    private String ngayChieu;

    // Thêm các tham số cần thiết vào constructor
    public ViewPagerAdapterForActivity(@NonNull ShowtimeHomeActivity fa,
                                       ArrayList<String> GioChieu,
                                       String tenPhim,
                                       int idPhim,
                                       String imageUrl,
                                       String ngayChieu) {
        super(fa);
        this.GioChieu = GioChieu;
        this.tenPhim = tenPhim;
        this.idPhim = idPhim;
        this.imageUrl = imageUrl;
        this.ngayChieu = ngayChieu;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new TPHCMFragment1();
                break;
            case 1:
                fragment = new HanoiFragment1();
                break;
            case 2:
                fragment = new HueFragment1();
                break;
            default:
                fragment = new TPHCMFragment1();
                break;
        }

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("gioChieuList", GioChieu);

        // Đẩy thêm các thuộc tính khác vào bundle
        bundle.putString("tenPhim", tenPhim);
        bundle.putInt("idPhim", idPhim);
        bundle.putString("imageUrl", imageUrl);
        bundle.putString("ngayChieu", ngayChieu);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
