package com.example.cinemaapp.UserInfor;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinemaapp.MainActivity;
import com.example.cinemaapp.R;
import com.example.cinemaapp.api.AuthAPI;
import com.example.cinemaapp.client.APIClient;
import com.example.cinemaapp.dto.request.SignInRequestDTO;
import com.example.cinemaapp.factory.GeneralResponse;
import com.example.cinemaapp.fragment.UserInfoActivity;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvRegisterNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Đổi tên nếu bạn đặt khác

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        tvRegisterNow = findViewById(R.id.tv_register_now);
        tvRegisterNow.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                SignInRequestDTO requestDTO = new SignInRequestDTO(username, password);

                AuthAPI apiService = APIClient.getClient().create(AuthAPI.class);
                apiService.login(requestDTO).enqueue(new retrofit2.Callback<GeneralResponse<String>>() {
                    @Override
                    public void onResponse(retrofit2.Call<GeneralResponse<String>> call, Response<GeneralResponse<String>> response) {
                        if (response.isSuccessful()) {
                            GeneralResponse<String> generalResponse = response.body();
                        if (generalResponse != null) {
                            Toast.makeText(LoginActivity.this, generalResponse.getData(), Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish(); // Đóng LoginActivity
                        }
                    }
                    @Override
                    public void onFailure(retrofit2.Call<GeneralResponse<String>> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                });
            }
        });

        tvForgotPassword.setOnClickListener(v -> {
            Toast.makeText(this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
        });

        // Nút chuyển đến trang đăng ký
        findViewById(R.id.btn_register).setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
