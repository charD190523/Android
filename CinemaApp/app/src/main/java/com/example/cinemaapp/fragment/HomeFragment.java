package com.example.cinemaapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.BannerAdapter;
import com.example.cinemaapp.adapter.MovieTabAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager2 bannerViewPager;
    private TabLayout bannerIndicator;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private MovieTabAdapter movieTabAdapter;

    private Handler bannerHandler = new Handler();
    private Runnable bannerRunnable;
    private int currentBannerIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Banner setup
        bannerViewPager = view.findViewById(R.id.banner_viewpager);

        List<Integer> bannerImages = Arrays.asList(
                R.drawable.banner_panor,
                R.drawable.banner_latmat8,
                R.drawable.banner_minecraft
        );

        BannerAdapter bannerAdapter = new BannerAdapter(requireContext(), bannerImages);
        bannerViewPager.setAdapter(bannerAdapter);

        // Tự động trượt banner
        bannerRunnable = new Runnable() {
            @Override
            public void run() {
                if (bannerAdapter.getItemCount() > 0) {
                    currentBannerIndex = (bannerViewPager.getCurrentItem() + 1) % bannerAdapter.getItemCount();
                    bannerViewPager.setCurrentItem(currentBannerIndex, true);
                    bannerHandler.postDelayed(this, 3000);
                }
            }
        };
        bannerHandler.postDelayed(bannerRunnable, 3000);

        // Tabs "Đang Chiếu / Sắp Chiếu"
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewPagerMovieTabs);

        movieTabAdapter = new MovieTabAdapter(requireActivity());
        viewPager2.setAdapter(movieTabAdapter);

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(position == 0 ? "Đang Chiếu" : "Sắp Chiếu")
        ).attach();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bannerHandler.removeCallbacks(bannerRunnable);
    }
}


