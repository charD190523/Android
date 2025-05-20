package com.example.cinemaapp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinemaapp.R;

public class ApplicationFormActivity extends AppCompatActivity {

    private EditText fullNameEditText, phoneNumberEditText, emailEditText;
    private RadioGroup positionGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);

        fullNameEditText = findViewById(R.id.full_name);
        phoneNumberEditText = findViewById(R.id.phone_number);
        emailEditText = findViewById(R.id.email);
        positionGroup = findViewById(R.id.position_group);
        Button submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString();
            String phoneNumber = phoneNumberEditText.getText().toString();
            String email = emailEditText.getText().toString();

            int selectedId = positionGroup.getCheckedRadioButtonId();
            RadioButton selectedPosition = findViewById(selectedId);
            String position = selectedPosition.getText().toString();

            // Xử lý dữ liệu ở đây (ví dụ: gửi lên server hoặc lưu vào cơ sở dữ liệu)

            Toast.makeText(this, "Đã gửi đơn ứng tuyển!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
