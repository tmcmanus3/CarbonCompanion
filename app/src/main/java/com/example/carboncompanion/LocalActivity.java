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

public class LocalActivity extends AppCompatActivity {
    private Button localBtn, backBtn;
    private EditText localItem;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        localBtn = findViewById(R.id.submitLocalItem);
        backBtn = findViewById(R.id.back);
        localItem = findViewById(R.id.localItem);
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
        // this is supposed to log the local shopping to the database
        localBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String local = localItem.getText().toString();
                if (local.length() > 0) {
                    Toast.makeText(LocalActivity.this, "# kg CO2 saved",Toast.LENGTH_SHORT).show();
                    addLocal(local); //No args, at least for now
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                } else {
                    Toast.makeText(LocalActivity.this, "Enter what you bought!" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // Update database
    // Need to increase total number of activities by 1. and total carbon saved
    private void addLocal(String item) {
        long curr = System.currentTimeMillis();
        Activity act = new Activity(8, item);
        mDatabase.child("activities").child(String.valueOf(curr)).setValue(user.getDisplayName() + " " +  act.toString());

        DatabaseReference child = mDatabase.child(User.class.getSimpleName()).child(user.getUid());
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User curr = snapshot.getValue(User.class);
                curr.addActivity(8, item);
                child.setValue(curr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", "database error ", error.toException());
            }
        });

    }

}