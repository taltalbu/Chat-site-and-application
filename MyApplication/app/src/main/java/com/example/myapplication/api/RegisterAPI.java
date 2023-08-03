package com.example.myapplication.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Url;
import com.example.myapplication.entity.Register;
import com.example.myapplication.entity.UserDetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterAPI {


    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private final MutableLiveData<Integer> registerSuccesfully;
    private String baseUrl;

    public RegisterAPI() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        baseUrl = Url.getInstance().getUrl();
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        registerSuccesfully = new MutableLiveData<>();
    }

    public void regestration(Register register) {
        Call<UserDetail> call = webServiceAPI.regestration(register);
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(@NonNull Call<UserDetail> call, @NonNull Response<UserDetail> response) {
                if (response.isSuccessful()) {
                    registerSuccesfully.setValue(response.code());
                } else {
                    registerSuccesfully.setValue(response.code());
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserDetail> call, @NonNull Throwable t) {
                registerSuccesfully.setValue(-1);
            }
        });
    }

    public MutableLiveData<Integer> getRegisterSuccesfully() {
        return registerSuccesfully;
    }
}
