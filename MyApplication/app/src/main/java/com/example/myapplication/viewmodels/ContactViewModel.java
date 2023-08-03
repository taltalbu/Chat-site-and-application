package com.example.myapplication.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.entity.Contact;
import com.example.myapplication.repository.ContactRepository;

import java.util.ArrayList;
import java.util.List;

public class ContactViewModel extends ViewModel {


    private ContactRepository mRepository;
    private LiveData<ArrayList<Contact>> contacts;

    public ContactViewModel(Context context,String token,String username) {
        mRepository = new ContactRepository(context,token,username);
        contacts = mRepository.getAll();
    }

    public LiveData<ArrayList<Contact>> getAll(){
        return contacts;}

    public void addContact(String username) {mRepository.add(username);}
//    public void deleteContact(String chatID) {mRepository.delete(chatID);}
    public void reloadContact() {mRepository.reload();}
    public void closeDBRepository() {
       mRepository.closeDB();
    }

}
