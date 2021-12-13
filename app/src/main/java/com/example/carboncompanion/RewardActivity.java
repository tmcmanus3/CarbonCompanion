package com.example.carboncompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class RewardActivity extends AppCompatActivity {
    private NavigationBarView bottomNavigationView;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private FirebaseAuth fAuth;
    private FirebaseDatabase db;
    private TextView activityText, rewardTotal, rewardContent;
    private int numRewards;
    private int activityFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        activityText = findViewById(R.id.activityText);
        db = FirebaseDatabase.getInstance();
       // mDatabase = db.getReference();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        mDatabase = db.getReference(User.class.getSimpleName());

        rewardTotal = findViewById(R.id.rewardTotal);
        rewardContent = findViewById(R.id.rewardContent);

        // will have to create new image view per reward total (should have a max # of 12)
        ImageView imgView = (ImageView)findViewById(R.id.reward_icon);
        ImageView imgView2 = (ImageView)findViewById(R.id.reward_icon2);
        ImageView imgView3 = (ImageView)findViewById(R.id.reward_icon3);
        ImageView imgView4 = (ImageView)findViewById(R.id.reward_icon4);
        ImageView imgView5 = (ImageView)findViewById(R.id.reward_icon5);
        ImageView imgView6 = (ImageView)findViewById(R.id.reward_icon6);
        ImageView imgView7 = (ImageView)findViewById(R.id.reward_icon7);
        ImageView imgView8 = (ImageView)findViewById(R.id.reward_icon8);
        ImageView imgView9 = (ImageView)findViewById(R.id.reward_icon9);
        ImageView imgView10 = (ImageView)findViewById(R.id.reward_icon10);
        ImageView imgView11 = (ImageView)findViewById(R.id.reward_icon11);



        if (user != null) {
            DatabaseReference child = mDatabase.child(user.getUid());
            child.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User curr = snapshot.getValue(User.class);
                    int currActivities = curr.getNumActivities();
                    int currGoal = curr.getGoal();

                    StringBuilder builder = new StringBuilder();
                    ArrayList<String> rewards = new ArrayList<>();

                    // REWARD 1: 1st Activity Logged
                    if (currActivities >0 && activityFirst !=1){
                        activityFirst = 1;
                        numRewards++;
                        rewardTotal.setText("Rewards: "+ numRewards);
                        imgView.setVisibility(View.VISIBLE);
                        rewards.add("Logged first activity!");
                    }

                    // REWARD 2: Hit goal
                    if (currActivities >= currGoal) {
                        numRewards++;
                        rewardTotal.setText("Rewards: "+ numRewards); // TO DO: add rewards ++ count like in feed

                        rewards.add("Hit goal!");
                        Log.i("REWARD", rewards.toString());
                        imgView2.setVisibility(View.VISIBLE);

                    }

                    // first walk
                    String walk = "walk";
                    if (curr.getActivities().contains(walk)){
                        numRewards++;
                        rewardTotal.setText("Rewards: "+ numRewards);
                        imgView3.setVisibility(View.VISIBLE);
                        rewards.add("Logged first walk!");
                    }

                    //  Check if bussed
                    String bus = "bus";
                    if (curr.getActivities().toLowerCase().contains(bus)){
                        numRewards++;
                        rewardTotal.setText("Rewards: "+ numRewards);
                        imgView4.setVisibility(View.VISIBLE);
                        rewards.add("Logged first bus!");
                    }
                    Log.i("Print:",curr.getActivities().toString());

                    // Check if volunteered
                    String volunteer = "volunteer";
                    if (curr.getActivities().toLowerCase().contains(volunteer)){
                        numRewards++;
                        rewardTotal.setText("Rewards: "+ numRewards);
                        imgView5.setVisibility(View.VISIBLE);
                        rewards.add("First time volunteering!");
                    }

                    // Check if biked
                    String bike = "bike";
                    if (curr.getActivities().toLowerCase().contains(bike)){
                        numRewards++;
                        rewardTotal.setText("Rewards: "+ numRewards);
                        imgView6.setVisibility(View.VISIBLE);
                        rewards.add("Logged first bike ride!");
                    }

                    // check if shopped locally
                    String local = "local";
                    if (curr.getActivities().toLowerCase().contains(local)){
                        numRewards++;
                        rewardTotal.setText("Rewards: "+ numRewards);
                        imgView7.setVisibility(View.VISIBLE);
                        rewards.add("First local purchase!");
                    }

                    // check if planted a tree
                    String tree = "tree";
                    if (curr.getActivities().toLowerCase().contains(tree)){
                        numRewards++;
                        rewardTotal.setText("Rewards: "+ numRewards);
                        imgView8.setVisibility(View.VISIBLE);
                        rewards.add("Planted first tree!");
                    }

                    // check if used vegetarian options
                    String veg = "ate";
                    if (curr.getActivities().toLowerCase().contains(veg)){
                        numRewards++;
                        rewardTotal.setText("Rewards: "+ numRewards);
                        imgView9.setVisibility(View.VISIBLE);
                        rewards.add("Used vegetarian options!");
                    }
                    // check if recycled
                    String recycle = "recycle";
                    if (curr.getActivities().toLowerCase().contains(recycle)){
                        numRewards++;
                        rewardTotal.setText("Rewards: "+ numRewards);
                        imgView10.setVisibility(View.VISIBLE);
                        rewards.add("Logged first recycling!");
                    }
                    // lights
                    String lights = "lights";
                    if (curr.getActivities().toLowerCase().contains(lights)){
                        numRewards++;
                        rewardTotal.setText("Rewards: "+ numRewards);
                        imgView11.setVisibility(View.VISIBLE);
                        rewards.add("Turned off lights for the first time!");
                    }

                    // AT END: prints out all rewards
                    Collections.reverse(rewards);
                    for (String reward: rewards) {
                        Log.i("REWARD1", reward);
                        builder.append(reward + "\n");
                    }
                    rewardContent.setText(builder.toString());
                    rewardContent.setMovementMethod(new ScrollingMovementMethod());

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("firebase", "database error ", error.toException());
                }
            });
        }

        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.reward);
        bottomNavigationView.setOnItemSelectedListener(bottomNavFunction);

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