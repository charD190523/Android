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

public class NowShowingFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewMovies);

        // Trượt ngang
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Danh sách phim mẫu
        List<MovieCardAdapter.Movie> movies = new ArrayList<>();
        movies.add(new MovieCardAdapter.Movie("Lật Mặt 8: Vòng Tay Nặng", 135, "T13", R.drawable.poster_latmat8, true));
        movies.add(new MovieCardAdapter.Movie("Minecraft: The Movie", 120, "P", R.drawable.minecraft_the_movie, true));
        movies.add(new MovieCardAdapter.Movie("Panor: Tà thuật huyết ngải", 118, "T18", R.drawable.panor, true));
        movies.add(new MovieCardAdapter.Movie("Alita: Thiên thần chiến binh", 109, "T14", R.drawable.alita, true));




        MovieCardAdapter adapter = new MovieCardAdapter(getContext(), movies);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
