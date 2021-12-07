package com.example.carboncompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


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
        mDatabase = db.getReference();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        mDatabase.child("activities").limitToLast(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuilder builder = new StringBuilder();
                //while(snapshot.getChildren())
                ArrayList<String> activities = new ArrayList<String>();
                for (DataSnapshot s1: snapshot.getChildren()) {
                    activities.add(s1.getValue().toString());
                    //activityText.setText(s1.toString());
                }
                Collections.reverse(activities);
                for (String activity: activities) {
                    builder.append(activity + "\n");
                }
                activityText.setText(builder.toString());
                activityText.setMovementMethod(new ScrollingMovementMethod());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*Log.d("testing", "made it here");
        Query query = mDatabase.orderByChild("activities").limitToLast(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder builder = new StringBuilder();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String activity = (String) snapshot.getValue();
                    builder.append(activity + "\n");
                }
                activityText.setText(builder.toString());
                Log.d("testing", builder.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */

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