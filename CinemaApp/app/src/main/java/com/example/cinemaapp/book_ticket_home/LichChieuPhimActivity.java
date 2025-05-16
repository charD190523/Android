package com.example.cinemaapp.book_ticket_home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.LichPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LichChieuPhimActivity extends AppCompatActivity {

    // Các biến hiện có
    ImageView rapCoverImageView;
    TextView tenPhimTextView;
    TextView thoiLuongTextView;
    LinearLayout ngayChieuLinearLayout;
    ImageView btnBackLichChieu;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    LichPagerAdapter pagerAdapter;
    private List<Date> danhSachNgayChieu;
    private int selectedDateIndex = -1;

    // Thêm vào phần khai báo mới
    private List<TextView> dayTextViews = new ArrayList<>(); // Danh sách các TextView ngày
    private SimpleDateFormat dateFormatterForIntent = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()); // Định dạng ngày tháng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phim_chi_tiet);

        // Ánh xạ các view
        rapCoverImageView = findViewById(R.id.rapCoverImageView);
        tenPhimTextView = findViewById(R.id.tenPhimTextView);
        thoiLuongTextView = findViewById(R.id.thoiLuongTextView);
        btnBackLichChieu = findViewById(R.id.btnBackLichChieu);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        ngayChieuLinearLayout = findViewById(R.id.ngayChieuLinearLayout);

        // Lấy dữ liệu phim từ Intent (nếu có)
        String tenPhim = getIntent().getStringExtra("tenPhim");
        int thoiLuongPhim = getIntent().getIntExtra("thoiLuong", 0); // 0 là giá trị mặc định nếu không có
        int anhBiaPhim = getIntent().getIntExtra("anhBiaPhim", R.drawable.banner_latmat8);

        // Hiển thị dữ liệu phim
        tenPhimTextView.setText(tenPhim);
        thoiLuongTextView.setText("Thời lượng: " + thoiLuongPhim + " phút");
        rapCoverImageView.setImageResource(anhBiaPhim);

        // Tạo và hiển thị các ô ngày
        populateNgayChieu();

        // Khởi tạo PagerAdapter
        pagerAdapter = new LichPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Liên kết TabLayout và ViewPager2
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

        // Xử lý sự kiện click nút Back
        btnBackLichChieu.setOnClickListener(v -> finish());
    }

    // Hàm populateNgayChieu (tạo các ô ngày)
    private void populateNgayChieu() {
        Calendar currentCalendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            Date date = currentCalendar.getTime();
            View dayView = LayoutInflater.from(this).inflate(R.layout.item_ngay_chieu, ngayChieuLinearLayout, false);
            TextView ngayTextView = dayView.findViewById(R.id.ngayTextView);
            ngayTextView.setText(formatDate(date) + "\n" + formatDayOfWeek(date));

            dayTextViews.add(ngayTextView);

            final int dayIndex = i;
            ngayTextView.setOnClickListener(v -> {
                selectedDateIndex = dayIndex;
                updateSelectedDay();
            });

            ngayChieuLinearLayout.addView(dayView);
            currentCalendar.add(Calendar.DAY_OF_MONTH, 1); // Tiến tới ngày tiếp theo
        }

        updateSelectedDay(); // Chọn ngày hiện tại ban đầu
    }

    // Hàm updateSelectedDay (cập nhật màu sắc ngày đã chọn)
    private void updateSelectedDay() {
        for (int i = 0; i < dayTextViews.size(); i++) {
            TextView dayTextView = dayTextViews.get(i);
            if (i == selectedDateIndex) {
                dayTextView.setBackgroundColor(Color.GREEN); // Màu nền khi chọn
            } else {
                dayTextView.setBackgroundColor(Color.BLACK); // Màu nền cho các ngày không chọn
            }
        }
    }

    // Hàm formatDate (định dạng ngày)
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.getDefault());
        return sdf.format(date);
    }

    // Hàm formatDayOfWeek (định dạng thứ trong tuần)
    private String formatDayOfWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE", Locale.getDefault());
        return sdf.format(date);
    }
    public String getSelectedDateForIntent() {
        if (selectedDateIndex >= 0 && selectedDateIndex < 7) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, selectedDateIndex);
            return dateFormatterForIntent.format(cal.getTime());
        }
        return dateFormatterForIntent.format(new Date()); // fallback nếu chưa chọn
    }


}


