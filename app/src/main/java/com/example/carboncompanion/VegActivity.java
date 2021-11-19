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

public class VegActivity extends AppCompatActivity {
    private Button vegBtn, backBtn;
    private EditText vegMeal;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veg);
        vegBtn = findViewById(R.id.submitVegMeal);
        backBtn = findViewById(R.id.back);
        vegMeal = findViewById(R.id.vegMeal);
        fAuth = FirebaseAuth.getInstance();

        // Go back to add activity page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });


        // TODO no carbon multiplier rn
        // this is supposed to log the meal to the database
        vegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String meal = vegMeal.getText().toString();
                if (meal.length() > 0) {
                    //change to a meaningful amount of carbon
                    Toast.makeText(VegActivity.this, "# kg CO2 saved",Toast.LENGTH_SHORT).show();
                    addMeal();
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                } else {
                    Toast.makeText(VegActivity.this, "Please enter some text" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // Update database
    // Need to increase total number of activities by 1. and total carbon saved by the passed argument
    // No args, unsure what to base this carbon off of
    private void addMeal() {


    }

}