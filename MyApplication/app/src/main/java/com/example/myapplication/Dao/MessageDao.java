package com.example.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.Contact;
import com.example.myapplication.entity.Message;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface MessageDao {
    @Query("SELECT * FROM message")
    List<Message> index();
    @Query("SELECT * FROM message WHERE id = :id")
    Message get(int id);

    @Insert
    void insert(Message... messages);

    @Update
    void update(Message... messages);

    @Delete
    void delete(Message... messages);

    @Query("DELETE FROM message")
    void clear();

//    @Query("DROP TABLE IF EXISTS tableName")
//    void dropTable(String tableName);
}
