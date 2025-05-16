package com.example.cinemaapp.book_ticket_home;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;

import java.util.List;


public class LichChieuPhimAdapter extends RecyclerView.Adapter<LichChieuPhimAdapter.LichChieuPhimViewHolder> {


    private final Context context;

    private List<LichChieuPhimItem> lichChieuList;

    private String tenPhim;

    private String ngayChieu;


    public LichChieuPhimAdapter(Context context, List<LichChieuPhimItem> lichChieuList, String tenPhim, String ngayChieu) {

        this.context = context;

        this.lichChieuList = lichChieuList;

        this.tenPhim = tenPhim;

        this.ngayChieu = ngayChieu;

    }


    public void setLichChieuList(List<LichChieuPhimItem> newList) {

        this.lichChieuList = newList;

        notifyDataSetChanged();

    }


    public void setTenPhim(String tenPhim) {

        this.tenPhim = tenPhim;

    }


    public void setNgayChieu(String ngayChieu) {

        this.ngayChieu = ngayChieu;

    }


    @NonNull

    @Override

    public LichChieuPhimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_lich_chieu_theo_phim, parent, false);

        return new LichChieuPhimViewHolder(view);

    }


    @Override

    public void onBindViewHolder(@NonNull LichChieuPhimViewHolder holder, int position) {

        LichChieuPhimItem item = lichChieuList.get(position);

        holder.dinhDangTextView.setText(item.getDinhDang());


// Xóa các view cũ trong LinearLayout

        holder.listGioChieu.removeAllViews();


// Thêm RecyclerView động vào LinearLayout

        if (item.getGioChieu() != null && !item.getGioChieu().isEmpty()) {

            RecyclerView recyclerView = new RecyclerView(context);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(

                    LinearLayout.LayoutParams.MATCH_PARENT,

                    LinearLayout.LayoutParams.WRAP_CONTENT

            );

            recyclerView.setLayoutParams(params);


// Thiết lập adapter cho giờ chiếu

            GioChieuAdapter gioChieuAdapter = new GioChieuAdapter(context, item.getGioChieu());

            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

            recyclerView.setAdapter(gioChieuAdapter);


// Thêm RecyclerView vào LinearLayout

            holder.listGioChieu.addView(recyclerView);

        }

    }


    @Override

    public int getItemCount() {

        return lichChieuList != null ? lichChieuList.size() : 0;

    }


    public static class LichChieuPhimViewHolder extends RecyclerView.ViewHolder {

        TextView dinhDangTextView;

        LinearLayout listGioChieu;


        public LichChieuPhimViewHolder(@NonNull View itemView) {

            super(itemView);

            dinhDangTextView = itemView.findViewById(R.id.dinhDangTextView);

            listGioChieu = itemView.findViewById(R.id.listGioChieu);

        }

    }


// Lớp mô hình dữ liệu

    public static class LichChieuPhimItem {

        private final String tenPhim;

        private final String dinhDang;

        private final List<String> gioChieu;


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


// Adapter cho giờ chiếu

    private static class GioChieuAdapter extends RecyclerView.Adapter<GioChieuAdapter.GioChieuViewHolder> {

        private final Context context;

        private final List<String> gioChieuList;


        public GioChieuAdapter(Context context, List<String> gioChieuList) {

            this.context = context;

            this.gioChieuList = gioChieuList;

        }


        @NonNull

        @Override

        public GioChieuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_gio_chieu, parent, false);

            return new GioChieuViewHolder(view);

        }


        @Override

        public void onBindViewHolder(@NonNull GioChieuViewHolder holder, int position) {

            String gioChieu = gioChieuList.get(position);

            holder.gioChieuButton.setText(gioChieu);

            holder.gioChieuButton.setOnClickListener(v -> {

// Xử lý sự kiện khi nhấn vào giờ chiếu

            });

        }


        @Override

        public int getItemCount() {

            return gioChieuList != null ? gioChieuList.size() : 0;

        }


        public static class GioChieuViewHolder extends RecyclerView.ViewHolder {

            Button gioChieuButton;


            public GioChieuViewHolder(@NonNull View itemView) {

                super(itemView);

                gioChieuButton = itemView.findViewById(R.id.btnGioChieu);

            }

        }

    }

}