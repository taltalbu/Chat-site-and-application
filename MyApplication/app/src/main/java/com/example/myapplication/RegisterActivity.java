package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myapplication.api.RegisterAPI;
import com.example.myapplication.component.ToastTop;
import com.example.myapplication.entity.Register;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private ImageView imageView;
    private ImageButton buttonAddImage;
    private EditText et_user_name;
    private EditText et_display_name;
    private EditText et_password ;
    private EditText et_confirm_password;
    private RegisterAPI registerAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerAPI = new RegisterAPI();
        imageView = findViewById(R.id.imageView);
        buttonAddImage = findViewById(R.id.buttonAddImage);

        buttonAddImage.setOnClickListener(v -> openImagePicker());
        Button btn_login = findViewById(R.id.login_bottun);
        btn_login.setOnClickListener(view -> {
            finish();
        });
        ToastTop fillFieldToast = new ToastTop(getApplicationContext(), "Please enter all fields");
        ToastTop password_not_match = new ToastTop(getApplicationContext(), "confirm password not match");
        ToastTop username_already_exist = new ToastTop(getApplicationContext(), "username already exist");
        ToastTop not_Valid_password = new ToastTop(getApplicationContext(), "password should contain least 8 character, include letter and number");
         et_user_name = findViewById(R.id.et_user_name);
         et_display_name = findViewById(R.id.et_display_name);
         et_password = findViewById(R.id.et_password);
         et_confirm_password = findViewById(R.id.et_confirm_password);

        Button register = findViewById(R.id.register);
        register.setOnClickListener(View -> {
            if(TextUtils.isEmpty(et_display_name.getText()) || TextUtils.isEmpty(et_user_name.getText())
                || TextUtils.isEmpty(et_password.getText()) || TextUtils.isEmpty(et_confirm_password.getText())) {
                fillFieldToast.activateToast();
            } else if (!isPasswordValid(et_password.getText().toString())) {
                not_Valid_password.activateToast();
                et_confirm_password.setText("");
                et_password.setText("");
            } else if (!et_password.getText().toString().equals( et_confirm_password.getText().toString())) {
               password_not_match.activateToast();
                et_confirm_password.setText("");
            }  else if (validUserName(et_user_name)) {
              register();
            } else {
                //username not much to the terms
            }
        });
        registerAPI.getRegisterSuccesfully().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer status) {
                if (status == 200) {
                    finish();
                } else if (status == 409){
                    username_already_exist.activateToast();
                    et_user_name.setText("");
                } else if(status == -1) {
                    //get fail
                    ToastTop registration_failed = new ToastTop(getApplicationContext(), "registration failed (please check your server");
                    registration_failed.activateToast();
                }else {
                    ToastTop registration_failed = new ToastTop(getApplicationContext(), "registration failed");
                    registration_failed.activateToast();
                }
            }
        });
    }

    void register() {
        Register register1 = new Register(et_user_name.getText().toString(),
                et_password.getText().toString(),
                et_display_name.getText().toString(),
                toBase64(imageView));
        registerAPI.regestration(register1);

    }
    boolean validUserName(EditText username){
        String regex = "^[A-Za-z]{3,20}$";
        return username.getText().toString().matches(regex);

    }
    public boolean isPasswordValid(String password) {
        // Define the regex pattern for a password containing both letters and numbers
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

        // Check if the provided password matches the pattern
        return password.matches(pattern);
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK) {
                Uri imageUri = data.getData();
                imageView.setImageURI(imageUri);
                imageView.setVisibility(View.VISIBLE);
//                buttonAddImage.setVisibility(View.GONE);
            }
        }
    }

    String toBase64 (ImageView iv) {
        iv.buildDrawingCache();
        Bitmap bitmap = iv.getDrawingCache();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image=stream.toByteArray();

        String img_str = Base64.encodeToString(image, 0);
        return  "data:image/png;base64," + img_str;
    }
}