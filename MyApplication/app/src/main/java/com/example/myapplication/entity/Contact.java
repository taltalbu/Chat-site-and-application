package com.example.myapplication.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.myapplication.DataBase.ConvertItem;

@Entity(tableName = "contacts")
public class Contact {
    @PrimaryKey
    @NonNull
    private String id;
    @TypeConverters(ConvertItem.class)
    private ContactUserDetails user;
    @TypeConverters(ConvertItem.class)
    private LastMessage lastMessage;

    public Contact(String id, ContactUserDetails user, LastMessage lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public ContactUserDetails getUser() {
        return user;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(ContactUserDetails user) {
        this.user = user;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

}
