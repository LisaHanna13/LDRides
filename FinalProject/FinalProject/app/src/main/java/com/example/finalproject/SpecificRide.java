package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.load.model.ModelLoader;
import com.google.android.gms.maps.model.LatLng;

public class SpecificRide extends AppCompatActivity {
    TextView SR_startPoint, SR_endPoint;
    EditText SR_waitTime, SR_timeOfArrival, SR_tripRate;
    Button SR_checkoutB, SR_cancelB;

    User user;

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
        String passengers = specificRideIntent.getStringExtra("passengers");

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
        String formatCost = String. format("%. 2f", cost);

        SR_tripRate.setText(formatCost);

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

        return startPoint.distanceTo(endPoint);
    }

    public double calcCost(double distanceInM) {
        // Rate per km
        final double RATE = 1.50;

        double distanceInKm = distanceInM / 1000;
        return RATE * distanceInKm;
    }
}