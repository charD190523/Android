package com.example.cinemaapp.Schedule;

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
import com.example.cinemaapp.model.Theater1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TPHCMFragment1 extends Fragment {

    private RecyclerView recyclerView;
    private TheaterAdapter1 adapter;
    private List<Theater1> theaterList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout cho fragment
        return inflater.inflate(R.layout.fragment_tphcm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Lấy danh sách giờ chiếu từ arguments
        ArrayList<String> gioChieuList = new ArrayList<>();
        String tenPhim = getArguments().getString("tenPhim");
        int idPhim = getArguments().getInt("idPhim");
        String imageUrl = getArguments().getString("imageUrl");
        String ngayChieu = getArguments().getString("ngayChieu");
        Bundle args = getArguments();
        if (args != null && args.containsKey("gioChieuList")) {
            gioChieuList = args.getStringArrayList("gioChieuList");

        }

        // Tạo danh sách rạp với giờ chiếu
        theaterList = Arrays.asList(
                new Theater1("BHD Star Bitexco", idPhim, tenPhim, imageUrl, ngayChieu, gioChieuList),
                new Theater1("BHD Star 3/2",idPhim, tenPhim, imageUrl, ngayChieu, gioChieuList),
                new Theater1("BHD Star Lê Văn Việt", idPhim, tenPhim, imageUrl, ngayChieu, gioChieuList)
        );

        // Set adapter cho RecyclerView
        adapter = new TheaterAdapter1(theaterList);
        recyclerView.setAdapter(adapter);
    }
}
