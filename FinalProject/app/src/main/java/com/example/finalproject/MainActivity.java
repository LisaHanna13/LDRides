package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static DatabaseHelper databaseHelper;

    TextView forgotPasswordB;
    EditText emailInput, passwordInput;
    Button signUpB, SI_signInClientB, SI_signInDriverB;

    User user;
    Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        emailInput = findViewById(R.id.SI_emailInput);
        passwordInput = findViewById(R.id.SI_passwordInput);
        forgotPasswordB = findViewById(R.id.forgotPasswordB);
        signUpB = findViewById(R.id.SI_signUpB);
        SI_signInClientB = findViewById(R.id.SI_signInClientB);
        SI_signInDriverB = findViewById(R.id.SI_signInDriverB);

//        databaseHelper.updateUser(2, "lisa", "hanna", "4131234123", "lisa", "123", 5, "berlin");

//        databaseHelper.insertRide("2010-04-23", 120, 8.42, "AGAIN", "PLEASE WORK", 2, 0);

//        databaseHelper.insertDriver("Deema", "Mohiar", "dm@hotmail.com", "1234",
//                "arabic, english", "2021-05-31","Montreal", 5,0, "i hate my job", "A123B123");

//        databaseHelper.insertDriver("Lisa", "Hanna", "lh@hotmail.com", "1234",
//                "arabic, english", "2021-05-31","Montreal", 5,0, "i hate my job", "A123B1F3");

        SI_signInDriverB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check if driver is trying to login
                driver = databaseHelper.getDriverByEmail(emailInput.getText().toString());

                if (driver.getEmail() != null) {
                    if (driver.getPassword().equals(passwordInput.getText().toString())) {
                        // Otherwise, allow user to go through
                        Intent homeDriverIntent = new Intent(MainActivity.this, HomeDriver.class);
                        homeDriverIntent.putExtra("driver", driver);

                        startActivityForResult(homeDriverIntent, 1);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid password",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // checks if user is trying to login
                else {
                    Toast.makeText(getApplicationContext(), "You are not a driver get out.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        // check if client is trying to log in
        SI_signInClientB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = databaseHelper.getUser(emailInput.getText().toString());

                if (user.getEmail() != null) {
                    // Make sure password matches
                    if (user.getPassword().equals(passwordInput.getText().toString())) {
                        // Otherwise, allow user to go through
                        Intent homeIntent = new Intent(MainActivity.this, Home.class);
                        homeIntent.putExtra("user", user);

                        startActivityForResult(homeIntent, 1);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid password",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "You are not a user get out.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
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