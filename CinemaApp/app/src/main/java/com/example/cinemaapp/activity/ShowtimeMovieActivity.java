package com.example.cinemaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cinemaapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cinemaapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// 1. Activity: BookTicketFilmActivity
// Layout: activity_book_ticket_film.xml
public class ShowtimeMovieActivity extends AppCompatActivity {

    ImageView phimCoverImageView;
    TextView tenPhimTextView;
    LinearLayout ngayChieuLinearLayout;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    ImageView btnBackLichChieu;
    private List<LocalDate> danhSachNgayChieu;
    private LocalDate selectedDate = LocalDate.now();
    private List<TextView> dayTextViews = new ArrayList<>();
    private DateTimeFormatter dateFormatterForIntent = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket_film);

        // Khởi tạo các view
        phimCoverImageView = findViewById(R.id.phimCoverImageView);
        tenPhimTextView = findViewById(R.id.tenPhimTextView);
        ngayChieuLinearLayout = findViewById(R.id.ngayChieuLinearLayout);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        btnBackLichChieu = findViewById(R.id.btnBackLichChieu);

        // Lấy dữ liệu phim từ Intent
        Intent intent = getIntent();
        String tenPhim = intent.getStringExtra("tenPhim");
        int anhBiaPhim = intent.getIntExtra("anhBiaPhim", R.drawable.a_minecraft_movie);  // Đặt ảnh mặc định nếu không có

        tenPhimTextView.setText(tenPhim);
        phimCoverImageView.setImageResource(anhBiaPhim);

        // Thiết lập lựa chọn ngày
        setupDateSelection();

        // Thiết lập ViewPager và TabLayout
        setupViewPager();

        btnBackLichChieu.setOnClickListener(v -> finish());
    }

    private void setupDateSelection() {
        danhSachNgayChieu = generateDates();
        populateNgayChieu();
    }

    private List<LocalDate> generateDates() {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            dates.add(currentDate.plusDays(i));
            currentDate = currentDate.plusDays(1);
        }
        return dates;
    }

    private void populateNgayChieu() {
        ngayChieuLinearLayout.removeAllViews();
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("E");

        for (int i = 0; i < danhSachNgayChieu.size(); i++) {
            LocalDate date = danhSachNgayChieu.get(i);
            View dayView = LayoutInflater.from(this).inflate(R.layout.item_ngay_chieu, ngayChieuLinearLayout, false);
            TextView ngayTextView = dayView.findViewById(R.id.ngayTextView);
            ngayTextView.setText(String.format("%s\n%s", dayFormatter.format(date), dayOfWeekFormatter.format(date)));
            dayTextViews.add(ngayTextView);

            final LocalDate selectedDateFinal = date; // Tạo bản sao final cho listener
            ngayTextView.setOnClickListener(v -> {
                selectedDate = selectedDateFinal;
                updateSelectedDay();
                // Thông báo cho các fragment về thay đổi ngày
                updateViewPagerFragments();
            });
            ngayChieuLinearLayout.addView(dayView);
        }
        updateSelectedDay();
    }

    private void updateSelectedDay() {
        for (int i = 0; i < dayTextViews.size(); i++) {
            TextView dayTextView = dayTextViews.get(i);
            LocalDate date = danhSachNgayChieu.get(i);
            if (date.isEqual(selectedDate)) {
                dayTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            } else {
                dayTextView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            }
        }
    }

    private void setupViewPager() {
        // Tạo PagerAdapter.  Quan trọng: Truyền vào FragmentActivity.
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Kết nối TabLayout và ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("TP.HCM");
                    break;
                case 1:
                    tab.setText("Hà Nội");
                    break;
                case 2:
                    tab.setText("Huế");
                    break;
            }
        }).attach();
    }

    private void updateViewPagerFragments() {
        // Duyệt qua các fragment và cập nhật dữ liệu.
//        for (int i = 0; i < viewPager.getAdapter().getItemCount(); i++) {
//            // QUAN TRỌNG: Tìm fragment.
//            MyFragment  fragment = (MyFragment) getSupportFragmentManager().findFragmentByTag("f" + i);
//            if (fragment != null) {
//                fragment.updateData(selectedDate); // Truyền ngày đã chọn
//            }
//        }
    }

    // QUAN TRỌNG: Sử dụng FragmentPagerAdapter hoặc FragmentStatePagerAdapter.
    private static class MyPagerAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
        private static final int NUM_PAGES = 3;

        public MyPagerAdapter(AppCompatActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            return  null;
            // Trả về fragment tương ứng cho mỗi tab
//            return MyFragment.newInstance(position, "ten phim", 0); // Sửa lỗi ở đây
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}
