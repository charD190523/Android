package com.example.cinemaapp.Schedule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.model.Theater;

import java.util.List;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder> {
    List<Theater> theaters;
    Context context;

    public TheaterAdapter(Context context, List<Theater> theaters) {
        this.context = context;
        this.theaters = theaters;
    }

    @NonNull
    @Override
    public TheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rap, parent, false);
        return new TheaterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterViewHolder holder, int position) {
        Theater currentTheater = theaters.get(position);
        holder.nameTextView.setText(currentTheater.name);
        holder.addressTextView.setText(currentTheater.address);
        holder.imageView.setImageResource(currentTheater.imageResource);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Theater clickedTheater = theaters.get(adapterPosition);

                    Intent intent = new Intent(context, LichChieuActivity.class);
                    intent.putExtra("tenRap", clickedTheater.name);
                    intent.putExtra("diaChiRap", clickedTheater.address);
                    intent.putExtra("anhBiaRap", clickedTheater.coverImageResource); // Truyền ID ảnh bìa
                    // Bạn có thể truyền thêm ID rạp hoặc thông tin cần thiết khác
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return theaters.size();
    }

    static class TheaterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView addressTextView;

        // Đổi tên constructor thành TheaterViewHolder
        public TheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rapImage);
            nameTextView = itemView.findViewById(R.id.rapName);
            addressTextView = itemView.findViewById(R.id.rapAddress);
        }
    }
}