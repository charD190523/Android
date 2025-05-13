package com.example.cinemaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinemaapp.R;
import com.example.cinemaapp.model.Movie;

import java.util.List;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.MovieViewHolder> {

    private final List<Movie> movieList;
    private final Context context;

    public MovieCardAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_card, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.movie_name.setText(movie.getMovie_name());
        String[] timeParts = movie.getDuration().split(":");
        int totalMinutes = Integer.parseInt(timeParts[0]) * 60 + Integer.parseInt(timeParts[1]);
        holder.duration.setText(totalMinutes + " phút");
        holder.required_age.setText(movie.getRequired_age() + "+");
        Glide.with(context).load(movie.getImage_url()).into(holder.image_url);
        Glide.with(context).load(movie.getImage_url()).into(holder.image_url);

        if (movie.isAvailable()) {
            holder.btnBooking.setVisibility(View.VISIBLE);
        } else {
            holder.btnBooking.setVisibility(View.GONE);
        }

        holder.btnBooking.setOnClickListener(v -> {
            Toast.makeText(context, "Đặt vé: " + movie.getMovie_name(), Toast.LENGTH_SHORT).show();
            // TODO: chuyển sang màn hình đặt vé
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView image_url;
        TextView movie_name, duration, required_age;
        Button btnBooking;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            image_url = itemView.findViewById(R.id.image_url);
            movie_name = itemView.findViewById(R.id.movie_name);
            duration = itemView.findViewById(R.id.duration);
            required_age = itemView.findViewById(R.id.required_age);
            btnBooking = itemView.findViewById(R.id.btnBooking);
        }
    }
}

