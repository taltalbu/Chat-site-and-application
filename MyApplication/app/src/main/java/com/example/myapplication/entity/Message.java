package com.example.myapplication.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.myapplication.DataBase.ConvertItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @TypeConverters(ConvertItem.class)
    private String created;
    @TypeConverters(ConvertItem.class)
    private String content;
    @TypeConverters(ConvertItem.class)
    private Sender sender;


    public Message(String content, String created, Sender sender) {
        this.created = created;
        this.content = content;
        this.sender = sender;
    }

    public String getCreated() {
        return getFormatedTime(created);
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }
    public String getFormatedTime(String string) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS");
        try {
            Date date = inputFormat.parse(string);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            assert date != null;
            return outputFormat.format(date);
        } catch (ParseException e) {
            return string;
        }
    }
}


