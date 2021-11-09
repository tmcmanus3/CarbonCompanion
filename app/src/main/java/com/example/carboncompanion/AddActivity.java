package com.example.carboncompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationBarView;

public class AddActivity extends AppCompatActivity {

    private NavigationBarView bottomNavigationView;
    private Button walkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setOnItemSelectedListener(bottomNavFunction);

        // button to log a walk
        walkBtn = findViewById(R.id.walk);
        walkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WalkActivity.class));
            }
        });
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
}