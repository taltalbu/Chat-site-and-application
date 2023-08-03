package com.example.myapplication.fireBase;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entity.MessageReciver;

public class singletonFirebase {
    private static MutableLiveData<String> contactFirebase;

    private static MutableLiveData<MessageReciver> messageFirebase;

    private static MutableLiveData<Boolean> isLogin;

    private singletonFirebase() {
        // Private constructor to prevent instantiation
    }

    public static synchronized MutableLiveData<String> getFirebaseContactInstance() {
        if (contactFirebase == null) {
            contactFirebase = new MutableLiveData<>();
        }
        return contactFirebase;
    }
    public static synchronized MutableLiveData<MessageReciver> getFirebaseMessageInstance() {
        if (messageFirebase == null) {
            messageFirebase =  new MutableLiveData<>();
        }
        return messageFirebase;
    }
    public static synchronized MutableLiveData<Boolean> getIsLoginInstance() {
        if (isLogin == null) {
            isLogin = new MutableLiveData<>();
        }
        return isLogin;
    }

}