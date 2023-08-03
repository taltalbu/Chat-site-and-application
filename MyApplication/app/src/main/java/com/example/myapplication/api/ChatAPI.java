// com.example.myapplication.api.ChatAPI
package com.example.myapplication.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Dao.ContactDao;
import com.example.myapplication.Url;
import com.example.myapplication.component.ToastTop;
import com.example.myapplication.entity.Contact;
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

public class ChatAPI {

    private Retrofit retrofit;
    private final WebServiceAPI webServiceAPI;
    private final MutableLiveData<ArrayList<Contact>> contacts;
    private final ContactDao contactDao;
    private String baseUrl;
    private final Context context;

    public ChatAPI(Context context, MutableLiveData<ArrayList<Contact>> contacts, ContactDao contactDao) {
        this.contacts = contacts;
        this.context = context;
        this.contactDao = contactDao;
        baseUrl = Url.getInstance().getUrl();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void getChats(String token) {
        String auth = "Bearer " + token;
        Call<ArrayList<Contact>> call = webServiceAPI.getChats(auth);
        call.enqueue(new Callback<ArrayList<Contact>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Contact>> call, @NonNull Response<ArrayList<Contact>> response) {
                new Thread(() -> {
                    if (response.isSuccessful()) {
                        contactDao.clear();
                        contactDao.insertList(response.body());
                        // Fetch the updated data from the database
                        contacts.postValue(listToArrayList(contactDao.index()));

                    } else {
                        contacts.postValue(new ArrayList<>());
                    }
                }).start();

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Contact>> call, @NonNull Throwable t) {
                // Handle failure
                new Handler(Looper.getMainLooper()).post(() -> {
                    ToastTop not_exist = new ToastTop(context,
                            "please check your server and then sign in again");
                    not_exist.activateToast();
                });
            }
        });
    }

    public void addContactChat(String token, String username) {
        String auth = "Bearer " + token;
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", username);
        requestBody.put("fromphone", "addcontact");
        Call<Contact> call = webServiceAPI.addContact(auth, requestBody);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                new Thread(() -> {
                    if (response.isSuccessful()) {
                        getChats(token);
                    } else {
                        if (response.code() == 400) {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                ToastTop not_exist = new ToastTop(context, "user is not exist");
                                not_exist.activateToast();
                            });
                        } else if(response.code() == 401) {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                ToastTop not_exist = new ToastTop(context,
                                        "please sign again");
                                not_exist.activateToast();
                            });
                        }
                    }
                }).start();
            }
            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    ToastTop not_exist = new ToastTop(context,
                            "please check your server and then try again");
                    not_exist.activateToast();
                });
            }
        });
    }
    public static ArrayList<Contact> listToArrayList(List<Contact> myList) {
        ArrayList<Contact> arl = new ArrayList<>();
        for (Contact object : myList) {
            arl.add((Contact) object);
        }
        return arl;
    }

//delete function for the next time ;-)
//    public void delete(String token, String chatID) {
//        String auth = "Bearer " + token;
//        Call<Contact> call = webServiceAPI.deleteContact(auth, Collections.singletonMap("id", chatID));
//        call.enqueue(new Callback<Contact>() {
//            @Override
//            public void onResponse(Call<Contact> call, Response<Contact> response) {
//                new Thread(() -> {
//                    if (response.isSuccessful()) {
//                        //implement for success
//                    } else {
//                        //implement for failure
//                    }
////                    status = response.code();
//                }).start();
//            }
//
//            @Override
//            public void onFailure(Call<Contact> call, Throwable t) {
//                // Handle failure
//            }
//        });
//    }

}
