package com.example.myapplication.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.entity.Message;
import com.example.myapplication.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

public class MessagesViewModel extends ViewModel {
    private MessageRepository mRepository;
    private LiveData<ArrayList<Message>> messages;

    public MessagesViewModel(Context context, String token,String chatID) {
        mRepository = new MessageRepository(context,token,chatID);
        messages = mRepository.getAll();
    }
    public void addMessageToDao(Message message){mRepository.addToDao(message);}
    public LiveData<ArrayList<Message>> get() {
        LiveData<ArrayList<Message>> l = messages;
        return messages;
    }

    public void addMessage(String message) {
        mRepository.add(message);
    }

    public void reload() {
        mRepository.reload();
    }

    public void closeDBRepository() {
        mRepository.closeDB();
    }

}

