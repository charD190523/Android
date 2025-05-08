package com.example.cinemaapp.Schedule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;

import java.util.List;

public class LichChieuPhimAdapter extends RecyclerView.Adapter<LichChieuPhimAdapter.ViewHolder> {
    private List<LichChieuPhimItem> lichChieuList;
    private Context context;
    private String tenRap;// Cần context để tạo Intent và Button
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
        holder.dinhDangTextView.setText(item.getDinhDang());

        holder.layoutGioChieu.removeAllViews(); // Xóa các view cũ

        for (String gioChieu : item.getGioChieu()) {
            Button btnGio = new Button(context);
            btnGio.setText(gioChieu);
            btnGio.setTextSize(14);
            btnGio.setTextColor(context.getResources().getColor(android.R.color.white));
            btnGio.setBackgroundResource(R.drawable.selector_gio_chieu); // Sử dụng background selector
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 8, 0); // Thêm margin giữa các nút
            holder.layoutGioChieu.addView(btnGio, params);

            btnGio.setOnClickListener(v -> {
                Intent intent = new Intent(context, ChonGheActivity.class);
                intent.putExtra("tenPhim", item.getTenPhim());
                intent.putExtra("gioChieu", gioChieu);
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
        TextView dinhDangTextView;
        LinearLayout layoutGioChieu; // Thay thế TextView gioChieuTextView bằng LinearLayout

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenPhimTextView = itemView.findViewById(R.id.tenPhimTextView);
            dinhDangTextView = itemView.findViewById(R.id.dinhDangTextView);
            layoutGioChieu = itemView.findViewById(R.id.layoutGioChieu); // Ánh xạ LinearLayout
        }
    }

    // Model class (giữ nguyên hoặc điều chỉnh nếu cần)
    public static class LichChieuPhimItem {
        private String tenPhim;
        private String dinhDang;
        private List<String> gioChieu;

        public LichChieuPhimItem(String tenPhim, String dinhDang, List<String> gioChieu) {
            this.tenPhim = tenPhim;
            this.dinhDang = dinhDang;
            this.gioChieu = gioChieu;
        }

        public String getTenPhim() {
            return tenPhim;
        }

        public String getDinhDang() {
            return dinhDang;
        }

        public List<String> getGioChieu() {
            return gioChieu;
        }
    }
}