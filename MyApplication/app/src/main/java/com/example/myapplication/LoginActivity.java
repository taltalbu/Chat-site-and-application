package com.example.myapplication;

import static com.example.myapplication.MyApplication.context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.DataBase.DataBase;
import com.example.myapplication.api.LoginAPI;
import com.example.myapplication.component.ToastTop;
import com.example.myapplication.entity.UserLogin;
import com.example.myapplication.fireBase.singletonFirebase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {
    private UserLogin userLogin = null;
    private TextView displayServerTextView;
    private String phoneToken;
    private LoginAPI loginAPI;
    private static final int REQUEST_CODE_SETTING = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SETTING && resultCode == RESULT_OK) {
            recreate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singletonFirebase.getIsLoginInstance().setValue(Boolean.FALSE);
        setContentView(R.layout.activity_login);

//        DataBase db = Room.databaseBuilder(context, DataBase.class, "contacts" + username)
//                .allowMainThreadQueries().build();



        ImageButton btn_setting = findViewById(R.id.btnSettings);
        loginAPI = new LoginAPI();
        btn_setting.setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        });


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this, instanceIdResult -> {
            phoneToken = instanceIdResult.getToken();
        });

        Button btn_register = findViewById(R.id.register_bottun);
        btn_register.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextPassword = findViewById(R.id.editTextPassword);

        ToastTop fillFieldToast = new ToastTop(getApplicationContext(), "Please enter all fields");
        ToastTop usernameOrPasswordIncorrect = new ToastTop(getApplicationContext(), "username or password incorrect");
        Button saveButtonLogin = findViewById(R.id.saveButtonLogin);
        saveButtonLogin.setOnClickListener(view ->{
            String username = editTextName.getText().toString();
            String password = editTextPassword.getText().toString();
            if(validLogin(editTextName, editTextPassword)) {
                if (userLogin != null) {
                    userLogin.setUsername(username);
                    userLogin.setPassword(password);
                } else {
                    userLogin = new UserLogin(username, password);
                }
                loginAPI.login(phoneToken,userLogin);
            } else {
                fillFieldToast.activateToast();
            }
        });
        loginAPI.getStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer status) {
                if (status == 200) {
                    handelSeccsfullLogin(loginAPI.getToken(), userLogin);
                } else if (status == 404) {
                    usernameOrPasswordIncorrect.activateToast();
                    editTextName.setText("");
                    editTextPassword.setText("");
                } else {
                    ToastTop login = new ToastTop(getApplicationContext(), "login failed (check your server)");
                    login.activateToast();
                }
            }
        });
    }

    void handelSeccsfullLogin (String token, UserLogin userLogin) {
        Intent intent = new Intent(this, chatActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("username", userLogin.getUsername());
        intent.putExtra("password", userLogin.getPassword());
        startActivity(intent);
    }
    boolean validLogin(EditText username, EditText password) {
        return !TextUtils.isEmpty(username.getText()) && !TextUtils.isEmpty(password.getText());
    }

    @Override
    protected void onPause() {
        super.onPause();
        singletonFirebase.getIsLoginInstance().setValue(Boolean.FALSE);
    }
    @Override
    protected void onResume () {
        super.onResume();
        singletonFirebase.getIsLoginInstance().setValue(Boolean.FALSE);
    }

}
