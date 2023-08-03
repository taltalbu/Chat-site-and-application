package com.example.myapplication;

import static com.example.myapplication.MyApplication.context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myapplication.Dao.ContactDao;
import com.example.myapplication.Dao.MessageDao;
import com.example.myapplication.DataBase.DataBase;
import com.example.myapplication.adapter.ContactListAdapter;
import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.api.UserAPI;
import com.example.myapplication.component.ToastTop;
import com.example.myapplication.entity.Contact;
import com.example.myapplication.entity.UserDetail;
import com.example.myapplication.entity.UserLogin;
import com.example.myapplication.fireBase.singletonFirebase;
import com.example.myapplication.repository.ContactRepository;
import com.example.myapplication.viewmodels.ContactViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class chatActivity extends AppCompatActivity {
    private ContactViewModel contactViewModel;
    private ContactListAdapter adapter;
    private String token;
    private UserLogin user;
    UserDetail userDetail;
    private Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singletonFirebase.getIsLoginInstance().setValue(Boolean.TRUE);
        Boolean x = singletonFirebase.getIsLoginInstance().getValue();
        setContentView(R.layout.activity_chat);
        handlePutExtra();
        handleGetUser();
        String u = user.getUsername();
        // move this insteed context1
        contactViewModel = new ContactViewModel(this,token,u);
        initAdapter();
        handleSettingButton();
        handleOnClickContact();
        handleOnAddContact();
        handleOnLogOut();
        contactViewModel.getAll()
                .observe(this, contacts1 -> adapter.setContacts(contacts1));
        singletonFirebase.getFirebaseMessageInstance().observe(this, messageReciver -> contactViewModel.reloadContact());
        singletonFirebase.getFirebaseContactInstance().observe(this, contact -> contactViewModel.reloadContact());
    }

    private void handleSettingButton () {
        ImageButton btn_setting = findViewById(R.id.btnSettings);
        btn_setting.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        });
    }

    private void handlePutExtra() {
        intent = getIntent();
        token = intent.getStringExtra("token");
        user = new UserLogin(intent.getStringExtra("username")
                , intent.getStringExtra("password"));
    }

    private void handleGetUser() {
        UserAPI userAPI = new UserAPI(getApplicationContext());
        userAPI.getUserDetailMutableLiveData()
                .observe(this, userDetail1 -> {
                    if (userDetail1 != null) {
                        userDetail = userDetail1;
                    } else {
                        ToastTop t = new ToastTop(getApplicationContext(),
                                "please check your server");
                        t.activateToast();
                        finish();
                    }
                });
        userAPI.get(token,user.getUsername());
    }


    private void initAdapter() {
        ListView lstContacts = findViewById(R.id.contact_list_view);
        adapter = new ContactListAdapter(this, contactViewModel.getAll().getValue());
        lstContacts.setAdapter(adapter);
    }

    private void handleOnAddContact() {
            ImageButton btnAddContact = findViewById(R.id.btnAddContact);
            btnAddContact.setOnClickListener(view -> showAddContactModal());
    }

    private void handleOnClickContact() {
        adapter.setOnItemClickListener((parent, view, position, id) -> {
            // Handle item click event
            Contact contact = adapter.getItem(position);
            if (contact != null) {
                Intent intent = new Intent(view.getContext(), singleChatActivity.class);
                intent.putExtra("chatID",contact.getId());
                intent.putExtra("token",token);
                intent.putExtra("displayName", contact.getUser().getDisplayName());
                intent.putExtra("username",userDetail.getUsername());
                intent.putExtra("displayNameCurr",userDetail.getDisplayName());
                intent.putExtra("profilePic",contact.getUser().getProfilePic());
                view.getContext().startActivity(intent);

            }
        });
    }
    void showAddContactModal() {
        final Dialog dialog = new Dialog(chatActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.addcontactmodal);

        final EditText Newusername =  dialog.findViewById(R.id.etNewUser);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener((v)->{
            String username = Newusername.getText().toString();
            dialog.dismiss();
            if (username.equals(user.getUsername())) {
                ToastTop t = new ToastTop(getApplicationContext(),"you can't add yourself");
                t.activateToast();
            } else {
                contactViewModel.addContact(username);
            }
        });
        dialog.show(); // Add this line to show the dialog
    }
    private void handleOnLogOut() {
        ImageButton btnLogOut = findViewById(R.id.btnLog);
        btnLogOut.setOnClickListener((v) -> {
            if (contactViewModel != null) {
                contactViewModel.closeDBRepository();
                List<Contact> contacts = contactViewModel.getAll().getValue();
                if (contacts != null) {
                    for (Contact contact : contacts) {
                        if(contact!= null) {
                            new Thread(()->{
                                String tableName = "messages" + contact.getId();
                                DataBase db = Room.databaseBuilder(getApplicationContext(), DataBase.class, tableName)
                                        .build();
                                MessageDao messageDao = db.messageDao();
                                messageDao.clear();
                                db.clearAllTables();
                                db.close();
                            }).start();
                        }
                    }
                }
            }
            new Thread(()->{
                DataBase db = Room.databaseBuilder(getApplicationContext(), DataBase.class, "contacts")
                        .build();
                ContactDao contactDao = db.contactDao();
                contactDao.clear();
                db.clearAllTables();
                db.close();

            }).start();
            finish();

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        singletonFirebase.getIsLoginInstance().setValue(Boolean.TRUE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        singletonFirebase.getIsLoginInstance().setValue(Boolean.TRUE);
    }

}