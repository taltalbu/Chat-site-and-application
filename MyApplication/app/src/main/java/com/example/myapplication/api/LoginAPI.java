package com.example.myapplication.api;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.MainActivity;
import com.example.myapplication.Url;
import com.example.myapplication.chatActivity;
import com.example.myapplication.entity.UserLogin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {

    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private String token;
    private final MutableLiveData<Integer> status;
    private String baseUrl;
    public LoginAPI() {
        baseUrl = Url.getInstance().getUrl();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        status = new MutableLiveData<>();

    }

    public String getToken() {
        return token;
    }

    public void login(String phoneToken,UserLogin userLogin) {
        Call<String> call = webServiceAPI.getToken(phoneToken,userLogin);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    // Token retrieved successfully
                    token = response.body();
                } else {
                    token = null;
                }
                status.setValue(response.code());
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                // Handle network failure
                status.setValue(-1);
            }
        });
    }

    public MutableLiveData<Integer> getStatus() {
        return status;
    }
}
