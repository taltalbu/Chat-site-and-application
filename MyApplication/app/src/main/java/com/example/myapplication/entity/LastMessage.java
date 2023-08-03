package com.example.myapplication.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.myapplication.DataBase.ConvertItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class LastMessage {
    @TypeConverters(ConvertItem.class)
    private  String sender;
    @TypeConverters(ConvertItem.class)
    private  String created;
    @TypeConverters(ConvertItem.class)
    private String content;



    public LastMessage( String created, String sender, String content) {
        this.created = created;
        this.sender = sender;
        this.content = content;
    }

    public String getId() {
        return sender;
    }

    public String getCreated() {
        return getFormatedTime(created);
    }

    public String getContent() {
        return content;
    }

    public void setId(String id) {
        this.sender = id;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setContent(String content) {
        this.content = content;
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
