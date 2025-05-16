package com.example.cinemaapp.Schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LichChieuPhimAdapter extends RecyclerView.Adapter<LichChieuPhimAdapter.ViewHolder> {
    private List<LichChieuPhimItem> lichChieuList;
    private Context context;
    private String tenRap;
    private String ngayChieu;

    public LichChieuPhimAdapter(Context context, List<LichChieuPhimItem> lichChieuList, String tenRap, String ngayChieu) {
        this.lichChieuList = lichChieuList;
        this.context = context;
        this.tenRap = tenRap;
        this.ngayChieu = ngayChieu;
    }

    public void setLichChieuList(List<LichChieuPhimItem> newList) {
        this.lichChieuList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_chieu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LichChieuPhimItem item = lichChieuList.get(position);
        holder.tenPhimTextView.setText(item.getTenPhim());
        holder.layoutGioChieu.removeAllViews();

        for (String gioChieu : item.getGioChieu()) {
            Button btnGio = new Button(context);
            String gioChieuFormat = gioChieu.substring(0, 5);

            btnGio.setText(gioChieuFormat);
            btnGio.setTextSize(14);
            btnGio.setTextColor(context.getResources().getColor(android.R.color.white));
            btnGio.setBackgroundResource(R.drawable.selector_gio_chieu);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 8, 0);
            holder.layoutGioChieu.addView(btnGio, params);

            btnGio.setOnClickListener(v -> {
                Intent intent = new Intent(context, ChonGheActivity.class);
                intent.putExtra("tenPhim", item.getTenPhim());
                intent.putExtra("gioChieu", String.valueOf(gioChieu));
                intent.putExtra("tenRap", tenRap);
                intent.putExtra("ngayChieu", ngayChieu);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return lichChieuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenPhimTextView;
        LinearLayout layoutGioChieu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenPhimTextView = itemView.findViewById(R.id.tenPhimTextView);
            layoutGioChieu = itemView.findViewById(R.id.layoutGioChieu);
        }
    }

    public static class LichChieuPhimItem {
        private String tenPhim;
        private List<String> gioChieu;

        public LichChieuPhimItem(String tenPhim, List<String> gioChieu) {
            this.tenPhim = tenPhim;
            this.gioChieu = gioChieu;
        }

        public String getTenPhim() {
            return tenPhim;
        }

        public List<String> getGioChieu() {
            return gioChieu;
        }
    }
}