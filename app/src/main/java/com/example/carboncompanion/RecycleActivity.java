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

public class RecycleActivity extends AppCompatActivity {
    private Button recycleBtn, backBtn;
    private EditText recycleItem;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        recycleBtn = findViewById(R.id.submitRecycle);
        backBtn = findViewById(R.id.back);
        recycleItem = findViewById(R.id.recycleItem);
        fAuth = FirebaseAuth.getInstance();

        // Go back to add activity page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });


        // TODO update carbon multiplier
        // this is supposed to log the recycling to the database
        recycleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = recycleItem.getText().toString();
                if (item.length() > 0) {
                    Toast.makeText(RecycleActivity.this, "# kg CO2 saved",Toast.LENGTH_SHORT).show();
                    addRecycle(); //No args, unsure what to put here
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                } else {
                    Toast.makeText(RecycleActivity.this, "Enter what you recycled!" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // Update database
    // Need to increase total number of activities by 1. and total carbon saved by the passed argument
    private void addRecycle() {


    }

}