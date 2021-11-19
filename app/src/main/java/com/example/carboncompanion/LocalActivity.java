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

public class LocalActivity extends AppCompatActivity {
    private Button localBtn, backBtn;
    private EditText localItem;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        localBtn = findViewById(R.id.submitLocalItem);
        backBtn = findViewById(R.id.back);
        localItem = findViewById(R.id.localItem);
        fAuth = FirebaseAuth.getInstance();

        // Go back to add activity page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });


        // TODO update carbon multiplier
        // this is supposed to log the local shopping to the database
        localBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String local = localItem.getText().toString();
                if (local.length() > 0) {
                    Toast.makeText(LocalActivity.this, "# kg CO2 saved",Toast.LENGTH_SHORT).show();
                    addLocal(); //No args, at least for now
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                } else {
                    Toast.makeText(LocalActivity.this, "Enter what you bought!" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // Update database
    // Need to increase total number of activities by 1. and total carbon saved
    private void addLocal() {


    }

}