package com.example.cinemaapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.MovieCardAdapter;
import com.example.cinemaapp.api.MovieApi;
import com.example.cinemaapp.client.APIClient;
import com.example.cinemaapp.dto.MovieResponse;
import com.example.cinemaapp.dto.UpdateInforDTO;
import com.example.cinemaapp.factory.GeneralResponse;
import com.example.cinemaapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NowShowingFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieCardAdapter adapter;
    private List<Movie> movies;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewMovies);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        movies = new ArrayList<>();
        fetchMoviesFromApi();

        adapter = new MovieCardAdapter(getContext(), movies);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void fetchMoviesFromApi() {
        MovieApi movieApi = APIClient.getClient().create(MovieApi.class);
        movieApi.getMovies().enqueue(new retrofit2.Callback<MovieResponse>(){
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movieList = response.body().getData();
                    movies.clear();
                    for (Movie movie : movieList) {
                        if (movie.isAvailable()) {
                            movies.add(movie);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Lỗi phản hồi từ server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Lỗi")
                        .setMessage("Lỗi tải phim: " + t.getMessage())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }

}
