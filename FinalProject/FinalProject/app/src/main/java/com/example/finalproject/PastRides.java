package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class PastRides extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    RideRecyclerViewAdapter adapter;

    ArrayList<HashMap<String, String>> allRides;
    User user;
    Ride ride;

    Button pr_homeB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_rides);

        Intent homeIntent = getIntent();
        user = (User) homeIntent.getSerializableExtra("user");

        databaseHelper = MainActivity.databaseHelper;
        recyclerView = findViewById(R.id.rideRecyclerView);
        pr_homeB = findViewById(R.id.pr_homeB);

        allRides = new ArrayList<>();

        // Get all the data
        Cursor res = databaseHelper.getAllRides(user.getUserId());
        if (res.getCount() == 0) {
            showMessage("Alert", "No data found");
        }

        String[] rides = new String[res.getCount()];
        String[] dates = new String[res.getCount()];
        String[] drivers = new String[res.getCount()];
        int[] durations = new int[res.getCount()];
        double[] costs = new double[res.getCount()];

        while (res.moveToNext()) {
            rides[res.getPosition()] = String.valueOf(res.getInt(0));
            dates[res.getPosition()] = res.getString(1);
            durations[res.getPosition()] = res.getInt(2);
            costs[res.getPosition()] = res.getDouble(3);

            // For the driver
            int driverId = res.getInt(7);
            String driverEmail = databaseHelper.getDriverById(driverId).getEmail();
            Driver driver = databaseHelper.getDriverByEmail(driverEmail);

            String driverName = "";

            // Only update string if values are not null
            if (driver.getFirstName() != null) {
                driverName = driver.getFirstName() + " " + driver.getLastName();
            }

            drivers[res.getPosition()] = driverName;

            // Fill Hashmap
            HashMap<String, String> retrievedRide = new HashMap<>();
            retrievedRide.put("ride", String.valueOf(res.getInt(0)));
            retrievedRide.put("date", res.getString(1));
            retrievedRide.put("driver", driverName);
            retrievedRide.put("duration", String.valueOf(res.getInt(2)));
            retrievedRide.put("cost", String.valueOf(res.getDouble(3)));

            allRides.add(retrievedRide);
        }

        adapter = new RideRecyclerViewAdapter(rides, dates, drivers, durations, costs, PastRides.this, new ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                Intent confirmationIntent = new Intent(PastRides.this, Confirmation.class);

                Ride ride = databaseHelper.getRideById(position);

                if(ride.getDriverId() == 0) {
                    Toast.makeText(getApplicationContext(), "This ride has not been completed yet!", Toast.LENGTH_SHORT).show();
                    return;
                }
                confirmationIntent.putExtra("user", user);
                confirmationIntent.putExtra("driver", databaseHelper.getDriverById(ride.getDriverId()));
                confirmationIntent.putExtra("pickup", ride.getPickup());
                confirmationIntent.putExtra("destination", ride.getDestination());
                confirmationIntent.putExtra("estimateTime", ride.getDuration());
                confirmationIntent.putExtra("cost", ride.getCost());

                startActivityForResult(confirmationIntent, 1);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(PastRides.this));

        pr_homeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHomeIntent = new Intent(PastRides.this, Home.class);

                goHomeIntent.putExtra("user", user);
                startActivityForResult(goHomeIntent, 1);
            }
        });
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}