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

public class HanoiFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hanoi, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Theater> list = Arrays.asList(
                new Theater("BHD Star Garden", "Tầng 4, TTTM Garden Shopping Center, Phố Mễ Trì, P.Mỹ Đình 1, Quận Nam Từ Liêm, Hà Nội", R.drawable.bhd_garden, R.drawable.bhd_garden),
                new Theater("BHD Star Phạm Ngọc Thạch", "Tầng 8, Vincom Center Phạm Ngọc Thạch, Quận Đống Đa, Hà Nội", R.drawable.bhd_pnt, R.drawable.bhd_pnt),
                new Theater("BHD Star Discovery", "Tầng 8, TTTM Discovery – 302 Cầu Giấy, P.Dịch Vọng, Quận Cầu Giấy, Hà Nội", R.drawable.bhd_discovery, R.drawable.bhd_discovery)
        );

        recyclerView.setAdapter(new TheaterAdapter(getContext(), list));
        return v;
    }
}

