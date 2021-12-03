package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
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
    static boolean proceedHome;

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
//        homeB = findViewById(R.id.homeB);

        Intent homeIntent = getIntent();
        user = (User) homeIntent.getSerializableExtra("user");

        Intent editProfileIntent = getIntent();
        String userEmail = editProfileIntent.getStringExtra("email");

        // Display all info from user
        if (user != null) {
            email.setText(user.getEmail());
        } else {
            // Create a user with all the info
            user = databaseHelper.getUser(userEmail);
            email.setText(userEmail);
        }
        fNameInput.setText(user.getFirstName());
        lNameInput.setText(user.getLastName());
        name.setText(fNameInput.getText().toString() + " " + lNameInput.getText().toString());
        phoneNumInput.setText(user.getPhoneNumber());


        // After user updates info
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fNameInput.getText().toString().equals("")
                        || lNameInput.getText().toString().equals("")
                        || phoneNumInput.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please fill out your information first.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isAlphabetic(fNameInput.getText().toString()) || !isAlphabetic(lNameInput.getText().toString())) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter a valid name u idiot sandwich.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phoneNumInput.length() != 10 && phoneNumInput.length() != 12 ||
                        !PhoneNumberUtils.isGlobalPhoneNumber(phoneNumInput.getText().toString())) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter a valid phone number idiot.", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isUpdated = databaseHelper.updateUser(user.getUserId(), fNameInput.getText().toString(),
                        lNameInput.getText().toString(), phoneNumInput.getText().toString());

                if (isUpdated) {
                    // Update local user
//                    user = databaseHelper.getUser(userEmail);
                    user.setFirstName(fNameInput.getText().toString());
                    user.setLastName(lNameInput.getText().toString());
                    user.setPhoneNumber(phoneNumInput.getText().toString());

                    Intent homeIntent = new Intent(EditProfile.this, Home.class);

                    homeIntent.putExtra("user", user);
                    startActivityForResult(homeIntent, 1);
//                    name.setText(fNameInput.getText().toString() + " " + lNameInput.getText().toString());
//                    fNameInput.setText(user.getFirstName());
//                    lNameInput.setText(user.getLastName());
//                    phoneNumInput.setText(user.getPhoneNumber());
//                    proceedHome = true;
                }
                else
                    Toast.makeText(getApplicationContext(), "Unsuccessful update", Toast.LENGTH_SHORT).show();
            }
        });

//        homeB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (fNameInput.getText().toString().equals("")
//                        || lNameInput.getText().toString().equals("")
//                        || phoneNumInput.getText().toString().equals("")) {
//                    Toast.makeText(getApplicationContext(),
//                            "Please fill out your information first.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (!proceedHome) {
//                    Toast.makeText(getApplicationContext(),
//                            "Please save your information first.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                Intent homeIntent = new Intent(EditProfile.this, Home.class);
//
//                homeIntent.putExtra("user", user);
//                startActivityForResult(homeIntent, 1);
//            }
//        });
    }

    public boolean isAlphabetic(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isAlphabetic(name.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}