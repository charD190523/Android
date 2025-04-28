package com.example.cinemaapp.client;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder();

                        SharedPreferences prefs = MyApplication.getAppContext().getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
                        String token = prefs.getString("jwt_token", null);

                        if (token != null) {
                            requestBuilder.header("Authorization", "Bearer " + token);
                        }

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    })
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://172.11.241.148:8080/") // ⚡ Đổi IP máy bạn ở đây
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
