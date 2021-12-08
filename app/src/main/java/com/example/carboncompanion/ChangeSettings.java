package com.example.carboncompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carboncompanion.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeSettings extends AppCompatActivity {
    private TextView mFullName, mEmail, mPhone, changeSettings;
    private Button mSubmitBtn, mCancelBtn;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;

    private NavigationBarView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_settings);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFullName = findViewById(R.id.editName);
        mEmail = findViewById(R.id.editEmailAddress);
        mSubmitBtn = findViewById(R.id.submitChangeButton);
        mCancelBtn = findViewById(R.id.cancelChangeButton);
        changeSettings = findViewById(R.id.changeSettings);

        String name = user.getDisplayName();
        String email = user.getEmail();

        mFullName.setText(name);
        mEmail.setText(email);



        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = mFullName.getText().toString();

                //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(newName)
                        .build();

                mDatabase.child("activities").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot activity: snapshot.getChildren()) {
                            //Log.d("search_id", user.getUid());
                            //Log.d("curr_id", activity.child("user_id").getValue().toString());
                            if(activity.child("user_id").getValue().toString().equals(user.getUid())) {
                                //Log.d("old name", user.getDisplayName());
                                String actString = activity.child("activity_string").getValue().toString();
                                mDatabase.child("activities").child(activity.getKey()).child("user_name").setValue(newName);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("done", "Profile updated");
                                }
                            }
                        });




                startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                // re-routing to Feed rather than Settings or Profile bc upon going to those first,
                // name does not appear updated until you go to a different activity first

            }

        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                }
            });
    }
}