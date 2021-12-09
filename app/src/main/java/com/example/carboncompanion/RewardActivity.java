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
                        rewards.add("Logged first goal!");
                    }

                    // REWARD 2: Hit goal
                    if (currActivities >= currGoal) {
                        numRewards++;
                        rewardTotal.setText("Rewards: "+ numRewards); // TO DO: add rewards ++ count like in feed

                        rewards.add("Hit goal!");
                        Log.i("REWARD", rewards.toString());
                        imgView2.setVisibility(View.VISIBLE);

                    }

                    // TODO: Track when a user logs first activity of its type, use Activity.getActivityType()
                    //  in Activity class; REWARD 3-12: this should track if when a user logs first activity of its type
                    //  i.e. Reward: "First walk!" when first activity of Activity_Type=1 is logged

                    /**for (int i=0; i<currActivities; ){
                     // DatabaseReference curr_node = mDatabase.child("activities").child(String.valueOf(curr));
                     }**/

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