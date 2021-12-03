package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {
    TextView welcome, passengers;
    EditText pickupInput, destinationInput;
    RecyclerView homeRecyclerView;
    CircleImageView minusB, plusB;
    Button searchB;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        welcome.setText("Welcome " + user.getFirstName());

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

                LatLng pickupLatLng;
                LatLng destinationLatLng;

                // Pickup
                if (pickupAddressList != null)
                    for (int i = 0; i < pickupAddressList.size(); i++) {
                        Address userPickupAddress = pickupAddressList.get(i);
                        pickupLatLng = new LatLng(userPickupAddress.getLatitude(),
                                userPickupAddress.getLongitude());
                    }
                else {
                    
                }

                // Destination
                if (destinationAddressList != null)
                    for (int i = 0; i < destinationAddressList.size(); i++) {
                        Address userDestinationAddress = destinationAddressList.get(i);
                        destinationLatLng = new LatLng(userDestinationAddress.getLatitude(),
                                userDestinationAddress.getLongitude());
                    }


            }
        });

        // For number of passengers
        minusB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPassengers = Integer.parseInt(passengers.getText().toString());

                // Make sure value can't be negative
                if (currentPassengers == 0)
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