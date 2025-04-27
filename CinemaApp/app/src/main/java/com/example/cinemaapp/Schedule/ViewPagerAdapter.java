package com.example.cinemaapp.Schedule;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cinemaapp.fragment.ScheduleFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull ScheduleFragment fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new TPHCMFragment();
            case 1: return new HanoiFragment();
            case 2: return new HueFragment();
            default: return new TPHCMFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

