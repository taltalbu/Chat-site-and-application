package com.example.myapplication.entity;

import androidx.room.PrimaryKey;

public class UserLogin {
    @PrimaryKey(autoGenerate=true)
    private int id;

    private String username;
    private String password;


    // Constructors
    public UserLogin(String username,String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
