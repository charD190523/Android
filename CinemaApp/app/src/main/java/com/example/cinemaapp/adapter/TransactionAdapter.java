package com.example.cinemaapp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.model.TransactionItem;

import java.util.List;

// app/src/main/java/your/package/name/TransactionAdapter.java


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<TransactionItem> transactionList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TransactionItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public TransactionAdapter(List<TransactionItem> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giao_dich, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionItem currentItem = transactionList.get(position);
        holder.tvMovieTitle.setText(currentItem.getMovieTitle());
        holder.tvDateTime.setText(currentItem.getDateTime());
        holder.tvPrice.setText(currentItem.getPrice());
        holder.tvRap.setText(currentItem.getCinemaName());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMovieTitle;
        public TextView tvDateTime;
        public TextView tvPrice;
        public TextView tvRap;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvRap = itemView.findViewById(R.id.tvRap);
        }
    }
}