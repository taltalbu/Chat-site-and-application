package com.example.myapplication.api;

import static com.example.myapplication.repository.MessageRepository.listToArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Dao.MessageDao;
import com.example.myapplication.Url;
import com.example.myapplication.component.ToastTop;
import com.example.myapplication.entity.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {
    private Retrofit retrofit;
    private final WebServiceAPI webServiceAPI;
    private final MutableLiveData<ArrayList<Message>> messages;
    private final MessageDao messageDao;
    private String baseUrl;
    private Context context;

    public MessageAPI(MutableLiveData<ArrayList<Message>> messages,
                      MessageDao messageDao,Context context) {
        this.context = context;
        baseUrl = Url.getInstance().getUrl();
        this.messages = messages;
        this.messageDao = messageDao;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get(String token, String id) {
        String auth = "Bearer " + token;
        Call<ArrayList<Message>> call = webServiceAPI.getMessages(auth,id);
        call.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Message>> call, @NonNull Response<ArrayList<Message>> response) {
                new Thread(() -> {
                    if (response.isSuccessful()) {
                        messageDao.clear();
                        ArrayList<Message> list = response.body();
                        // Reverse the list
                        Collections.reverse(list);
                        for(Message m : list) {
                            messageDao.insert(m);
                        }
                        // Fetch the updated data from the database
                        messages.postValue(listToArrayList(messageDao.index()));
                    } else {
                        messages.setValue(new ArrayList<>());
                    }
                }).start();

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Message>> call, @NonNull Throwable t) {
                // Handle failure
                new Handler(Looper.getMainLooper()).post(() -> {
                    ToastTop not_exist = new ToastTop(context,
                            "please check your server and then try again");
                    not_exist.activateToast();
                });
            }
        });
    }

    public void add(String token, String message, String id) {
        String auth = "Bearer " + token;
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("msg", message);
        requestBody.put("fromphone","sendmessage");
        Call<Message> call = webServiceAPI.addMassage(auth,id, requestBody);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                new Thread(() -> {
                    if (response.isSuccessful()) {
                        ArrayList<Message> currentMessages = messages.getValue();
                        Message m = response.body();
                        currentMessages.add(m);
                        if(messageDao.get(m.getId()) == null) {
                            messageDao.insert(m);
                        }
                        messages.postValue(currentMessages);
                    } else {
                        int c=3;
                    }
                }).start();
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                // Handle failure
                new Handler(Looper.getMainLooper()).post(() -> {
                    ToastTop not_exist = new ToastTop(context,
                            "please check your server and then try again");
                    not_exist.activateToast();
                });
            }
        });
    }

}
