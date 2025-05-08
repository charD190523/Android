package com.example.cinemaapp.Schedule;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LichChieuActivity extends AppCompatActivity {

    ImageView rapCoverImageView;
    TextView tenRapTextView;
    TextView diaChiRapTextView;
    LinearLayout ngayChieuLinearLayout;
    RecyclerView lichChieuRecyclerView;
    LichChieuPhimAdapter lichChieuAdapter;
    List<LichChieuPhimAdapter.LichChieuPhimItem> lichChieuList; // Sử dụng đúng model class
    LocalDate selectedDate = LocalDate.now();
    List<TextView> dayTextViews = new ArrayList<>();
    DateTimeFormatter dateFormatterForIntent = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Khai báo ở cấp độ class
    ImageView btnBackLichChieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_chieu);

        rapCoverImageView = findViewById(R.id.rapCoverImageView);
        tenRapTextView = findViewById(R.id.tenRapTextView);
        diaChiRapTextView = findViewById(R.id.diaChiRapTextView);
        ngayChieuLinearLayout = findViewById(R.id.ngayChieuLinearLayout);
        lichChieuRecyclerView = findViewById(R.id.lichChieuRecyclerView);
        lichChieuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnBackLichChieu = findViewById(R.id.btnBackLichChieu);
        btnBackLichChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity hiện tại và quay lại Activity trước
            }
        });
        // Lấy thông tin rạp từ Intent
        Intent intent = getIntent();
        String tenRap = intent.getStringExtra("tenRap");
        String diaChiRap = intent.getStringExtra("diaChiRap");
        int anhBiaRap = intent.getIntExtra("anhBiaRap", R.drawable.sample_rap); // Nhận ID ảnh bìa (có giá trị mặc định)

        tenRapTextView.setText(tenRap);
        diaChiRapTextView.setText(diaChiRap);
        rapCoverImageView.setImageResource(anhBiaRap); // Hiển thị ảnh bìa

        // Tạo và hiển thị các ô ngày trong 7 ngày tới
        populateNgayChieu();
        String ngayChieuFormatted = dateFormatterForIntent.format(selectedDate);

        // Tạo dữ liệu lịch chiếu (ví dụ: ngẫu nhiên cho ngày hiện tại)
        lichChieuList = generateLichChieuPhim(selectedDate);
        lichChieuAdapter = new LichChieuPhimAdapter(this, lichChieuList, tenRap, ngayChieuFormatted); // Truyền context vào Adapter
        lichChieuRecyclerView.setAdapter(lichChieuAdapter);
    }

    private void populateNgayChieu() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd"); // Khai báo và khởi tạo ở đây
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
                lichChieuList = generateLichChieuPhim(selectedDate);
                lichChieuAdapter.setLichChieuList(lichChieuList);
            });

            ngayChieuLinearLayout.addView(dayView);
        }
        updateSelectedDay(); // Chọn ngày hiện tại ban đầu
    }

    private void updateSelectedDay() {
        for (int i = 0; i < dayTextViews.size(); i++) {
            TextView dayTextView = dayTextViews.get(i);
            LocalDate date = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                date = LocalDate.now().plusDays(i);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (date.isEqual(selectedDate)) {
                    dayTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.green)); // Màu xanh bạn muốn
                } else {
                    dayTextView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
                }
            }
        }
    }

    private List<LichChieuPhimAdapter.LichChieuPhimItem> generateLichChieuPhim(LocalDate date) {
        List<LichChieuPhimAdapter.LichChieuPhimItem> items = new ArrayList<>();
        Random random = new Random();
        int numMovies = random.nextInt(3) + 1; // 1 đến 3 phim

        for (int i = 0; i < numMovies; i++) {
            String tenPhim = getRandomTenPhim();
            List<String> gioChieu = generateGioChieu2D(random);
            items.add(new LichChieuPhimAdapter.LichChieuPhimItem(tenPhim, "2D", gioChieu)); // Thêm định dạng 2D
        }
        return items;
    }

    private String getRandomTenPhim() {
        String[] tenPhims = {"Địa đạo: Mật trời trong bóng tối", "A Minecraft Movie", "DROP: Buổi hẹn hò kinh hoàng", "PANOR: Tà thuật huyết ngải"};
        Random random = new Random();
        return tenPhims[random.nextInt(tenPhims.length)];
    }

    private List<String> generateGioChieu2D(Random random) {
        List<String> gioChieu = new ArrayList<>();
        int soSuat = random.nextInt(3) + 1; // 1 đến 3 suất chiếu
        int gioBatDau = 10 + random.nextInt(10); // Giờ bắt đầu từ 10 đến 19
        for (int i = 0; i < soSuat; i++) {
            int phut = random.nextInt(60);
            gioChieu.add(String.format("%02d:%02d", gioBatDau + i * 2, phut)); // Giả sử cách nhau 2 tiếng
        }
        return gioChieu;
    }
}