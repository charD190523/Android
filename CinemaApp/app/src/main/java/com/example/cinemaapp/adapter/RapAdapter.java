package com.example.cinemaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.book_ticket_home.ChonGheActivity2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RapAdapter extends RecyclerView.Adapter<RapAdapter.RapViewHolder> {

    private Context context;
    private List<String> danhSachRap;
    private String tenPhim;
    private String thoiLuong;
    private int anhBiaPhim;
    private List<List<String>> danhSachGioChieu;

    public RapAdapter(Context context, List<String> danhSachRap, String tenPhim, String thoiLuong, int anhBiaPhim, List<List<String>> danhSachGioChieu) {
        this.context = context;
        this.danhSachRap = danhSachRap;
        this.tenPhim = tenPhim;
        this.thoiLuong = thoiLuong;
        this.anhBiaPhim = anhBiaPhim;
        this.danhSachGioChieu = danhSachGioChieu;
    }

    public RapAdapter(Context context, List<String> danhSachRap, String tenPhim, String thoiLuong, int anhBiaPhim) {
        this.context = context;
        this.danhSachRap = danhSachRap;
        this.tenPhim = tenPhim;
        this.thoiLuong = thoiLuong;
        this.anhBiaPhim = anhBiaPhim;
        this.danhSachGioChieu = new ArrayList<>();
    }

    @NonNull
    @Override
    public RapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rap_booking, parent, false);
        return new RapViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RapViewHolder holder, final int position) {
        String tenRap = danhSachRap.get(position);
        holder.tenRapTextView.setText(tenRap);

        // Thiết lập sự kiện onClick cho item rạp
        holder.tenRapContainer.setOnClickListener(v -> {
            if (holder.gioChieuContainer.getVisibility() == View.GONE) {
                holder.gioChieuContainer.setVisibility(View.VISIBLE);
                holder.expandIndicator.setImageResource(R.drawable.ic_arrow_back); // Mũi tên lên khi mở
                hienThiGioChieu(holder, position);
            } else {
                holder.gioChieuContainer.setVisibility(View.GONE);
                holder.expandIndicator.setImageResource(R.drawable.ic_arrow_back); // Mũi tên xuống khi đóng
                holder.listGioChieu.removeAllViews();
            }
        });

        // Khởi tạo danh sách giờ chiếu nếu rỗng
        if (danhSachGioChieu.size() <= position) {
            danhSachGioChieu.add(generateGioChieu2D(new Random()));
        }
    }

    private void hienThiGioChieu(RapViewHolder holder, int position) {
        holder.listGioChieu.removeAllViews(); // Xóa các view giờ chiếu cũ

        List<String> gioChieuCuaRap = danhSachGioChieu.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        for (String gioChieu : gioChieuCuaRap) {
            // Inflate item_gio_chieu.xml
            View gioChieuView = inflater.inflate(R.layout.item_gio_chieu, holder.listGioChieu, false);
            Button btnGioChieu = gioChieuView.findViewById(R.id.btnGioChieu);
            btnGioChieu.setText(gioChieu);

            // Thêm sự kiện click cho nút giờ chiếu (tùy chọn)
            btnGioChieu.setOnClickListener(v -> {
                // Xử lý khi người dùng chọn giờ chiếu, ví dụ: mở activity đặt vé
                Intent intent = new Intent(context, ChonGheActivity2.class);
                intent.putExtra("tenPhim", tenPhim);
                intent.putExtra("gioChieu", gioChieu);
                intent.putExtra("tenRap", danhSachRap.get(position));
                intent.putExtra("ngayChieu", getCurrentDate()); // hoặc gán ngày cụ thể tùy logic
                context.startActivity(intent);
            });

            holder.listGioChieu.addView(gioChieuView);
        }
    }
    private String getCurrentDate() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date());
    }


    private List<String> generateGioChieu2D(Random random) {
        List<String> gioChieu = new ArrayList<>();
        int soSuat = random.nextInt(3) + 1; // 1 đến 3 suất chiếu
        int gioBatDau = 10 + random.nextInt(10); // Giờ bắt đầu từ 10 đến 19
        for (int i = 0; i < soSuat; i++) {
            int phut = random.nextInt(60);
            gioChieu.add(String.format("%02d:%02d", gioBatDau + i * 2, phut));
        }
        return gioChieu;
    }

    @Override
    public int getItemCount() {
        return danhSachRap.size();
    }

    static class RapViewHolder extends RecyclerView.ViewHolder {
        TextView tenRapTextView;
        LinearLayout gioChieuContainer;
        LinearLayout listGioChieu;
        LinearLayout tenRapContainer;
        ImageView expandIndicator;

        public RapViewHolder(@NonNull View itemView) {
            super(itemView);
            tenRapTextView = itemView.findViewById(R.id.tenRapTextView);
            gioChieuContainer = itemView.findViewById(R.id.gioChieuContainer);
            listGioChieu = itemView.findViewById(R.id.list_giờ_chiếu);
            tenRapContainer = itemView.findViewById(R.id.tenRapContainer);
            expandIndicator = itemView.findViewById(R.id.expandIndicator);
        }
    }
}