package com.example.carboncompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carboncompanion.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private TextView welcomeMsg;
    private TextView numActivities;
    private TextView goal;
    private FirebaseUser user;
    private NavigationBarView bottomNavigationView;
    private ImageView profileImage;
    private Button changeProfileImage;
    private Button setGoal;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        welcomeMsg = findViewById(R.id.welcomeText);
        numActivities = findViewById(R.id.numberText);
        goal = findViewById(R.id.goalText);
        setGoal = findViewById(R.id.setGoal);
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseDatabase.getInstance();
        mDatabase = db.getReference(User.class.getSimpleName());

        profileImage = findViewById(R.id.profileImage);
        changeProfileImage = findViewById(R.id.changeImage);

        // if profile image already exists on firebase, download that image and set it to the ImageView
        StorageReference profileRef = storageReference.child("users/" + user.getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        // display name
        if (user != null) {
            String name = user.getDisplayName();
            welcomeMsg.setText("Welcome " + name);

            //set activity number
            DatabaseReference child = mDatabase.child(user.getUid());
            child.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User curr = snapshot.getValue(User.class);
                    int currActivities = curr.getNumActivities();
                    int currGoal = curr.getGoal();
                    numActivities.setText(String.valueOf(currActivities) + " activities");
                    if(currActivities >= currGoal) {
                        goal.setText("Goal Reached!");
                    } else {
                        goal.setText("Current goal: " + String.valueOf(currGoal));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("firebase", "database error ", error.toException());
                }
            });
        }


        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnItemSelectedListener(bottomNavFunction);

        // when the change profile image button is clicked
        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open gallery to select photo
                Intent openGalleryIntent  = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        setGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SetGoal.class));
            }
        });
        //.setOnClickListener(new View.OnClickListener()
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000) {
            if(resultCode == Activity.RESULT_OK) {
                // get image uri
                Uri imageUri = data.getData();

                // profileImage.setImageURI(imageUri);

                // upload to firebase
                uploadImageToFirebase(imageUri);
            }
        }
    }

    // upload image to firebase storage
    private void uploadImageToFirebase(Uri imageUri) {
        StorageReference fileReference = storageReference.child("users/" + user.getUid() + "/profile.jpg");
        fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Toast.makeText(ProfileActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // set the ImageView to the image downloaded from firebase
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // if upload is unsuccessful
                Toast.makeText(ProfileActivity.this, "Image could not be uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private NavigationBarView.OnItemSelectedListener bottomNavFunction = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()) {
                case R.id.settings:
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    return true;
                case R.id.add_activity:
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                    return true;
                case R.id.reward:
                    startActivity(new Intent(getApplicationContext(), RewardActivity.class));
                    return true;
                case R.id.feed:
                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                    return true;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    return true;
            }
            return false;
        }
    };

    // Logout from the app
    public void logoutFunc(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }


    //TO DO
    //set text for goal

}