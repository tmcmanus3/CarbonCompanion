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

public class RecycleActivity extends AppCompatActivity {
    private Button recycleBtn, backBtn;
    private EditText recycleItem;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        recycleBtn = findViewById(R.id.submitRecycle);
        backBtn = findViewById(R.id.back);
        recycleItem = findViewById(R.id.recycleItem);
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
        // this is supposed to log the recycling to the database
        recycleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = recycleItem.getText().toString();
                if (item.length() > 0) {
                    Toast.makeText(RecycleActivity.this, "# kg CO2 saved",Toast.LENGTH_SHORT).show();
                    addRecycle(item); //No args, unsure what to put here
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                } else {
                    Toast.makeText(RecycleActivity.this, "Enter what you recycled!" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // Update database
    // Need to increase total number of activities by 1. and total carbon saved by the passed argument
    private void addRecycle(String item) {
        long curr = System.currentTimeMillis();
        Activity act = new Activity(5, item);
        DatabaseReference curr_node = mDatabase.child("activities").child(String.valueOf(curr));
        curr_node.child("user_name").setValue(user.getDisplayName());
        curr_node.child("user_id").setValue(user.getUid());
        curr_node.child("activity_string").setValue(act.toString());

        DatabaseReference child = mDatabase.child(User.class.getSimpleName()).child(user.getUid());
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User curr = snapshot.getValue(User.class);
                curr.addActivity(5, item);
                child.setValue(curr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", "database error ", error.toException());
            }
        });

    }

}