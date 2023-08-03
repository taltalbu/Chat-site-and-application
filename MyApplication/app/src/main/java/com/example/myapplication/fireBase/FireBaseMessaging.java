package com.example.myapplication.fireBase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;
import com.example.myapplication.entity.Message;
import com.example.myapplication.entity.MessageReciver;
import com.example.myapplication.entity.Sender;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class FireBaseMessaging extends FirebaseMessagingService {
    MutableLiveData<MessageReciver> newMsg;
    MutableLiveData<String> newContact;
    public FireBaseMessaging() {

        newMsg = singletonFirebase.getFirebaseMessageInstance();
        newContact = singletonFirebase.getFirebaseContactInstance();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Boolean x = singletonFirebase.getIsLoginInstance().getValue();
        if (x) {
            // Check if the message contains a data payload
            if (remoteMessage.getData().size() > 0) {
                String notificationTitle = remoteMessage.getNotification().getTitle();
                if (notificationTitle.equals("add-message")) {
                    handleOnReciveMessage(remoteMessage.getData().get("msg"));

                } else {
                    handleOnReciveContact(remoteMessage.getData().get("displayName"));
                }
            }
        }
    }

    public void handleOnReciveContact(String msgJson) {
        try {
            JSONObject msgObject = new JSONObject(msgJson);
            String displayName = msgObject.getString("displayName");
            newContact.postValue(displayName);
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("congratulation: ")
                    .setContentText(displayName + " add you as a contact")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
        } catch (Exception e) {

        }

    }

    public void handleOnReciveMessage(String msgJson) {
        try {
            JSONObject msgObject = new JSONObject(msgJson);
            String chatID = msgObject.getString("chatID");
            String created = msgObject.getString("created");
            String content = msgObject.getString("content");
            String username = msgObject.getString("username");
            String displayName = msgObject.getString("displayName");
            Sender sender = new Sender(username,displayName,"");
            MessageReciver msg = new MessageReciver(chatID,content,created,sender);
            newMsg.postValue(msg);

            //create notification
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("new message from " + displayName)
                    .setContentText(content)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());

        }catch (Exception ex) {
            int x = 1;

        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", "My channel", importance);
            channel.setDescription("Demo channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}