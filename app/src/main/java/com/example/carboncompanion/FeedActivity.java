package com.example.carboncompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FeedActivity extends AppCompatActivity {

    private NavigationBarView bottomNavigationView;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private FirebaseAuth fAuth;
    private FirebaseDatabase db;
    private TextView activityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);


        activityText = findViewById(R.id.activityText);
        db = FirebaseDatabase.getInstance();
        mDatabase = db.getReference(User.class.getSimpleName());
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        DatabaseReference child = mDatabase.child(user.getUid());
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User curr = snapshot.getValue(User.class);
                String activities = getActivities(curr.getActivities());
                activityText.setText(activities);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", "database error ", error.toException());
            }

            private String getActivities(String activities) {
                String[] split = activities.split("\n");
                StringBuilder builder = new StringBuilder();
                for(String s: split) {
                    builder.append(s + "\n");
                }
                return builder.toString();
            }
        });

        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.feed);
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
                    return true;
                case R.id.add_activity:
                    Intent intentAdd = new Intent(getApplicationContext(), AddActivity.class);
                    startActivity(intentAdd);
                    return true;
                case R.id.reward:
                    Intent intentReward = new Intent(getApplicationContext(), RewardActivity.class);
                    startActivity(intentReward);
                    return true;
                case R.id.settings:
                    Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intentSettings);
                    return true;
                case R.id.profile:
                    Intent intentProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intentProfile);
                    return true;
            }
            return false;
        }
    };
}