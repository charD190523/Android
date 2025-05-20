package com.example.cinemaapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemaapp.activity.ApplicationFormActivity;
import com.example.cinemaapp.R;

public class RecruitmentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruit, container, false);

        Button applyButton = view.findViewById(R.id.apply_button);
        applyButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ApplicationFormActivity.class);
            startActivity(intent);
        });

        return view;
    }
}

