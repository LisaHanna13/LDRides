package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    EditText emailInput, passwordInput, keywordInput;
    CheckBox checkbox;
    Button signUpB;
    TextView signInB;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseHelper = MainActivity.databaseHelper;

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        keywordInput = findViewById(R.id.keywordInput);
        checkbox = findViewById(R.id.checkbox);
        signUpB = findViewById(R.id.signUpB);
        signInB = findViewById(R.id.signInB);

        signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make sure fields are filled
                if (emailInput.getText().toString().equals("")
                        || passwordInput.getText().toString().equals("")
                        || keywordInput.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Do not leave any field empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Make sure checkbox is checked
                if (!checkbox.isChecked()) {
                    Toast.makeText(getApplicationContext(), "You must agree to the Terms of Services.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create user
                boolean isInserted = databaseHelper.insertUser("", "", "",
                        emailInput.getText().toString(), passwordInput.getText().toString(),
                        5, keywordInput.getText().toString());

                if (isInserted) {
                    Intent editProfileIntent = new Intent(SignUp.this, EditProfile.class);
                    editProfileIntent.putExtra("email", emailInput.getText().toString());

                    startActivityForResult(editProfileIntent, 1);
                }
                else
                    Toast.makeText(getApplicationContext(), "Fail :(", Toast.LENGTH_SHORT).show();
            }
        });

        signInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = new Intent(SignUp.this, MainActivity.class);
                startActivity(signInIntent);
            }
        });
    }
}