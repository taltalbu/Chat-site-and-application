package com.example.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.Contact;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contacts")
    List<Contact> index();
    @Query("SELECT * FROM contacts WHERE id = :id")
    Contact get(int id);

    @Insert
    void insertList(ArrayList<Contact> contacts);

    @Insert
    void insert(Contact... contacts);

    @Update
    void update(Contact... contacts);

    @Delete
    void delete(Contact... contacts);
    @Query("DELETE FROM contacts")
    void clear();
}

