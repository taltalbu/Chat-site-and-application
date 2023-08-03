package com.example.myapplication.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.Dao.ContactDao;
import com.example.myapplication.Dao.MessageDao;
import com.example.myapplication.DataBase.DataBase;
import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.api.MessageAPI;
import com.example.myapplication.entity.Contact;
import com.example.myapplication.entity.Message;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MessageRepository {
    private final MessageDao messagesDao;
    private final MessageAPI messageAPI;
    private final String token;
    private MessageListData messageListData;
    private DataBase db;
    private String chatID;
    public MessageRepository(Context context, String token,String chatID) {
        this.token = token;
        this.chatID = chatID;
        db = Room.databaseBuilder(context, DataBase.class, "messages"
                + chatID)
                .allowMainThreadQueries().build();
        messagesDao = db.messageDao();
        messageListData = new MessageListData();
        messageAPI = new MessageAPI(messageListData,messagesDao,context);


    }

    class MessageListData extends MutableLiveData<ArrayList<Message>> {
        public MessageListData() {
            super();
            setValue(listToArrayList(messagesDao.index()));
        }

        @Override
        protected void onActive() {
            super.onActive();

           messageAPI.get(token,chatID);
        }
    }
    public void closeDB() {
        db.close();
    }


    public LiveData<ArrayList<Message>> getAll() {
        return messageListData;
    }

    public void add(final String message) {
        messageAPI.add(token,message,chatID);
    }

    public void reload() {
        messageAPI.get(token,chatID);
    }

    public static ArrayList<Message> listToArrayList(List<Message> myList) {
        ArrayList<Message> arl = new ArrayList<>();
        for (Message object : myList) {
            arl.add((Message) object);
        }
        return arl;

    }
    public void addToDao(Message message) {
        new Thread(() -> {
            ArrayList<Message> currentMessages = messageListData.getValue();
            currentMessages.add(message);
            if(messagesDao.get(message.getId()) == null) {
                messagesDao.insert(message);
            }
            messageListData.postValue(currentMessages);
        }).start();
    }
    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

}

