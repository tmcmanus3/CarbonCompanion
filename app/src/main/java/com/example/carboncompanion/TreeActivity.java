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

public class TreeActivity extends AppCompatActivity {
    private Button treeBtn, backBtn;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);
        treeBtn = findViewById(R.id.submitTree);
        backBtn = findViewById(R.id.back);
        fAuth = FirebaseAuth.getInstance();

        // Go back to add activity page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });


        // TODO update carbon multiplier
        // this is supposed to log the tree
        treeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //make a meaningful value
                Toast.makeText(TreeActivity.this, "# kg of carbon saved" ,Toast.LENGTH_SHORT).show();
                addTree(); //no args, unsure what to put here
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
                }
        });

    }

    // Update database
    // Need to increase total number of activities by 1. and total carbon saved by the passed argument
    private void addTree() {


    }

}