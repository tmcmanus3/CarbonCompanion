package com.example.carboncompanion;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {
    private TextView mFullName, mEmail, mPhone, changeSettings;
    private FirebaseAuth fAuth;
    private FirebaseUser user;

    private NavigationBarView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        mFullName = findViewById(R.id.nameView);
        mEmail = findViewById(R.id.emailView);
        mPhone = findViewById(R.id.phoneNumber);
        changeSettings = findViewById(R.id.changeSettings);

        // display name
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            //  String number = user.getPhoneNumber();

            mFullName.setText("Name: " + name);
            mEmail.setText("Email: " + email);
            // mPhone.setText("Phone number: "+number);
        }

        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnItemSelectedListener(bottomNavFunction);
        // Note : this code was causing the app to crash. so I removed it.

//        if (savedInstanceState == null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.settings, new SettingsFragment())
//                    .commit();
//        }
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        changeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // TODO: Make a new class to ask user if they want to change email, username, PW, etc.
             //   startActivity(startActivity(new Intent(getApplicationContext(), ChangeSettingsActivity.class));
            }
        });
    }

//    public static class SettingsFragment extends PreferenceFragmentCompat {
//        @Override
//        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//            setPreferencesFromResource(R.xml.root_preferences, rootKey);
//        }
//    }


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
}