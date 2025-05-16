package com.example.cinemaapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cinemaapp.R;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView bannerImage, backButton;
    private TextView movieTitle, movieGenre,movieDuration, movieDescription, movieDirector, movieActor;
    private Button bookTicketButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        backButton = findViewById(R.id.btnBackMovieDetail);
        // Ánh xạ các thành phần giao diện
        bannerImage = findViewById(R.id.image_url);
        movieTitle = findViewById(R.id.movie_name);
        movieGenre = findViewById(R.id.genre);
        movieDuration = findViewById(R.id.duration);
        movieDescription = findViewById(R.id.description);
        movieDirector = findViewById(R.id.director);
        movieActor = findViewById(R.id.actor);
        bookTicketButton = findViewById(R.id.bookTicketButton);

        // Lấy dữ liệu từ Intent
        String movieGenre = getIntent().getStringExtra("MOVIE_GENRE");
        String movieDescription = getIntent().getStringExtra("MOVIE_DESCRIPTION");
        String movieDirector = getIntent().getStringExtra("MOVIE_DIRECTOR");
        String movieActor = getIntent().getStringExtra("MOVIE_ACTOR");
        String movieId = getIntent().getStringExtra("MOVIE_ID");
        String movieImageUrl = getIntent().getStringExtra("MOVIE_IMAGE_URL");
        String movieName = getIntent().getStringExtra("MOVIE_NAME");
        int movieDuration = getIntent().getIntExtra("MOVIE_DURATION", 0);
        int movieRequiredAge = getIntent().getIntExtra("MOVIE_REQUIRED_AGE", 0);
        boolean isAvailable = getIntent().getBooleanExtra("MOVIE_AVAILABLE", false);
        // Cập nhật giao diện với dữ liệu nhận được
        updateUI(movieGenre, movieDescription, movieDirector, movieActor, movieId, movieImageUrl, movieName, movieDuration, movieRequiredAge, isAvailable);
       backButton.setOnClickListener(v -> finish());
        // Xử lý sự kiện nhấn nút "Đặt vé"
        bookTicketButton.setOnClickListener(v -> {
            Toast.makeText(this, "Chuyển hướng đến màn hình đặt vé...", Toast.LENGTH_SHORT).show();
            // Thêm logic để chuyển sang màn hình đặt vé
        });
    }

    private void updateUI(String genre, String description, String director, String actor, String id, String imageUrl, String name, int duration, int reqiredAge,boolean isAvailable) {
        Log.d("MovieDetailActivity", "genre=" + genre + ", description=" + description);

        // Tải ảnh banner bằng Glide
        Glide.with(this).load(imageUrl).into(bannerImage);
        // Cập nhật mô tả phim
        movieDescription.setText(description);
        // Cập nhật thể loại phim
        movieGenre.setText(genre);
        // Cập nhật diễn viên
        movieDirector.setText(director);
        // Cập nhật diễn viên
        movieActor.setText(actor);
        // Cập nhật tiêu đề phim
        movieTitle.setText(name);
        // Cập nhật thời lượng phim (genres tạm thời để trống vì chưa có dữ liệu)
        movieDuration.setText(duration + " phút");
        // Cập nhật độ tuổi yêu cầu

        // Các thông tin khác (mô tả, ngày phát hành, ngôn ngữ, phòng chiếu) để trống hoặc lấy từ nguồn khác nếu cần
        if (isAvailable) {
            bookTicketButton.setVisibility(View.VISIBLE);
        } else {
            bookTicketButton.setVisibility(View.GONE);
        }
    }

}