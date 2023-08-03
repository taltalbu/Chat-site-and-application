package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.component.ToastTop;

import java.lang.reflect.Field;
import java.util.Locale;

public class SettingActivity extends AppCompatActivity {
    private EditText serverAddressEditText;
    private TextView displayServerTextView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        serverAddressEditText = findViewById(R.id.server_address_edittext);
        displayServerTextView = findViewById(R.id.display_server);
        displayServerTextView.setText("Server address is:" + Url.getInstance().getUrl());
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            if (saveSettings()) {
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        Button themeButton = findViewById(R.id.theme_button);
        themeButton.setOnClickListener(v -> {
            toggleNightMode();
            onBackPressed();
        });
        updateThemeButtonLabel();
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private boolean saveSettings() {
        String url = serverAddressEditText.getText().toString();
        String regex = "^http://.*$";
        ToastTop server_not_valid = new ToastTop(getApplicationContext(),
                "not valid server address, adrres must include http://...");
        if (url.matches(regex)) {
            Url.getInstance().setUrl(url);
            displayServerTextView.setText(url);
            return true;
        } else {
            server_not_valid.activateToast();
            return false;
        }
    }

    private void toggleNightMode() {
        int currentNightMode = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void updateThemeButtonLabel() {
        Button themeButton = findViewById(R.id.theme_button);
        int currentNightMode = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_NO) {
            themeButton.setText("Night Mode");

        } else {
            themeButton.setText("Day Mode");
        }
    }
}