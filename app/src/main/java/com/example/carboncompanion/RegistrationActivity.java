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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private EditText rUsername, rPassword, rConfirmPassword, rEmail, rConfirmEmail;
    private FirebaseAuth firebaseAuth;
    private Button register;
    private User user;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();
        rUsername = (EditText) findViewById(R.id.name);
        rPassword = (EditText) findViewById(R.id.password);
        rConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        rEmail = (EditText) findViewById(R.id.email);
        rConfirmEmail = (EditText) findViewById(R.id.confirmEmail);
        register = (Button) findViewById(R.id.register);
        user = new User();
        dbHelper = new DatabaseHelper(getApplicationContext());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setName(rUsername.toString());
                user.setEmail(rEmail.toString());
                user.setPassword(rPassword.toString());

                dbHelper.addUser(user);

                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("EMAIL", rEmail.toString().trim());
                startActivity(intent);
            }
        });


    }

}