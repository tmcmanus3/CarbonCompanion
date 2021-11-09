package com.example.carboncompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.carboncompanion.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private TextView welcomeMsg;
    private FirebaseUser user;
    private NavigationBarView bottomNav;


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

        bottomNav = findViewById(R.id.bottomnav);
        bottomNav.setOnItemSelectedListener(bottomNavFunction);
    }

    private NavigationBarView.OnItemSelectedListener bottomNavFunction = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()) {
                case R.id.settings:
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    break;
                case R.id.add_activity:
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                    break;
                case R.id.reward:
                    startActivity(new Intent(getApplicationContext(), RewardActivity.class));
                    break;
                case R.id.feed:
                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                    break;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    break;
            }
            return true;
        }
    };

    // Logout from the app
    public void logoutFunc(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }


    //TO DO
    //set text for goal

}