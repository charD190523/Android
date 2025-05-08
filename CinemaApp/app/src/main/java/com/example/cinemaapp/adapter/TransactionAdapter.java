package com.example.cinemaapp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.model.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTransactionTime;
        private TextView tvSite;
        private TextView tvItemName;
        private TextView tvQuantity;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTransactionTime = itemView.findViewById(R.id.tv_transaction_time);
            tvSite = itemView.findViewById(R.id.tv_site);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
        }

        public void bind(Transaction transaction) {
            tvTransactionTime.setText(transaction.getTransactionTime());
            tvSite.setText(transaction.getSite());
            tvItemName.setText(transaction.getItemName());
            tvQuantity.setText(String.valueOf(transaction.getQuantity()));
        }
    }
}
