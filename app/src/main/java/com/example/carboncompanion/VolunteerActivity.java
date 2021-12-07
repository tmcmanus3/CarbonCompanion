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

public class VolunteerActivity extends AppCompatActivity {
    private Button volunteerBtn, backBtn;
    private EditText volunteerTask;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        volunteerBtn = findViewById(R.id.submitVolunteer);
        backBtn = findViewById(R.id.back);
        volunteerTask = findViewById(R.id.volunteerTask);
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


        // TODO update carbon multiplier
        // this is supposed to log the volunteer work to the database
        volunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String volunteer = volunteerTask.getText().toString();

                if (volunteer.length() > 0) {
                    //change to meaningful value
                    Toast.makeText(VolunteerActivity.this, "# kg CO2 saved",Toast.LENGTH_SHORT).show();
                    addVolunteer(volunteer); //No args for now at least
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                } else {
                    Toast.makeText(VolunteerActivity.this, "Enter how you volunteered!" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // Update database
    // Need to increase total number of activities by 1. and total carbon saved (could just be flat amount?)
    private void addVolunteer(String volunteer) {
        long curr = System.currentTimeMillis();
        Activity act = new Activity(7, volunteer);
        mDatabase.child("activities").child(String.valueOf(curr)).setValue(user.getDisplayName() + " " +  act.toString());

        DatabaseReference child = mDatabase.child(User.class.getSimpleName()).child(user.getUid());
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User curr = snapshot.getValue(User.class);
                curr.addActivity(7, volunteer);
                child.setValue(curr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", "database error ", error.toException());
            }
        });

    }

}