package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {
    EditText pass_EmailInput, pass_keywordInput;
    Button pass_continueB;
    TextView pass_signInB;

    User user;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        databaseHelper = MainActivity.databaseHelper;

        pass_EmailInput = findViewById(R.id.pass_EmailInput);
        pass_keywordInput = findViewById(R.id.pass_keywordInput);
        pass_continueB = findViewById(R.id.pass_continueB);
        pass_signInB = findViewById(R.id.pass_signInB);

        pass_continueB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start by looking for user that matches email
                user = databaseHelper.getUser(pass_EmailInput.getText().toString());

                // Make sure user exists
                if (user.getEmail() == null) {
                    Toast.makeText(getApplicationContext(), "Account not found.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Make sure keyword matches
                if (!user.getKeyword().equals(pass_keywordInput.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Invalid keyword",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Otherwise, allow user to go through
                Intent newPassIntent = new Intent(ForgotPassword.this, NewPassword.class);
                newPassIntent.putExtra("user", user);

                startActivityForResult(newPassIntent, 1);
            }
        });

        pass_signInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = new Intent(ForgotPassword.this, MainActivity.class);
                startActivity(signInIntent);
            }
        });
    }
}