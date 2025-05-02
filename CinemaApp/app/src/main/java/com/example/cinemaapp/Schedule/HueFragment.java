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

public class HueFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hue, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Theater> list = Arrays.asList(
                new Theater("BHD Star Huế", "Tầng 5, Vincom Plaza Huế, 50A Hùng Vương, Phú Nhuận, Quận Thuận Hóa, TP.Huế", R.drawable.bhd_hue, R.drawable.bhd_hue)
        );

        recyclerView.setAdapter(new TheaterAdapter(getContext(), list));
        return v;
    }
}

