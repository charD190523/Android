package com.example.cinemaapp.UserInfor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinemaapp.R;
import com.example.cinemaapp.api.AuthAPI;
import com.example.cinemaapp.client.APIClient;
import com.example.cinemaapp.dto.request.SignUpRequestDTO;
import com.example.cinemaapp.factory.GeneralResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Đổi tên nếu bạn đặt khác

        etName = findViewById(R.id.et_fullname);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_login);

        tvLogin.setOnClickListener(v -> {
            finish(); // Quay lại trang đăng nhập
        });

        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)
                    || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            } else {
                SignUpRequestDTO request = new SignUpRequestDTO(name, password, confirmPassword, email);

                AuthAPI apiService = APIClient.getClient().create(AuthAPI.class);
                apiService.register(request).enqueue(new retrofit2.Callback<GeneralResponse<String>>() {
                    @Override
                    public void onResponse(Call<GeneralResponse<String>> call, Response<GeneralResponse<String>> response) {
                        if (response.isSuccessful()) {
                            GeneralResponse<String> generalResponse = response.body();
                            if (generalResponse != null) {
                                Toast.makeText(RegisterActivity.this, generalResponse.getData(), Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish(); // Đóng RegisterActivity
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<GeneralResponse<String>> call, Throwable t) {
//                        Toast.makeText(RegisterActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("RegisterActivity", "Lỗi kết nối: " + t.getMessage());
                    }
                });
            }
        });
    }
}

