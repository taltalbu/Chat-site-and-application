package com.example.myapplication.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.Dao.ContactDao;
import com.example.myapplication.DataBase.DataBase;
import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.entity.Contact;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContactRepository {

    private final ContactDao contactDao;
    private final ChatAPI chatAPI;
    private final String token;
    private DataBase db;
    private final ContactListData contactListData;
    public ContactRepository(Context context, String token,String username) {
        this.token = token;
        db = Room.databaseBuilder(context, DataBase.class, "contacts")
                .allowMainThreadQueries().build();
        contactDao = db.contactDao();
        contactListData = new ContactListData();
        chatAPI = new ChatAPI(context, contactListData,contactDao);
    }

    class ContactListData extends MutableLiveData<ArrayList<Contact>> {
        public  ContactListData() {
            super();
            List<Contact> temp = contactDao.index();
            setValue(listToArrayList(temp));
        }
        @Override
        protected void onActive() {
            super.onActive();

            chatAPI.getChats(token);
        }
    }
    public void add(String username){
        chatAPI.addContactChat(token,username);
    }


    public LiveData<ArrayList<Contact>> getAll(){return contactListData;}

//    public void delete (final String chatID){chatAPI.delete(token,chatID);}
    public void reload (){chatAPI.getChats(token);}

    public void closeDB() {
        db.close();
    }

    public static ArrayList<Contact> listToArrayList(List<Contact> myList) {
        ArrayList<Contact> arl = new ArrayList<>();
        for (Contact object : myList) {
            arl.add((Contact) object);
        }
        return arl;

    }

}
