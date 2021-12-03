package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.load.model.ModelLoader;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;

public class SpecificRide extends AppCompatActivity {
    TextView SR_startPoint, SR_endPoint;
    EditText SR_waitTime, SR_timeOfArrival, SR_tripRate;
    Button SR_checkoutB, SR_cancelB;

    User user;
    String passengers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_ride);

        Intent specificRideIntent = getIntent();
        user = (User) specificRideIntent.getSerializableExtra("user");
        String pickup = specificRideIntent.getStringExtra("pickup");
        String destination = specificRideIntent.getStringExtra("destination");
        String pickupLL = specificRideIntent.getStringExtra("pickupLL");
        String destinationLL = specificRideIntent.getStringExtra("destinationLL");
        passengers = specificRideIntent.getStringExtra("passengers");

        SR_startPoint = findViewById(R.id.SR_startPoint);
        SR_endPoint = findViewById(R.id.SR_endPoint);
        SR_waitTime = findViewById(R.id.SR_waitTime);
        SR_timeOfArrival = findViewById(R.id.SR_timeOfArrival);
        SR_tripRate = findViewById(R.id.SR_tripRate);
        SR_checkoutB = findViewById(R.id.SR_checkoutB);
        SR_cancelB = findViewById(R.id.SR_cancelB);


        // Set location and destination
        SR_startPoint.setText("From: " + pickup);
        SR_endPoint.setText("To: " + destination);

        // Set time / cost
        double distance = calcDistance(pickupLL, destinationLL);
        double cost = calcCost(distance);
        String formatCost = String.format("%.2f", cost) + "$";

        int waitTime = calcWaitTime(distance);

        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, waitTime);
        Date time = now.getTime();

        SR_tripRate.setText(formatCost);
        SR_waitTime.setText(waitTime + " minutes");
        SR_timeOfArrival.setText(String.valueOf(time));

        SR_checkoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkoutIntent = new Intent(SpecificRide.this, Checkout.class);

                checkoutIntent.putExtra("user", user);
                checkoutIntent.putExtra("pickup", pickup);
                checkoutIntent.putExtra("destination", destination);
                checkoutIntent.putExtra("estimateTime", waitTime);
                checkoutIntent.putExtra("cost", cost);

                startActivityForResult(checkoutIntent, 1);
            }
        });

        // If cancel is clicked
        SR_cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(SpecificRide.this, Home.class);
                homeIntent.putExtra("user", user);
                startActivityForResult(homeIntent, 1);
            }
        });
    }

    // Calculate distance between start and end
    public double calcDistance(String source, String destination) {
        // Get source lat / long
        String sourceLat = source.substring((source.lastIndexOf('(') + 1), source.lastIndexOf(','));
        String sourceLong = source.substring((source.lastIndexOf(',') + 1), source.lastIndexOf(')'));

        // Get destination lat / long
        String destinationLat = destination.substring((destination.lastIndexOf('(') + 1), destination.lastIndexOf(','));
        String destinationLong = destination.substring((destination.lastIndexOf(',') + 1), destination.lastIndexOf(')'));

        // Start location
        Location startPoint = new Location(LocationManager.GPS_PROVIDER);
        startPoint.setLatitude(Double.parseDouble(sourceLat));
        startPoint.setLongitude(Double.parseDouble(sourceLong));

        // End location
        Location endPoint = new Location(LocationManager.GPS_PROVIDER);
        endPoint.setLatitude(Double.parseDouble(destinationLat));
        endPoint.setLongitude(Double.parseDouble(destinationLong));

        // Note: distance is in METERS!!
        return startPoint.distanceTo(endPoint);
    }

    public double calcCost(double distanceInM) {
        // Rate per km
        final double RATE;

        if (Integer.valueOf(passengers) > 4)
            RATE = 2.50;
        else
            RATE = 1.50;

        double distanceInKm = distanceInM / 1000;
        return RATE * distanceInKm;
    }

    public int calcWaitTime(double distanceInM) {
        // Time (in mins) per km
        final int RATE = 1;

        double distanceInKm = distanceInM / 1000;
        return (int) (RATE * distanceInKm);
    }
}