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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

        Intent driverIntent = getIntent();
        user = (User) driverIntent.getSerializableExtra("user");
        driver = (Driver) driverIntent.getSerializableExtra("driver");

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

        // Set basic information
        driverName.setText(driver.getFirstName() + " " + driver.getLastName());
        driverEmail.setText(driver.getEmail());
        driverLanguages.setText(driver.getLanguages());
        driverDate.setText(driver.getDateJoined());
        driverLocation.setText(driver.getLocation());
        driverNotes.setText(driver.getOtherNotes());

        // Set hearts
        setHearts(driver.getRating());

        driver_homeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(DriverProfile.this, Home.class);
                homeIntent.putExtra("user", user);
                startActivityForResult(homeIntent, 1);
            }
        });
    }

    public void setHearts(int counter) {
        switch(counter) {
            case 0:
                heart1.setImageResource(R.drawable.light_heart);
                heart2.setImageResource(R.drawable.light_heart);
                heart3.setImageResource(R.drawable.light_heart);
                heart4.setImageResource(R.drawable.light_heart);
                heart5.setImageResource(R.drawable.light_heart);
                return;
            case 1:
                heart1.setImageResource(R.drawable.dark_heart);
                heart2.setImageResource(R.drawable.light_heart);
                heart3.setImageResource(R.drawable.light_heart);
                heart4.setImageResource(R.drawable.light_heart);
                heart5.setImageResource(R.drawable.light_heart);
                return;
            case 2:
                heart1.setImageResource(R.drawable.dark_heart);
                heart2.setImageResource(R.drawable.dark_heart);
                heart3.setImageResource(R.drawable.light_heart);
                heart4.setImageResource(R.drawable.light_heart);
                heart5.setImageResource(R.drawable.light_heart);
                return;
            case 3:
                heart1.setImageResource(R.drawable.dark_heart);
                heart2.setImageResource(R.drawable.dark_heart);
                heart3.setImageResource(R.drawable.dark_heart);
                heart4.setImageResource(R.drawable.light_heart);
                heart5.setImageResource(R.drawable.light_heart);
                return;
            case 4:
                heart1.setImageResource(R.drawable.dark_heart);
                heart2.setImageResource(R.drawable.dark_heart);
                heart3.setImageResource(R.drawable.dark_heart);
                heart4.setImageResource(R.drawable.dark_heart);
                heart5.setImageResource(R.drawable.light_heart);
                return;
            case 5:
                heart1.setImageResource(R.drawable.dark_heart);
                heart2.setImageResource(R.drawable.dark_heart);
                heart3.setImageResource(R.drawable.dark_heart);
                heart4.setImageResource(R.drawable.dark_heart);
                heart5.setImageResource(R.drawable.dark_heart);
                return;
            default:
        }
    }
}