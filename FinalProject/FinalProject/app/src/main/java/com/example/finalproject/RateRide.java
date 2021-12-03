package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class RateRide extends AppCompatActivity {
    Button viewDriverB, rate_submitB;
    EditText comments;
    CircleImageView r_minusB, r_plusB;
    ImageView r_heart1, r_heart2, r_heart3, r_heart4, r_heart5;
    static int counter;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_ride);

        Intent rateIntent = getIntent();
        user = (User) rateIntent.getSerializableExtra("user");

        viewDriverB = findViewById(R.id.viewDriverB);
        rate_submitB = findViewById(R.id.rate_submitB);
        comments = findViewById(R.id.comments);
        r_minusB = findViewById(R.id.r_minusB);
        r_plusB = findViewById(R.id.r_plusB);
        r_heart1 = findViewById(R.id.r_heart1);
        r_heart2 = findViewById(R.id.r_heart2);
        r_heart3 = findViewById(R.id.r_heart3);
        r_heart4 = findViewById(R.id.r_heart4);
        r_heart5 = findViewById(R.id.r_heart5);

        viewDriverB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ADD GODDAMN INTENT HERE with the driver passed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                Intent driverIntent = new Intent(RateRide.this, DriverProfile.class);
                driverIntent.putExtra("user", user);
                startActivityForResult(driverIntent, 1);
            }
        });

        r_minusB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make sure counter is not below 0
                if (counter == 0 )
                    return;

                counter--;
                setHearts(counter);
            }
        });

        r_plusB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make sure counter is not above 5
                if (counter == 5)
                    return;

                counter++;
                setHearts(counter);
            }
        });
    }

    public void setHearts(int counter) {
        switch(counter) {
            case 0:
                r_heart1.setImageResource(R.drawable.light_heart);
                r_heart2.setImageResource(R.drawable.light_heart);
                r_heart3.setImageResource(R.drawable.light_heart);
                r_heart4.setImageResource(R.drawable.light_heart);
                r_heart5.setImageResource(R.drawable.light_heart);
                return;
            case 1:
                r_heart1.setImageResource(R.drawable.dark_heart);
                r_heart2.setImageResource(R.drawable.light_heart);
                r_heart3.setImageResource(R.drawable.light_heart);
                r_heart4.setImageResource(R.drawable.light_heart);
                r_heart5.setImageResource(R.drawable.light_heart);
                return;
            case 2:
                r_heart1.setImageResource(R.drawable.dark_heart);
                r_heart2.setImageResource(R.drawable.dark_heart);
                r_heart3.setImageResource(R.drawable.light_heart);
                r_heart4.setImageResource(R.drawable.light_heart);
                r_heart5.setImageResource(R.drawable.light_heart);
                return;
            case 3:
                r_heart1.setImageResource(R.drawable.dark_heart);
                r_heart2.setImageResource(R.drawable.dark_heart);
                r_heart3.setImageResource(R.drawable.dark_heart);
                r_heart4.setImageResource(R.drawable.light_heart);
                r_heart5.setImageResource(R.drawable.light_heart);
                return;
            case 4:
                r_heart1.setImageResource(R.drawable.dark_heart);
                r_heart2.setImageResource(R.drawable.dark_heart);
                r_heart3.setImageResource(R.drawable.dark_heart);
                r_heart4.setImageResource(R.drawable.dark_heart);
                r_heart5.setImageResource(R.drawable.light_heart);
                return;
            case 5:
                r_heart1.setImageResource(R.drawable.dark_heart);
                r_heart2.setImageResource(R.drawable.dark_heart);
                r_heart3.setImageResource(R.drawable.dark_heart);
                r_heart4.setImageResource(R.drawable.dark_heart);
                r_heart5.setImageResource(R.drawable.dark_heart);
                return;
            default:
        }
    }
}