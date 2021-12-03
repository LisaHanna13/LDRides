package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {
    TextView welcome, passengers;
    EditText pickupInput, destinationInput;
    RecyclerView homeRecyclerView;
    CircleImageView minusB, plusB;
    Button searchB;

    DatabaseHelper databaseHelper;
    User user;

    RecyclerView recyclerView;
    HomeRecyclerViewAdapter adapter;
    ArrayList<HashMap<String, String>> pastRides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseHelper = MainActivity.databaseHelper;

        Intent homeIntent = getIntent();
        user = (User) homeIntent.getSerializableExtra("user");

        welcome = findViewById(R.id.welcome);
        passengers = findViewById(R.id.passengers);
        pickupInput = findViewById(R.id.pickupInput);
        destinationInput = findViewById(R.id.destinationInput);
        homeRecyclerView = findViewById(R.id.homeRecyclerView);
        minusB = findViewById(R.id.minusB);
        plusB = findViewById(R.id.plusB);
        searchB = findViewById(R.id.searchB);
        recyclerView = findViewById(R.id.homeRecyclerView);

        welcome.setText("Welcome " + user.getFirstName());

        //-----------------------------------------------------------------------------------------

        // Recycler view section
        pastRides = new ArrayList<>();
        Cursor res = databaseHelper.getAllRides(user.getUserId());
        if (res.getCount() == 0) {
            showMessage("Alert", "No past rides found");
            return;
        }

        String[] pastPickups = new String[res.getCount()];
        String[] pastDestinations = new String[res.getCount()];

        while (res.moveToNext()) {
            pastPickups[res.getPosition()] = res.getString(4);
            pastDestinations[res.getPosition()] = res.getString(5);

            // Fill Hashmap
            HashMap<String, String> retrievedRide = new HashMap<>();
            retrievedRide.put("pastPickup", String.valueOf(res.getInt(4)));
            retrievedRide.put("pastDestination", String.valueOf(res.getDouble(5)));

            pastRides.add(retrievedRide);
        }

        adapter = new HomeRecyclerViewAdapter(pastPickups, pastDestinations, Home.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Home.this));

        //-----------------------------------------------------------------------------------------

        // Search for locations
        searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pickup = pickupInput.getText().toString();
                String destination = destinationInput.getText().toString();

                List<Address> pickupAddressList = null;
                List<Address> destinationAddressList = null;


                Geocoder geocoder = new Geocoder(Home.this);
                try {
                    pickupAddressList = geocoder.getFromLocationName(pickup, 6);
                    destinationAddressList = geocoder.getFromLocationName(destination, 6);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                LatLng pickupLatLng = null;
                LatLng destinationLatLng = null;

                // Pickup
                if (pickupAddressList != null)
                    for (int i = 0; i < pickupAddressList.size(); i++) {
                        Address userPickupAddress = pickupAddressList.get(i);
                        pickupLatLng = new LatLng(userPickupAddress.getLatitude(),
                                userPickupAddress.getLongitude());
                    }
                else {
                    Toast.makeText(getApplicationContext(), "Please enter a pickup address",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Destination
                if (destinationAddressList != null)
                    for (int i = 0; i < destinationAddressList.size(); i++) {
                        Address userDestinationAddress = destinationAddressList.get(i);
                        destinationLatLng = new LatLng(userDestinationAddress.getLatitude(),
                                userDestinationAddress.getLongitude());
                    }
                else {
                    Toast.makeText(getApplicationContext(), "Please enter a destination address",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Make sure addresses exist
                if (pickupLatLng == null) {
                    Toast.makeText(getApplicationContext(), "Invalid pickup address",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (destinationLatLng == null) {
                    Toast.makeText(getApplicationContext(), "Invalid destination address",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // If all is well, proceed to next page
                Intent specificRideIntent = new Intent(Home.this, SpecificRide.class);

                specificRideIntent.putExtra("user", user);
                specificRideIntent.putExtra("pickup", pickup);
                specificRideIntent.putExtra("destination", destination);
                specificRideIntent.putExtra("pickupLL", String.valueOf(pickupLatLng));
                specificRideIntent.putExtra("destinationLL", String.valueOf(destinationLatLng));
                specificRideIntent.putExtra("passengers", String.valueOf(passengers));

                startActivityForResult(specificRideIntent, 1);
            }
        });

        //-----------------------------------------------------------------------------------------

        // For number of passengers
        minusB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPassengers = Integer.parseInt(passengers.getText().toString());

                // Make sure value can't be below 1
                if (currentPassengers == 1)
                    return;

                // If not negative, decrement value
                currentPassengers--;
                passengers.setText(String.valueOf(currentPassengers));
            }
        });

        plusB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPassengers = Integer.parseInt(passengers.getText().toString());

                // Make sure value can't go over 6 people
                if (currentPassengers == 6)
                    return;

                // If not over 6, increment value
                currentPassengers++;
                passengers.setText(String.valueOf(currentPassengers));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editProfileView:
                Intent homeIntent = new Intent(Home.this, EditProfile.class);
                homeIntent.putExtra("user", user);
                startActivityForResult(homeIntent, 1);
                return true;
            case R.id.pastRidesView:
                Intent homeIntent2 = new Intent(Home.this, PastRides.class);
                homeIntent2.putExtra("user", user);
                startActivityForResult(homeIntent2, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}