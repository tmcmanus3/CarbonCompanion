package com.example.carboncompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carboncompanion.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationActivity extends AppCompatActivity {
    private EditText mFullName, mEmail, mPassword, mPhone;
    private Button mRegisterBtn, mLoginBtn;
    private FirebaseAuth fAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mFullName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phoneNumber);
        mRegisterBtn = findViewById(R.id.register);
        mLoginBtn = findViewById(R.id.regLogin);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String name = mFullName.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();

                // check text entries
                // TODO add more tests

                if(TextUtils.isEmpty(name)) {
                    mFullName.setError("Name is required");
                    return;
                }
                if(TextUtils.isEmpty(phone)) {
                    mPhone.setError("Phone number is required");
                    return;
                }
                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }
                if(password.length() <= 6) {
                    mPassword.setError("Password must be greater than 6 characters");
                    return;
                }

                // create user
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "User created.",Toast.LENGTH_SHORT).show();

                            // set display name
                            user = fAuth.getCurrentUser();
                            //Log.d("User", user.getUid().toString());
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();

                            user.updateProfile(profileUpdates);

                            // initialize user in the database
                            User newUser = new User(email, 0, 0);
                            DAUser daUser = new DAUser();
                            daUser.add(newUser);

                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        // if login button is pressed, go back to the LoginActivity
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}