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
import com.example.cinemaapp.adapter.MovieCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class ComingSoonFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewMovies);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<MovieCardAdapter.Movie> movies = new ArrayList<>();
        movies.add(new MovieCardAdapter.Movie("Superman", 120, "TBC", R.drawable.superman, false));
        movies.add(new MovieCardAdapter.Movie("Thunderbolts: Biệt đội sấm sét", 126, "TBC", R.drawable.thunderbolt, false));
        movies.add((new MovieCardAdapter.Movie("Holy Night: Đội săn quỷ", 120, "TBC", R.drawable.holynight, false)));
        movies.add((new MovieCardAdapter.Movie("Lilo & Stitch", 108, "TBC", R.drawable.lilo, false)));

        MovieCardAdapter adapter = new MovieCardAdapter(getContext(), movies);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
