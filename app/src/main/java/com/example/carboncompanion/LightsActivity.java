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

public class LightsActivity extends AppCompatActivity {
    private Button lightsBtn, backBtn;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights);
        lightsBtn = findViewById(R.id.submitLights);
        backBtn = findViewById(R.id.back);
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


        // TODO update carbon
        // this is supposed to log the lights to the database
        lightsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //make a meaningful value
                Toast.makeText(LightsActivity.this, "# kg of carbon saved!", Toast.LENGTH_SHORT).show();
                addLights(); //no args, not sure what to put here
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });

    }

    // Update database
    // Need to increase total number of activities by 1. and total carbon saved by the passed argument
    private void addLights() {
        long curr = System.currentTimeMillis();
        Activity act = new Activity(6, "");

        DatabaseReference curr_node = mDatabase.child("activities").child(String.valueOf(curr));
        curr_node.child("user_name").setValue(user.getDisplayName());
        curr_node.child("user_id").setValue(user.getUid());
        curr_node.child("activity_string").setValue(act.toString());

        DatabaseReference child = mDatabase.child(User.class.getSimpleName()).child(user.getUid());
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User curr = snapshot.getValue(User.class);
                curr.addActivity(6, "");
                child.setValue(curr);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", "database error ", error.toException());
            }
        });

    }
}
