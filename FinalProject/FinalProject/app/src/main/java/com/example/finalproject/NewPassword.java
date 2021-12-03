package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewPassword extends AppCompatActivity {
    EditText newPasswordInput, confirmNewPassInput;
    Button resetPasswordB;
    TextView np_signInB;

    User user;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        Intent newPassIntent = getIntent();
        user = (User) newPassIntent.getSerializableExtra("user");
        databaseHelper = MainActivity.databaseHelper;

        newPasswordInput = findViewById(R.id.newPasswordInput);
        confirmNewPassInput = findViewById(R.id.confirmNewPassInput);
        resetPasswordB = findViewById(R.id.resetPasswordB);
        np_signInB = findViewById(R.id.np_signInB);

        resetPasswordB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make sure fields are filled
                if (newPasswordInput.getText().toString().equals("")
                        || confirmNewPassInput.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Do not leave any field empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Make sure both passwords match
                if (!newPasswordInput.getText().toString().equals(confirmNewPassInput.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isUpdated = databaseHelper.updateUser(user.getUserId(), user.getFirstName(),
                        user.getLastName(), user.getPhoneNumber());

                if (isUpdated) {
                    Toast.makeText(getApplicationContext(), "Password successfully updated!",
                            Toast.LENGTH_SHORT).show();
                    Intent signInIntent = new Intent(NewPassword.this, MainActivity.class);
                    startActivity(signInIntent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Unsuccessful update", Toast.LENGTH_SHORT).show();
            }
        });

        np_signInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = new Intent(NewPassword.this, MainActivity.class);
                startActivity(signInIntent);
            }
        });
    }
}