package com.example.carboncompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetGoal extends AppCompatActivity {
    private Button submitButton;
    private Button cancelButton;
    private EditText newGoal;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);

        submitButton = findViewById(R.id.submitGoalButton);
        cancelButton = findViewById(R.id.cancelGoalButton);
        newGoal = findViewById(R.id.editGoal);
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(User.class.getSimpleName()).child(user.getUid()).child("goal").setValue(Integer.valueOf(newGoal.getText().toString()));
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });


    }
}