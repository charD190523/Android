package com.example.cinemaapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cinemaapp.fragment.LichChieuHaNoiFragment;
import com.example.cinemaapp.fragment.LichChieuHueFragment;
import com.example.cinemaapp.fragment.LichChieuTPHCMFragment;

public class LichPagerAdapter extends FragmentStateAdapter {

    public LichPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new LichChieuTPHCMFragment();
            case 1:
                return new LichChieuHaNoiFragment();
            case 2:
                return new LichChieuHueFragment();
            default:
                return new LichChieuTPHCMFragment(); // Mặc định
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Số lượng tab
    }
}

