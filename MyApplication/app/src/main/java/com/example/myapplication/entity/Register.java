package com.example.myapplication.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Register {
    @PrimaryKey(autoGenerate=true)
    private int id;

    private String username;
    private String password;
    private String displayName;

    private String profilePic;

    // Constructors
    public Register(String username,String password,String displayName,String profilePic) {
        this.displayName = displayName;
        this.password = password;
        this.username = username;
        this.profilePic = profilePic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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
