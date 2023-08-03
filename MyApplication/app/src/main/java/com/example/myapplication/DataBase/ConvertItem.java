package com.example.myapplication.DataBase;
import androidx.room.TypeConverter;

import com.example.myapplication.entity.ContactUserDetails;
import com.example.myapplication.entity.LastMessage;
import com.example.myapplication.entity.Message;
import com.example.myapplication.entity.Sender;
import com.example.myapplication.entity.UserDetail;
import com.google.gson.Gson;

import java.sql.Time;
import java.util.Date;

public class ConvertItem {

    @TypeConverter
    public static String fromContactUserDetails(ContactUserDetails userDetails) {
        // Convert the ContactUserDetails object to a string representation (e.g., JSON)
        // and return it.
        Gson gson = new Gson();
        return gson.toJson(userDetails);
    }

    @TypeConverter
    public static ContactUserDetails toContactUserDetails(String userDetailsJson) {
        // Convert the string representation back to a ContactUserDetails object
        // and return it.
        Gson gson = new Gson();
        return gson.fromJson(userDetailsJson, ContactUserDetails.class);
    }

    @TypeConverter
    public static String fromLastMessage(LastMessage lastMessage) {
        // Convert the ContactUserDetails object to a string representation (e.g., JSON)
        // and return it.
        Gson gson = new Gson();
        return gson.toJson(lastMessage);
    }

    @TypeConverter
    public static LastMessage toLastMessage(String lastMessage) {
        // Convert the string representation back to a ContactUserDetails object
        // and return it.
        Gson gson = new Gson();
        return gson.fromJson(lastMessage, LastMessage.class);
    }

    @TypeConverter
    public static UserDetail toUserDetail(String userDetail) {
        Gson gson = new Gson();
        return gson.fromJson(userDetail, UserDetail.class);
    }
    @TypeConverter
    public static String fromUserDetail(UserDetail userDetail) {
        // Convert the ContactUserDetails object to a string representation (e.g., JSON)
        // and return it.
        Gson gson = new Gson();
        return gson.toJson(userDetail);
    }

    @TypeConverter
    public static Sender toSender(String sender) {
        Gson gson = new Gson();
        return gson.fromJson(sender, Sender.class);
    }
    @TypeConverter
    public static String fromSender(Sender sender) {
        // Convert the ContactUserDetails object to a string representation (e.g., JSON)
        // and return it.
        Gson gson = new Gson();
        return gson.toJson(sender);
    }
}
