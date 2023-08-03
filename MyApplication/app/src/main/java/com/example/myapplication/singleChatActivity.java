package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.myapplication.adapter.MessageListAdapter;
import com.example.myapplication.component.ToastTop;
import com.example.myapplication.entity.Message;
import com.example.myapplication.entity.Sender;
import com.example.myapplication.fireBase.singletonFirebase;
import com.example.myapplication.viewmodels.MessagesViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class singleChatActivity extends AppCompatActivity {
    private TextView displayNameTextView;
    private String token;
    private String displayNameValue;
    private String username;
    private String displayNameCurr;
    private String profilePicBase64;
    private String chatID;
    private Intent intent;
    private RecyclerView messageListView;
    private MessageListAdapter messageListAdapter;
    private MessagesViewModel messagesViewModel;
    private ImageView profilePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chat);
        profilePic = findViewById(R.id.specific_user_image);
        initPutExtra();
        messageListView = findViewById(R.id.message_list_view);
        messagesViewModel = new MessagesViewModel(getApplicationContext(),token,chatID);
        initAdapter();
        handleBackToChats();
        handleSendMessage();
        messagesViewModel.get().observe(this, messages -> {
            messageListAdapter.setMessages(messages);
            messageListView.scrollToPosition(messageListAdapter.getArraySize());
        });

        singletonFirebase.getFirebaseMessageInstance().observe(this, message -> {
            if(message.getChatID().equals(chatID)) {
                messagesViewModel.addMessageToDao(new Message(
                        message.getCreated(),message.getContent(),message.getSender()));
            }
        });
    }

    private void initPutExtra() {
        displayNameTextView = findViewById(R.id.display_name_single_chat);
        intent = getIntent();
        token = intent.getStringExtra("token");
        chatID = intent.getStringExtra("chatID");
        displayNameValue = intent.getStringExtra("displayName");
        displayNameTextView.setText(displayNameValue);
        displayNameCurr = intent.getStringExtra("displayNameCurr");
        username = intent.getStringExtra("username");
        profilePicBase64 = intent.getStringExtra("profilePic");
        displayBase64Image(profilePicBase64);
    }


    public void displayBase64Image(String base64ImageString) {
        try {
            // Remove the data:image/png;base64, prefix from the base64 string
            String base64Image = base64ImageString.replace("data:image/png;base64,", "");
            // Decode the base64 string into a byte array
            byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);
            // Create a bitmap from the byte array
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            // Set the bitmap in the ImageView
            profilePic.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void initAdapter() {
        ArrayList<Message> messageList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageListView.setLayoutManager(linearLayoutManager);
        // Creating the adapter and setting it to the ListView
        messageListAdapter = new MessageListAdapter(singleChatActivity.this, messagesViewModel.get().getValue(),username);
        messageListView.setAdapter(messageListAdapter);
    }

    private void handleBackToChats() {
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            onBackPressed();
            new Thread(()->{
                if(messagesViewModel != null) {
                    messagesViewModel.closeDBRepository();
                }
            }).start();
            finish();
        });
    }

    private void handleSendMessage() {
        ImageButton btnSend = findViewById(R.id.btnSend);
        EditText etMessageSend = findViewById(R.id.etMessage);
        btnSend.setOnClickListener(view-> {
            String msg = etMessageSend.getText().toString();
            messagesViewModel.addMessage(msg);
            messageListView.scrollToPosition(messageListAdapter.getArraySize());
            etMessageSend.setText("");
        });
    }

}
