package com.example.carboncompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class WalkActivity extends AppCompatActivity {
    public static int walkCounter;
    private Button walkBtn, backBtn;
    private EditText walkDist;
    private FirebaseAuth fAuth;

    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        walkBtn = findViewById(R.id.submitWalkDist);
        backBtn = findViewById(R.id.back);
        walkDist = findViewById(R.id.walkDist);
        fAuth = FirebaseAuth.getInstance();

        user = fAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        mDatabase = db.getReference();

        // Go back to add activity page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });


        // TODO update carbon multiplier. It's set at 0.1 rn
        // this is supposed to log the walk to the database
        walkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double distance = Double.parseDouble(walkDist.getText().toString());
                DecimalFormat df = new DecimalFormat("#.##");
                if (distance > 0) {
                    Toast.makeText(WalkActivity.this, "" + Double.valueOf(df.format(distance*0.1)) + " kg CO2 saved",Toast.LENGTH_SHORT).show();
                    addWalk(String.valueOf(distance), Double.valueOf(df.format(distance*0.1)));
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                } else {
                    Toast.makeText(WalkActivity.this, "Distance must be greater than 0" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // Update database
    // Need to increase total number of activities by 1. and total carbon saved by the passed argument
    private void addWalk(String distance, double carbon) {
        walkCounter = 1;
        long curr = System.currentTimeMillis();
        Activity act = new Activity(1, distance);
        DatabaseReference curr_node = mDatabase.child("activities").child(String.valueOf(curr));
        curr_node.child("user_name").setValue(user.getDisplayName());
        curr_node.child("user_id").setValue(user.getUid());
        curr_node.child("activity_string").setValue(act.toString());

        DatabaseReference child = mDatabase.child(User.class.getSimpleName()).child(user.getUid());
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User curr = snapshot.getValue(User.class);
                curr.addActivity(1, distance);
                curr.addCarbonSaved(carbon);
                child.setValue(curr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", "database error ", error.toException());
            }
        });


    }

    public int getWalkCounter(){
        return walkCounter;
    }
}