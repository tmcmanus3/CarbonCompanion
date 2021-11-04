package com.example.carboncompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText lEmail, lPassword;
    //private FirebaseAuth firebaseAuth;
    private Button login, register;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i("message", "oncreate");
        //firebaseAuth = FirebaseAuth.getInstance();
        login = (Button) findViewById(R.id.login_button);
        register = (Button) findViewById(R.id.registerFromLogin);
        lPassword = findViewById(R.id.login_password);
        lEmail = findViewById(R.id.login_email);

        dbHelper = new DatabaseHelper(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper.checkUser(lEmail.toString(), lPassword.toString())) {
                    Log.i("sheee","this shouldnt work");
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    intent.putExtra("EMAIL", lEmail.toString().trim());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                    Log.i("failure message", "fail");
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });
    }


}