package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DriverProfile extends AppCompatActivity {
    TextView driverName, driverEmail, driverLanguages, driverDate, driverLocation, driverNotes;
    ImageView heart1, heart2, heart3, heart4, heart5;
    Button driver_homeB;

    Driver driver;
    User user;

    static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

        Intent driverIntent = getIntent();
        user = (User) driverIntent.getSerializableExtra("user");
        driver = new Driver(); // THIS NEEDS TO BE CHANGED!!!!!!!!!!!!!!!!!!!!!!!!! get driver from intent

        driverName = findViewById(R.id.driverName);
        driverEmail = findViewById(R.id.driverEmail);
        driverLanguages = findViewById(R.id.driverLanguages);
        driverDate = findViewById(R.id.driverDate);
        driverLocation = findViewById(R.id.driverLocation);
        driverNotes = findViewById(R.id.driverNotes);
        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);
        heart4 = findViewById(R.id.heart4);
        heart5 = findViewById(R.id.heart5);
        driver_homeB = findViewById(R.id.driver_homeB);

        driverName.setText(driver.getFirstName() + " " + driver.getLastName());
        driverEmail.setText(driver.getEmail());
        driverLanguages.setText(driver.getLanguages());
        driverDate.setText(driver.getDateJoined());
        driverLocation.setText(driver.getLocation());
        driverNotes.setText(driver.getOtherNotes());

        driver_homeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(DriverProfile.this, Home.class);
                homeIntent.putExtra("user", user);
                startActivityForResult(homeIntent, 1);
            }
        });
    }
}