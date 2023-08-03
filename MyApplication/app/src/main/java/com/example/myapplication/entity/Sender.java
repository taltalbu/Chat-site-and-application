package com.example.myapplication.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.myapplication.DataBase.ConvertItem;

@Entity

public class Sender {
    @PrimaryKey(autoGenerate=true)
    private int id;
    @TypeConverters(ConvertItem.class)
    private String username;
    @TypeConverters(ConvertItem.class)
    private String displayName;
    @TypeConverters(ConvertItem.class)
    private String profilePic;

    // Constructors
    public Sender(String username, String displayName, String profilePic) {
        this.displayName = displayName;
        this.username = username;
        this.profilePic = profilePic;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
