package com.example.myapplication.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Url;
import com.example.myapplication.component.ToastTop;
import com.example.myapplication.entity.Contact;
import com.example.myapplication.entity.UserDetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
        Retrofit retrofit;
        WebServiceAPI webServiceAPI;
        MutableLiveData<UserDetail> userDetailMutableLiveData;
        private  String baseUrl;
        private Context context;
        public UserAPI(Context context) {
            this.context = context;
            baseUrl = Url.getInstance().getUrl();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            webServiceAPI = retrofit.create(WebServiceAPI.class);
            userDetailMutableLiveData = new MutableLiveData<>();
        }

        public void get(String token,String username) {
            String auth = "Bearer " + token;
            Call<UserDetail> call = webServiceAPI.getUser(auth,  username);
            call.enqueue(new Callback<UserDetail>() {
                @Override
                public void onResponse(@NonNull Call<UserDetail> call, @NonNull Response<UserDetail> response) {
                    if (response.isSuccessful()) {
                        // Token retrieved successfully
                        userDetailMutableLiveData.setValue(response.body());
                    } else {
                        userDetailMutableLiveData.setValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserDetail> call, @NonNull Throwable t) {
                    // Handle network failure
                    userDetailMutableLiveData.setValue(null);
                }

            });
        }

    public MutableLiveData<UserDetail> getUserDetailMutableLiveData() {
        return userDetailMutableLiveData;
    }

}
