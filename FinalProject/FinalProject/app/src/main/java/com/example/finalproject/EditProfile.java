package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity {
    TextView name, email;
    EditText fNameInput, lNameInput, phoneNumInput;
    Button saveB, homeB;

    DatabaseHelper databaseHelper;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        databaseHelper = MainActivity.databaseHelper;

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        fNameInput = findViewById(R.id.fNameInput);
        lNameInput = findViewById(R.id.lNameInput);
        phoneNumInput = findViewById(R.id.phoneNumInput);
        saveB = findViewById(R.id.saveB);
        homeB = findViewById(R.id.homeB);

        Intent editProfileIntent = getIntent();
        String userEmail = editProfileIntent.getStringExtra("email");

        // Create a user with all the info
        user = databaseHelper.getUser(userEmail);

        // Display all info from user
        email.setText(userEmail);
        fNameInput.setText(user.getFirstName());
        lNameInput.setText(user.getLastName());
        phoneNumInput.setText(user.getPhoneNumber());

        // After user updates info
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated = databaseHelper.updateUser(user.getUserId(), fNameInput.getText().toString(),
                        lNameInput.getText().toString(), phoneNumInput.getText().toString(),
                        userEmail, user.getPassword(), user.getRating(), user.getKeyword());

                if (isUpdated) {
                    // Update local user
                    user = databaseHelper.getUser(userEmail);
                    name.setText(fNameInput.getText().toString() + " " + lNameInput.getText().toString());
                    fNameInput.setText(user.getFirstName());
                    lNameInput.setText(user.getLastName());
                    phoneNumInput.setText(user.getPhoneNumber());
                }
                else
                    Toast.makeText(getApplicationContext(), "Unsuccessful update", Toast.LENGTH_SHORT).show();
            }
        });

        homeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fNameInput.getText().toString().equals("")
                        || lNameInput.getText().toString().equals("")
                        || phoneNumInput.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please fill out your information first.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent homeIntent = new Intent(EditProfile.this, Home.class);

                homeIntent.putExtra("user", user);
                startActivityForResult(homeIntent, 1);
            }
        });
    }
}