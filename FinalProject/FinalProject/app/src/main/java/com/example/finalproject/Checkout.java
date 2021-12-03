package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Checkout extends AppCompatActivity {
    TextView ck_startPoint, ck_destination, ck_duration, ck_subtotal, ck_tps, ck_tvq, ck_total;
    EditText cardNum, expDate, securityCode;
    Button ck_confirmB, ck_cancelB;

    User user;
    Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        ck_startPoint = findViewById(R.id.ck_startPoint);
        ck_destination = findViewById(R.id.ck_destination);
        ck_duration = findViewById(R.id.ck_duration);
        ck_subtotal = findViewById(R.id.ck_subTotal);
        ck_tps = findViewById(R.id.ck_tps);
        ck_tvq = findViewById(R.id.ck_tvq);
        ck_total = findViewById(R.id.ck_total);
        cardNum = findViewById(R.id.cardNum);
        expDate = findViewById(R.id.expDate);
        securityCode = findViewById(R.id.securityCode);
        ck_confirmB = findViewById(R.id.ck_confirmB);
        ck_cancelB = findViewById(R.id.ck_cancelB);

        Intent checkoutIntent = getIntent();
        user = (User) checkoutIntent.getSerializableExtra("user");
        String pickup = checkoutIntent.getStringExtra("pickup");
        String destination = checkoutIntent.getStringExtra("destination");
        int estimateTime = checkoutIntent.getIntExtra("estimateTime", 0);
        double cost = checkoutIntent.getDoubleExtra("cost", 0);

        ck_startPoint.setText("Pickup Spot: " + pickup);
        ck_destination.setText("Destination: " + destination);
        ck_duration.setText("Duration Estimate: " + estimateTime + " minutes");

        double tps = cost * 5 / 100;
        double tvq = cost * 10 / 100;
        double total = tps + tvq + cost;

        String formatCost = String.format("%.2f", cost) + "$";
        String formatTps = String.format("%.2f", tps) + "$";
        String formatTvq = String.format("%.2f", tvq) + "$";
        String formatTotal = String.format("%.2f", total) + "$";

        ck_subtotal.setText(formatCost);
        ck_tps.setText(formatTps);
        ck_tvq.setText(formatTvq);
        ck_total.setText(formatTotal);

        ck_confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardNum.getText().equals("") || expDate.getText().equals("") || securityCode.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "Do not leave card information fields empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cardNum.length() != 16) {
                    Toast.makeText(getApplicationContext(), "Invalid Card Number",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (expDate.length() != 5 && expDate.length() != 4) {
                    Toast.makeText(getApplicationContext(), "Invalid Expiry Date",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (securityCode.length() != 3) {
                    Toast.makeText(getApplicationContext(), "Invalid Security Code",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent checkoutIntent = new Intent(Checkout.this, Confirmation.class);

                checkoutIntent.putExtra("user", user);
//                checkoutIntent.putExtra("driver", driver);
                checkoutIntent.putExtra("pickup", pickup);
                checkoutIntent.putExtra("destination", destination);
                checkoutIntent.putExtra("estimateTime", estimateTime);
                checkoutIntent.putExtra("cost", cost);

                startActivityForResult(checkoutIntent, 1);
            }
        });

        ck_cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(Checkout.this, Home.class);
                homeIntent.putExtra("user", user);
                startActivityForResult(homeIntent, 1);
            }
        });
    }
}