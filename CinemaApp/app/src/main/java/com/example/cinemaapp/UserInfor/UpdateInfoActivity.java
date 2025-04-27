package com.example.cinemaapp.UserInfor;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinemaapp.R;

import java.util.Arrays;
import java.util.Calendar;

public class UpdateInfoActivity extends AppCompatActivity {
    private EditText etDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        // Back button
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());

        // Date of Birth field with DatePicker
        etDob = findViewById(R.id.et_dob);
        etDob.setOnClickListener(v -> showDatePickerDialog());

        // Set up Gender Spinner
        Spinner spinnerGender = findViewById(R.id.spinner_gender);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Arrays.asList("Nam", "Nữ", "Khác")
        );
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);
        spinnerGender.setSelection(0); // Default to "Nam"

        // Set up Province Spinner
        Spinner spinnerProvince = findViewById(R.id.spinner_province);
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Arrays.asList("Hà Nội", "TP. Hồ Chí Minh", "Đà Nẵng", "Hải Phòng", "Cần Thơ")
        );
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(provinceAdapter);
        spinnerProvince.setSelection(0); // Default to "Hà Nội"

        // Update button
        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(v -> {
            // Get values from fields
            EditText etEmail = findViewById(R.id.et_email);
            EditText etLastName = findViewById(R.id.et_last_name);
            EditText etFirstName = findViewById(R.id.et_first_name);
            EditText etPhone = findViewById(R.id.et_phone);
            EditText etAddress = findViewById(R.id.et_address);

            String email = etEmail.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String firstName = etFirstName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String dob = etDob.getText().toString().trim();
            String gender = spinnerGender.getSelectedItem().toString();
            String province = spinnerProvince.getSelectedItem().toString();
            String address = etAddress.getText().toString().trim();

            // Basic validation
            if (email.isEmpty() || lastName.isEmpty() || firstName.isEmpty() || phone.isEmpty() ||
                    dob.isEmpty() || address.isEmpty()) {
                Toast.makeText(UpdateInfoActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                // Simulate successful update (replace with real update logic)
                Toast.makeText(UpdateInfoActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish(); // Return to UserInfoActivity
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the selected date as DD/MM/YYYY
                    String selectedDate = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    etDob.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}