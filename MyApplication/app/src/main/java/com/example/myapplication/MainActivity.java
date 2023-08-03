package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.installations.FirebaseInstallations;
//import com.google.firebase.installations.InstallationTokenResult;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Url.getInstance().setUrl("http://10.0.2.2:5000/api/");
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();


//        FirebaseInstallations.getInstance().getToken(true).addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstallationTokenResult>() {
//            @Override
//            public void onSuccess(InstallationTokenResult installationTokenResult) {
//                String token = installationTokenResult.getToken();
//                // Use the token as needed
//                Log.d("FCM Token", token);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                // Handle error
//                Log.e("FCM Token", "Error getting token: " + e.getMessage());
//            }
//        });

    }

}