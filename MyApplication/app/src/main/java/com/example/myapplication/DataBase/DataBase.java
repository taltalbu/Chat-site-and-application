package com.example.myapplication.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

//import com.example.myapplication.Dao.MessageDao;
import com.example.myapplication.Dao.ContactDao;
import com.example.myapplication.Dao.MessageDao;
import com.example.myapplication.entity.Contact;
import com.example.myapplication.entity.Message;

@Database(entities = {Contact.class, Message.class}, version = 1)
@TypeConverters(ConvertItem.class)
public abstract class DataBase extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract MessageDao messageDao();
}
