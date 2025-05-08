package com.example.cinemaapp.Schedule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;

import java.util.List;

public class GioChieuAdapter extends RecyclerView.Adapter<GioChieuAdapter.ViewHolder> {

    private Context context;
    private List<String> listGioChieu;
    private String tenPhim; // Cần truyền tên phim vào adapter

    public GioChieuAdapter(Context context, List<String> listGioChieu, String tenPhim) {
        this.context = context;
        this.listGioChieu = listGioChieu;
        this.tenPhim = tenPhim;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gio_chieu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String gioChieu = listGioChieu.get(position);
        holder.btnGioChieu.setText(gioChieu);

        holder.btnGioChieu.setOnClickListener(v -> {
            // Xử lý sự kiện khi giờ chiếu được nhấn
            Intent intent = new Intent(context, ChonGheActivity.class);
            intent.putExtra("tenPhim", tenPhim);
            intent.putExtra("gioChieu", gioChieu);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listGioChieu.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnGioChieu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnGioChieu = itemView.findViewById(R.id.btnGioChieu);
        }
    }
}
