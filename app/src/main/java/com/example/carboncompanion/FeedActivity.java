package com.example.carboncompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

public class FeedActivity extends AppCompatActivity {

    private NavigationBarView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setOnItemSelectedListener(bottomNavFunction);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private NavigationBarView.OnItemSelectedListener bottomNavFunction = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.feed:
                    Intent intentFeed = new Intent(getApplicationContext(), FeedActivity.class);
                    startActivity(intentFeed);
                    break;
                case R.id.add_activity:
                    Intent intentAdd = new Intent(getApplicationContext(), AddActivity.class);
                    startActivity(intentAdd);
                    break;
                case R.id.reward:
                    Intent intentReward = new Intent(getApplicationContext(), RewardActivity.class);
                    startActivity(intentReward);
                    break;
                case R.id.settings:
                    Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intentSettings);
                    break;
                case R.id.profile:
                    Intent intentProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intentProfile);
                    break;
            }
            return true;
        }
    };
}