package com.example.cinemaapp.Schedule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.model.Theater1;

import java.util.List;

public class TheaterAdapter1 extends RecyclerView.Adapter<TheaterAdapter1.TheaterViewHolder> {

    private final List<Theater1> theaterList;

    public TheaterAdapter1(List<Theater1> theaterList) {
        this.theaterList = theaterList;
    }

    @NonNull
    @Override
    public TheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rap2, parent, false);
        return new TheaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterViewHolder holder, int position) {
        Theater1 theater = theaterList.get(position);
        holder.tenRapTextView.setText(theater.getName());
        holder.gioChieuContainer.removeAllViews();

        // Tạo mới TextView cho mỗi giờ chiếu
        for (String gio : theater.getShowTimes()) {
            String gioFormat = gio.substring(0, 5);

            Button btnGio = new Button(holder.itemView.getContext());
            btnGio.setText(gioFormat);
            btnGio.setTextSize(14);
            btnGio.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.white));
            btnGio.setBackgroundResource(R.drawable.selector_gio_chieu); // Drawable bạn tự tạo

            // Set padding và margin
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 4, 8, 4);
            btnGio.setLayoutParams(params);

            // Xử lý click
            btnGio.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, ChonGheActivity.class);
                intent.putExtra("tenPhim", theater.getMovieName());  // ví dụ getter tên phim
                intent.putExtra("idPhim", theater.getMovieId());     // ví dụ getter id phim
                intent.putExtra("imageUrl", theater.getMovieImageUrl());
                intent.putExtra("gioChieu", gioFormat);               // giờ chiếu vừa tạo
                intent.putExtra("tenRap", theater.getName());         // tên rạp
                intent.putExtra("ngayChieu", theater.getDate());
                context.startActivity(intent);
            });

            holder.gioChieuContainer.addView(btnGio); // Thêm button vào layout
        }
    }

    @Override
    public int getItemCount() {
        return theaterList.size();
    }

    public static class TheaterViewHolder extends RecyclerView.ViewHolder {
        TextView tenRapTextView;
        LinearLayout gioChieuContainer;

        public TheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            tenRapTextView = itemView.findViewById(R.id.tenRapTextView1);
            gioChieuContainer = itemView.findViewById(R.id.layoutGioChieu);
        }
    }
}
