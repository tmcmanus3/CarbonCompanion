package com.example.carboncompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;

public class VolunteerActivity extends AppCompatActivity {
    private Button volunteerBtn, backBtn;
    private EditText volunteerTask;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        volunteerBtn = findViewById(R.id.submitVolunteer);
        backBtn = findViewById(R.id.back);
        volunteerTask = findViewById(R.id.volunteerTask);
        fAuth = FirebaseAuth.getInstance();

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
                    addVolunteer(); //No args for now at least
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                } else {
                    Toast.makeText(VolunteerActivity.this, "Enter how you volunteered!" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // Update database
    // Need to increase total number of activities by 1. and total carbon saved (could just be flat amount?)
    private void addVolunteer() {


    }

}