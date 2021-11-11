package com.example.carboncompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class AddActivity extends AppCompatActivity {

    private NavigationBarView bottomNavigationView;
    private FloatingActionButton walkBtn;
    private FloatingActionButton bikeBtn;
    private FloatingActionButton busBtn;
    private FloatingActionButton treeBtn;
    private FloatingActionButton vegBtn;
    private FloatingActionButton recycleBtn;
    private FloatingActionButton lightBtn;
    private FloatingActionButton volunteerBtn;
    private FloatingActionButton localBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.add_activity);
        bottomNavigationView.setOnItemSelectedListener(bottomNavFunction);

        // button to log a walk
        walkBtn = findViewById(R.id.walk);
        walkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WalkActivity.class));
            }
        });

        //button to log a bike ride
        bikeBtn = findViewById(R.id.bike);
        bikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), BikeActivity.class));
            }
        });
        //button to log a bus ride
        busBtn = findViewById(R.id.bus);
        busBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), BusActivity.class));
            }
        });
        //button to log planting a tree
        treeBtn = findViewById(R.id.plant_tree);
        treeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), TreeActivity.class));
            }
        });

        //button to log a vegetarian/vegan meal
        vegBtn = findViewById(R.id.vegetarian_meal);
        vegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), VegActivity.class));
            }
        });
        //button to log recycling
        recycleBtn = findViewById(R.id.recycle);
        recycleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), RecycleActivity.class));
            }
        });
        //button to log turning off the lights
        lightBtn = findViewById(R.id.lights_off);
        lightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), LightActivity.class));
            }
        });
        //button to log volunteering
        volunteerBtn = findViewById(R.id.volunteer);
        volunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), VolunteerActivity.class));
            }
        });
        //button to log shopping locally
        localBtn = findViewById(R.id.shop_local);
        localBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), LocalActivity.class));
            }
        });
    }

    private NavigationBarView.OnItemSelectedListener bottomNavFunction = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()) {
                case R.id.settings:

                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    return true;
                case R.id.add_activity:

                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                    return true;
                case R.id.reward:
                    startActivity(new Intent(getApplicationContext(), RewardActivity.class));
                    return true;
                case R.id.feed:
                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                    return true;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    return true;
            }
            return false;
        }
    };
}