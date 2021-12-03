package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    static DatabaseHelper databaseHelper;

    TextView forgotPasswordB;
    EditText emailInput, passwordInput;
    Button signUpB, signInB;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        emailInput = findViewById(R.id.SI_emailInput);
        passwordInput = findViewById(R.id.SI_passwordInput);
        forgotPasswordB = findViewById(R.id.forgotPasswordB);
        signUpB = findViewById(R.id.SI_signUpB);
        signInB = findViewById(R.id.SI_signInB);

        signInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start by looking for user that matches email
                user = databaseHelper.getUser(emailInput.getText().toString());

                // Make sure user exists
                if (user.getEmail() == null) {
                    Toast.makeText(getApplicationContext(), "Account not found.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Make sure password matches
                if (!user.getPassword().equals(passwordInput.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Invalid password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Otherwise, allow user to go through
                Intent homeIntent = new Intent(MainActivity.this, Home.class);
                homeIntent.putExtra("user", user);

                startActivityForResult(homeIntent, 1);
            }
        });

        forgotPasswordB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}