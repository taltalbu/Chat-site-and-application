package com.example.myapplication.api;


import com.example.myapplication.entity.Contact;
import com.example.myapplication.entity.Message;
import com.example.myapplication.entity.Register;
import com.example.myapplication.entity.UserDetail;
import com.example.myapplication.entity.UserLogin;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @POST("Tokens")
    Call<String> getToken(@Header("phonetoken") String phoneToken,@Body UserLogin userLogin);

    @GET("Users/{username}")
    Call<UserDetail> getUser(@Header("Authorization") String token,@Path("username") String username);

    @POST("Users")
    Call<UserDetail> regestration(@Body Register register);

    @GET("Chats/:id")
    Call getUserChat();

    @GET("Chats/{id}/Messages")
    Call<ArrayList<Message>> getMessages(@Header("Authorization") String token,@Path("id") String chatID);

    @POST("Chats/{id}/Messages")
    Call<Message> addMassage(@Header("Authorization") String token,@Path("id") String id,@Body Map<String, String> message);

    @GET("Chats")
    Call <ArrayList<Contact>> getChats(@Header("Authorization") String token);

    @POST("Chats")
    Call<Contact> addContact(@Header("Authorization") String token, @Body Map<String, String> username);

    @DELETE("Chats")
    Call<Contact> deleteContact(@Header("Authorization") String token, @Body Map<String, String> id);
}
