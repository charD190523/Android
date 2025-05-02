package com.example.cinemaapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cinemaapp.R;
import com.example.cinemaapp.Schedule.ThongBaoActivity;
import com.example.cinemaapp.Schedule.ViewPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ScheduleFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private ImageView menuIcon;
    private ImageView notificationIcon;
    private TextView titleTextView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private NavigationView navigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout cho Fragment
        View view = inflater.inflate(R.layout.activity_schedule, container, false);

        // Ánh xạ các thành phần giao diện
        drawerLayout = requireActivity().findViewById(R.id.drawer_layout);
        menuIcon = view.findViewById(R.id.menuIcon);
        notificationIcon = view.findViewById(R.id.notificationIcon);
        titleTextView = view.findViewById(R.id.titleTextView);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        navigationView = requireActivity().findViewById(R.id.nav_view);

        // Xử lý sự kiện click cho menu icon
        menuIcon.setOnClickListener(v -> drawerLayout.openDrawer(navigationView));

        // Xử lý sự kiện click cho notification icon
        notificationIcon.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ThongBaoActivity.class);
            startActivity(intent);
        });

        // Thiết lập ViewPager2
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Thiết lập TabLayout với ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("TPHCM");
                    break;
                case 1:
                    tab.setText("Hà Nội");
                    break;
                case 2:
                    tab.setText("Huế");
                    break;
            }
        }).attach();

        return view;
    }
}