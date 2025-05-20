package com.example.cinemaapp.UserInfor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinemaapp.R;
import com.example.cinemaapp.dto.response.InvoiceDetailDTO;
import com.example.cinemaapp.model.Seat;

import org.w3c.dom.Text;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        InvoiceDetailDTO invoiceDetailDTO = (InvoiceDetailDTO) intent.getSerializableExtra("invoiceDetailDTO");

        // Tìm các TextView trong layout
        TextView tvDetailMovieTitle = findViewById(R.id.tvDetailMovieTitle);
        TextView tvDetailSeats = findViewById(R.id.tvDetailSeats);
        TextView tvDetailDateTime = findViewById(R.id.tvDetailDateTime);
        TextView tvDetailTotalPrice = findViewById(R.id.tvDetailTotalPrice);
        TextView tvDetailTicketPrice = findViewById(R.id.tvDetailTicketPrice);
        TextView tvDetailPopcornPrice = findViewById(R.id.tvDetailPopcornPrice);

        // Thiết lập dữ liệu động từ InvoiceDetailDTO
        if (invoiceDetailDTO != null) {
            // Tên phim
            tvDetailMovieTitle.setText(invoiceDetailDTO.getMovieName());

            // Ngày và giờ chiếu
            String dateTime = invoiceDetailDTO.getShowDate() + " " + invoiceDetailDTO.getStartTime();
            tvDetailDateTime.setText(dateTime);

            // Danh sách ghế
            List<Seat> seats = invoiceDetailDTO.getSeats();
            if (seats != null && !seats.isEmpty()) {
                String seatNames = seats.stream()
                        .map(Seat::getSeatName) // Giả sử Seat có phương thức getTen() để lấy tên ghế
                        .collect(Collectors.joining(", "));
                tvDetailSeats.setText(seatNames);
            } else {
                tvDetailSeats.setText("-/-");
            }

            // Tổng giá
            tvDetailTotalPrice.setText(String.format("%,.0f đ", invoiceDetailDTO.getTotalPrice()));

            // Giá vé
            tvDetailTicketPrice.setText(String.format("%,.0f đ", invoiceDetailDTO.getTicketPrice()));

            // Giá bắp nước
            tvDetailPopcornPrice.setText(String.format("%,.0f đ", invoiceDetailDTO.getFoodPrice()));
        }

        // Xử lý nút Back
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}