package com.example.carboncompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.carboncompanion.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private TextView welcomeMsg;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        welcomeMsg = findViewById(R.id.welcomeText);

        // display name
        if (user != null) {
            String name = user.getDisplayName();
            welcomeMsg.setText("Welcome " + name);
        }
    }

    public void logoutFunc(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }


    //TO DO
    //set text for goal

}