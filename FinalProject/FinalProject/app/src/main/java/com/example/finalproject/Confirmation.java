package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Confirmation extends AppCompatActivity {
    TextView date, driver, licensePlate, duration, totalCost, startPoint, destination;
    EditText timeBeHere;
    Button conf_rateRideB, conf_homeB;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        date = findViewById(R.id.date);
        driver = findViewById(R.id.driver);
        licensePlate = findViewById(R.id.licensePlate);
        duration = findViewById(R.id.duration);
        totalCost = findViewById(R.id.totalCost);
        timeBeHere = findViewById(R.id.timeBeHere);
        startPoint = findViewById(R.id.startPoint);
        destination = findViewById(R.id.destination);
        conf_rateRideB = findViewById(R.id.conf_rateRideB);
        conf_homeB = findViewById(R.id.conf_homeB);

        Intent checkoutIntent = getIntent();
        user = (User) checkoutIntent.getSerializableExtra("user");
        String pickup = checkoutIntent.getStringExtra("pickup");
        String destinationVal = checkoutIntent.getStringExtra("destination");
        int estimateTime = checkoutIntent.getIntExtra("estimateTime", 0);
        double cost = checkoutIntent.getDoubleExtra("cost", 0);

        Date currentDate = new Date();
        String formatDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);

        date.setText(formatDate);
        duration.setText(estimateTime + " minutes");
        totalCost.setText(cost + "$");
        timeBeHere.setText(estimateTime);
        startPoint.setText("Pickup Spot: " + pickup);
        destination.setText("Destination: " + destinationVal);

        conf_rateRideB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rateIntent = new Intent(Confirmation.this, RateRide.class);
                rateIntent.putExtra("user", user);
                startActivityForResult(rateIntent, 1);
            }
        });

        conf_homeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(Confirmation.this, Home.class);
                homeIntent.putExtra("user", user);
                startActivityForResult(homeIntent, 1);
            }
        });
    }
}