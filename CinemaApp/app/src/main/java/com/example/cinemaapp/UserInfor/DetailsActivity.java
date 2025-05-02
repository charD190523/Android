package com.example.cinemaapp.UserInfor;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.adapter.TransactionAdapter;
import com.example.cinemaapp.R;
import com.example.cinemaapp.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Set up RecyclerView
        RecyclerView rvTransactions = findViewById(R.id.rv_transactions);
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));

        // Sample transaction data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("2025-04-12 10:00", "BHD Star", "Movie Ticket", 2));
        transactions.add(new Transaction("2025-04-11 15:30", "BHD Star", "Popcorn Combo", 1));
        transactions.add(new Transaction("2025-04-10 18:45", "BHD Star", "Movie Ticket", 3));

        // Set adapter
        TransactionAdapter adapter = new TransactionAdapter(transactions);
        rvTransactions.setAdapter(adapter);

        // Set up back button
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
    }
}