package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeDriver extends AppCompatActivity {

    TextView welcome, user, ride, pickup, destination, duration, cost;
    Button signOutB;

    DatabaseHelper databaseHelper;
    Driver driver;

    RecyclerView recyclerView;
    HomeDriverRecyclerViewAdapter adapter;
    ArrayList<HashMap<String, String>> potentialRides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_driver);

        recyclerView = findViewById(R.id.homeDriverRV);
        signOutB = findViewById(R.id.signOutDriverB);
        databaseHelper = MainActivity.databaseHelper;
        Intent homeDriverIntent = getIntent();
        driver = (Driver) homeDriverIntent.getSerializableExtra("driver");

        welcome = findViewById(R.id.welcome2);
        welcome.setText("Welcome " + driver.getFirstName());

        potentialRides = new ArrayList<>();
        listAllPotentialRides();
        //-----------------------------------------------------------------------------------------

        //-----------------------------------------------------------------------------------------

        // when user wants to log out
        signOutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(HomeDriver.this, MainActivity.class);
                startActivityForResult(loginIntent, 1);
            }
        });
    }

    private void listAllPotentialRides() {
        // Recycler view section
        Cursor res = databaseHelper.getAllUnassignedRides();

        if (res.getCount() == 0) {
            showMessage("Alert", "No rides to accept at the moment");
            return;
        }

        int[] allRides = new int[res.getCount()];
        String[] allUsers = new String[res.getCount()];
        String[] allPickups = new String[res.getCount()];
        String[] allDestinations = new String[res.getCount()];
        int[] allDurations = new int[res.getCount()];
        double[] allCosts = new double[res.getCount()];

        while (res.moveToNext()) {
            allRides[res.getPosition()] = Integer.valueOf(res.getString(0));
            allUsers[res.getPosition()] = res.getString(6);
            allPickups[res.getPosition()] = res.getString(4);
            allDestinations[res.getPosition()] = res.getString(5);
            allDurations[res.getPosition()] = Integer.valueOf(res.getString(2));
            allCosts[res.getPosition()] = Double.valueOf(res.getString(3));

            // Fill Hashmap
            HashMap<String, String> retrievedRide = new HashMap<>();
            retrievedRide.put("rideId", String.valueOf(allRides[res.getPosition()]));
            retrievedRide.put("userId", allUsers[res.getPosition()]);
            retrievedRide.put("pickup", allPickups[res.getPosition()]);
            retrievedRide.put("destination", allDestinations[res.getPosition()]);
            retrievedRide.put("duration", String.valueOf(allDurations[res.getPosition()]));
            retrievedRide.put("cost", String.valueOf(allCosts[res.getPosition()]));

            potentialRides.add(retrievedRide);
        }
        adapter = new HomeDriverRecyclerViewAdapter(allRides, allUsers, allPickups, allDestinations, allDurations, allCosts, HomeDriver.this, new ClickListener() {
            @Override public void onPositionClicked(int position) {
                // position has been programmed in Adapter to return rideId
                databaseHelper.assignDriverToRide(driver.getDriverId(), position);
                databaseHelper.assignDateToRide(position);
                listAllPotentialRides();
            }
        });

            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
    }


    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}