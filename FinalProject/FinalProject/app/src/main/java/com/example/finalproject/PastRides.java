package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class PastRides extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    RideRecyclerViewAdapter adapter;

    ArrayList<HashMap<String, String>> allRides;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_rides);

        Intent homeIntent = getIntent();
        user = (User) homeIntent.getSerializableExtra("user");

        databaseHelper = MainActivity.databaseHelper;
        recyclerView = findViewById(R.id.rideRecyclerView);

        allRides = new ArrayList<>();

        // Get all the data
        Cursor res = databaseHelper.getAllRides(user.getUserId());
        if (res.getCount() == 0) {
            showMessage("Alert", "No data found");
            return;
        }

        String[] dates = new String[res.getCount()];
        String[] drivers = new String[res.getCount()];
        int[] durations = new int[res.getCount()];
        double[] costs = new double[res.getCount()];

        while (res.moveToNext()) {
            dates[res.getPosition()] = res.getString(1);
            durations[res.getPosition()] = res.getInt(2);
            costs[res.getPosition()] = res.getDouble(3);

            // For the driver
            int driverId = res.getInt(7);
            Driver driver = databaseHelper.getDriver(driverId);

            String driverName = driver.getFirstName() + " " + driver.getLastName();
            drivers[res.getPosition()] = driverName;

            // Fill Hashmap
            HashMap<String, String> retrievedRide = new HashMap<>();
            retrievedRide.put("date", res.getString(1));
            retrievedRide.put("driver", driverName);
            retrievedRide.put("duration", String.valueOf(res.getInt(2)));
            retrievedRide.put("cost", String.valueOf(res.getDouble(3)));

            allRides.add(retrievedRide);
        }

        adapter = new RideRecyclerViewAdapter(dates, drivers, durations, costs, PastRides.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(PastRides.this));
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}