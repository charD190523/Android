package com.example.cinemaapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cinemaapp.fragment.ComingSoonFragment;
import com.example.cinemaapp.fragment.NowShowingFragment;

public class MovieTabAdapter extends FragmentStateAdapter {

    public MovieTabAdapter(@NonNull FragmentActivity activity) {
        super(activity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new NowShowingFragment();
        } else {
            return new ComingSoonFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Đang Chiếu và Sắp Chiếu
    }
}

