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
import com.example.cinemaapp.model.Theater;

import java.util.Arrays;
import java.util.List;

public class TPHCMFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tphcm, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Theater> list = Arrays.asList(
                new Theater("BHD Star Bitexco", "Tầng 3, TTTM Bitexco, 2 Hải Triều, Q.1, TP.HCM", R.drawable.bhd_bitexco, R.drawable.bhd_bitexco),
                new Theater("BHD Star 3/2", "Lầu 5, TTTM Vincom 3/2, 3C Đường 3 Tháng 2, Q.10, TP.HCM", R.drawable.bhd_32, R.drawable.bhd_32),
                new Theater( "BHD Star Lê Văn Việt", "Tầng 4, Vincom Plaza, 50 Lê Văn Việt, P.Hiệp Phú, Q.9, TP.HCM", R.drawable.bhd_lvv, R.drawable.bhd_lvv)
        );

        recyclerView.setAdapter(new TheaterAdapter(getContext(), list));
        return v;
    }
}

