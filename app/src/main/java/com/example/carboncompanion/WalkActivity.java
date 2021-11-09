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

public class WalkActivity extends AppCompatActivity {
    private Button walkBtn, backBtn;
    private EditText walkDist;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        walkBtn = findViewById(R.id.submitWalkDist);
        backBtn = findViewById(R.id.back);
        walkDist = findViewById(R.id.walkDist);
        fAuth = FirebaseAuth.getInstance();

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
                    addWalk(Double.valueOf(df.format(distance*0.1)));
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                } else {
                    Toast.makeText(WalkActivity.this, "Distance must be greater than 0" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // Update database
    // Need to increase total number of activities by 1. and total carbon saved by the passed argument
    private void addWalk(double carbon) {


    }
}