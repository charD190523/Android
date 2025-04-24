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

import com.example.cinemaapp.R;

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
        holder.textTitle.setText(movie.getTitle());
        holder.textInfo.setText(movie.getDuration() + " phút  " + movie.getAgeTag());
        holder.imagePoster.setImageResource(movie.getImageResId());

        if (movie.isBookingAvailable) {
            holder.btnBooking.setVisibility(View.VISIBLE);
        } else {
            holder.btnBooking.setVisibility(View.GONE);
        }

        holder.btnBooking.setOnClickListener(v -> {
            Toast.makeText(context, "Đặt vé: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
            // TODO: chuyển sang màn hình đặt vé
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePoster;
        TextView textTitle, textInfo;
        Button btnBooking;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
            textTitle = itemView.findViewById(R.id.textTitle);
            textInfo = itemView.findViewById(R.id.textInfo);
            btnBooking = itemView.findViewById(R.id.btnBooking);
        }
    }

    // Movie class nội bộ (hoặc có thể tách riêng)
    public static class Movie {
        private final String title;
        private final int duration;
        private final String ageTag;
        private final int imageResId;

        public Movie(String title, int duration, String ageTag, int imageResId, boolean isBookingAvailable) {
            this.isBookingAvailable = isBookingAvailable;
            this.title = title;
            this.duration = duration;
            this.ageTag = ageTag;
            this.imageResId = imageResId;
        }

        public String getTitle() { return title; }
        public int getDuration() { return duration; }
        public String getAgeTag() { return ageTag; }
        public int getImageResId() { return imageResId; }

        public boolean isBookingAvailable;
    }
}

