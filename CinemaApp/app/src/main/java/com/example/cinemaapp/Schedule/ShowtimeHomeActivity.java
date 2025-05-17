package com.example.cinemaapp.Schedule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Grid;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinemaapp.R;
import com.example.cinemaapp.api.ShowtimeAPI;
import com.example.cinemaapp.client.APIClient;
import com.example.cinemaapp.dto.MovieShowDTO;
import com.example.cinemaapp.dto.ShowtimeDTO;
import com.example.cinemaapp.factory.GeneralResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cinemaapp.R;
import com.example.cinemaapp.api.ShowtimeAPI;
import com.example.cinemaapp.client.APIClient;
import com.example.cinemaapp.dto.MovieShowDTO;
import com.example.cinemaapp.dto.ShowtimeDTO;
import com.example.cinemaapp.factory.GeneralResponse;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ShowtimeHomeActivity extends AppCompatActivity {
    ImageView movieCoverImageView;
    TextView thoiLuongTextView;
    TextView tenPhimTextView;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    Integer movieId;

    LinearLayout ngayChieuLinearLayout;
    ArrayList<String> gioChieuList;
    LocalDate selectedDate = LocalDate.now();
    List<TextView> dayTextViews = new ArrayList<>();
    DateTimeFormatter dateFormatterForIntent = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dateFormatterForBackend = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    ImageView btnBackLichChieu;
    String movieName;
    String movieImageUrl;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime_movie);

        movieCoverImageView = findViewById(R.id.movieCoverImage);
        tenPhimTextView = findViewById(R.id.tenPhimText);
        thoiLuongTextView = findViewById(R.id.thoiLuongText);
        ngayChieuLinearLayout = findViewById(R.id.ngayChieuLinearLayout1);
        tabLayout = findViewById(R.id.tabLayout1);
        viewPager = findViewById(R.id.viewPager1);
        btnBackLichChieu = findViewById(R.id.btnBackLichChieu1);
        btnBackLichChieu.setOnClickListener(v -> finish());

        // Lấy thông tin rạp từ Intent
        movieId = getIntent().getIntExtra("movieId", 0);
        movieName = getIntent().getStringExtra("movieName");
        movieImageUrl = getIntent().getStringExtra("movieImageUrl");
        int movieDuration = getIntent().getIntExtra("movieDuration", 0);
        int movieRequiredAge = getIntent().getIntExtra("movieRequiredAge", 0);
        Glide.with(this).load(movieImageUrl).into(movieCoverImageView);

        tenPhimTextView.setText(movieName);
        thoiLuongTextView.setText(movieDuration + " phút");
        // Khởi tạo danh sách lịch chiếu
        // Tạo và hiển thị các ô ngày
        populateNgayChieu();

        // Lấy lịch chiếu cho ngày hiện tại
        fetchLichChieu(movieId,selectedDate);
        // Thiết lập ViewPager2
    }

    private void populateNgayChieu() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("E");

        for (int i = 0; i < 7; i++) {
            LocalDate date = currentDate.plusDays(i);
            View dayView = LayoutInflater.from(this).inflate(R.layout.item_ngay_chieu, ngayChieuLinearLayout, false);
            TextView ngayTextView = dayView.findViewById(R.id.ngayTextView);
            ngayTextView.setText(String.format("%s\n%s", dayFormatter.format(date), dayOfWeekFormatter.format(date)));
            dayTextViews.add(ngayTextView);

            final LocalDate finalDate = date;
            ngayTextView.setOnClickListener(v -> {
                selectedDate = finalDate;
                updateSelectedDay();
                fetchLichChieu(movieId,selectedDate);
            });

            ngayChieuLinearLayout.addView(dayView);
        }
        updateSelectedDay();
    }

    private void fetchLichChieu(Integer movieId, LocalDate showDate) {
        ShowtimeAPI showtimeAPI = APIClient.getClient().create(ShowtimeAPI.class);
        String formattedDate = dateFormatterForBackend.format(showDate);

        Log.d("LichChieu", "Fetching showtimes for date: " + formattedDate);
        showtimeAPI.findByDate(movieId,LocalDate.parse(formattedDate)).enqueue(new Callback<GeneralResponse<MovieShowDTO>>() {
            @Override
            public void onResponse(Call<GeneralResponse<MovieShowDTO>> call, Response<GeneralResponse<MovieShowDTO>> response) {
                Log.d("LichChieu", "API response: " + (response.isSuccessful() ? "Success" : "Failed, code: " + response.code()));
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    MovieShowDTO movieShowDTOs = response.body().getData();
                    updateLichChieuList(movieShowDTOs);
                } else {
                    Log.d("LichChieu", "No data or response failed");
                    Toast.makeText(ShowtimeHomeActivity.this, "Không có lịch chiếu cho ngày này", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse<MovieShowDTO>> call, Throwable t) {
                Log.e("LichChieu", "API failure: " + t.getMessage());
                Toast.makeText(ShowtimeHomeActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLichChieuList(MovieShowDTO movieShowDTOs) {
            gioChieuList = new ArrayList<>();
            if (movieShowDTOs.getShowtimes() != null) {
                for (ShowtimeDTO showtime : movieShowDTOs.getShowtimes()) {
                    String startTime = showtime.getStartTime();
                    gioChieuList.add(startTime);
            }
        }

        ViewPagerAdapterForActivity adapter = new ViewPagerAdapterForActivity(this, gioChieuList, movieName, movieId, movieImageUrl, selectedDate.format(dateFormatterForIntent));
        viewPager.setUserInputEnabled(false);
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
    }

    private void updateSelectedDay() {
        for (int i = 0; i < dayTextViews.size(); i++) {
            TextView dayTextView = dayTextViews.get(i);
            LocalDate date = LocalDate.now().plusDays(i);
            if (date.isEqual(selectedDate)) {
                dayTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            } else {
                dayTextView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            }
        }
    }


}
