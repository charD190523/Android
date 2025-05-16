package com.example.cinemaapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.RapAdapter;

import java.util.ArrayList;
import java.util.List;

public class LichChieuHaNoiFragment extends Fragment {

    private RecyclerView rapRecyclerView;
    private RapAdapter rapAdapter;
    private List<String> danhSachRapHaNoi;

    // Thông tin phim
    private String tenPhim;
    private String thoiLuong;
    private int anhBiaPhim;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lich_chieu_ha_noi, container, false);
        rapRecyclerView = view.findViewById(R.id.rapRecyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Lấy thông tin phim từ Bundle
        if (getArguments() != null) {
            tenPhim = getArguments().getString("tenPhim");
            thoiLuong = getArguments().getString("thoiLuong");
            anhBiaPhim = getArguments().getInt("anhBiaPhim");
        }

        // Dữ liệu rạp cho Hà Nội
        danhSachRapHaNoi = new ArrayList<>();
        danhSachRapHaNoi.add("BHD Star Garden");
        danhSachRapHaNoi.add("BHD Star Phạm Ngọc Thạch");
        danhSachRapHaNoi.add("BHD Star Discovery");
        // ...

        // Thiết lập RecyclerView và Adapter
        rapRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        rapAdapter = new RapAdapter(requireContext(), danhSachRapHaNoi, tenPhim, thoiLuong, anhBiaPhim);
        rapRecyclerView.setAdapter(rapAdapter);
    }
}