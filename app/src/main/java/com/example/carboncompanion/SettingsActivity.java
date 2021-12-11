package com.example.carboncompanion;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.example.carboncompanion.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class SettingsActivity extends AppCompatActivity {
    private TextView mFullName, mEmail, mPassword, changeSettings;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private Button logOutBtn, changeProfileImage;
    private StorageReference storageReference;
    private NavigationBarView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);



        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        mFullName = findViewById(R.id.nameView);
        mEmail = findViewById(R.id.emailView);
        changeSettings = findViewById(R.id.changeSettings);
        logOutBtn = findViewById(R.id.logout);
        changeProfileImage = findViewById(R.id.changeImage);
        storageReference = FirebaseStorage.getInstance().getReference();



        // display name
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();

            mFullName.setText("Name: " + name);
            mEmail.setText("Email: " + email);
        }


        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnItemSelectedListener(bottomNavFunction);
        // Note : this code was causing the app to crash. so I removed it.

//        if (savedInstanceState == null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.settings, new SettingsFragment())
//                    .commit();
//        }
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        changeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChangeSettings.class));
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        // when the change profile image button is clicked
        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open gallery to select photo
                Intent openGalleryIntent  = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });
    }

//    public static class SettingsFragment extends PreferenceFragmentCompat {
//        @Override
//        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//            setPreferencesFromResource(R.xml.root_preferences, rootKey);
//        }
//    }

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
                        // Picasso.get().load(uri).into(profileImage);
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // if upload is unsuccessful
                Toast.makeText(SettingsActivity.this, "Image could not be uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private NavigationBarView.OnItemSelectedListener bottomNavFunction = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()) {
                case R.id.settings:
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    break;
                case R.id.add_activity:
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                    break;
                case R.id.reward:
                    startActivity(new Intent(getApplicationContext(), RewardActivity.class));
                    break;
                case R.id.feed:
                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                    break;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    break;
            }
            return true;
        }
    };
}