package com.example.cinemaapp.api;

import com.example.cinemaapp.dto.request.SignInRequestDTO;
import com.example.cinemaapp.dto.request.SignUpRequestDTO;
import com.example.cinemaapp.factory.GeneralResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("/api/auth/register")
    Call<GeneralResponse<String>> register(@Body SignUpRequestDTO request);

    @POST("/api/auth/login")
    Call<GeneralResponse<String>> login (@Body SignInRequestDTO request);
}
