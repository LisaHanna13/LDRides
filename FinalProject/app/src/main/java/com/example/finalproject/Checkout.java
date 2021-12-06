package com.example.finalproject;

import static java.sql.Types.NULL;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Checkout extends AppCompatActivity {
    TextView ck_startPoint, ck_destination, ck_duration, ck_subtotal, ck_tps, ck_tvq, ck_total;
    EditText cardNum, expDate, securityCode;
    Button ck_confirmB, ck_cancelB;

    User user;
    Driver driver;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        databaseHelper = MainActivity.databaseHelper;

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

        expDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() == 2) {
                    if(start == 2 && before == 1 && !charSequence.toString().contains("/")){
                        expDate.setText("" + charSequence.toString().charAt(0));
                        expDate.setSelection(1);
                    }
                    else {
                        expDate.setText(charSequence + "/");
                        expDate.setSelection(3);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ck_confirmB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (cardNum.getText().equals("") || expDate.getText().equals("") || securityCode.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "Do not leave card information fields empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cardNum.length() != 16 || !validateIsNumber(cardNum.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Invalid Card Number",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (expDate.length() != 5 && expDate.length() != 4) {
                    Toast.makeText(getApplicationContext(), "Invalid Expiry Date",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String today = dtf.format(now);

                int currYear = Integer.valueOf(today.substring(2,4));
                int currMonth = Integer.valueOf(today.substring(5,7));

                int inputMonth;
                int inputYear;

                if(expDate.length() == 5) {
                    inputMonth = Integer.valueOf(expDate.getText().toString().substring(0,2));
                    inputYear = Integer.valueOf(expDate.getText().toString().substring(3));
                } else {
                    inputMonth = Integer.valueOf(expDate.getText().toString().substring(0,1));
                    inputYear = Integer.valueOf(expDate.getText().toString().substring(2));
                }

                // make sure year hasn't passed
                if(inputYear < currYear) {
                    Toast.makeText(getApplicationContext(), "Your card cannot be expired!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // if it's current year, make sure month hasnt passed
                else if (inputYear == currYear) {
                    if(inputMonth < currMonth || inputMonth > 12 || inputMonth <= 0) {
                        Toast.makeText(getApplicationContext(), "Your card cannot be expired!",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // otherwise, make sure month is valid
                else {
                    if(inputMonth > 12 || inputMonth <= 0) {
                        Toast.makeText(getApplicationContext(), "Please input a valid month (1-12).",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (securityCode.length() != 3 || !validateIsNumber(securityCode.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Invalid Security Code",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getApplicationContext(), "Your ride request has been sent successfully!", Toast.LENGTH_LONG).show();
                Intent homeIntent = new Intent(Checkout.this, Home.class);

                homeIntent.putExtra("user", user);

                startActivityForResult(homeIntent, 1);

//                Intent checkoutIntent = new Intent(Checkout.this, Confirmation.class);
//
//                checkoutIntent.putExtra("user", user);
////                checkoutIntent.putExtra("driver", driver);
//                checkoutIntent.putExtra("pickup", pickup);
//                checkoutIntent.putExtra("destination", destination);
//                checkoutIntent.putExtra("estimateTime", estimateTime);
//                checkoutIntent.putExtra("cost", cost);

//
                Date date = Calendar.getInstance().getTime();
                boolean isRideCreated = databaseHelper.insertRide(String.valueOf(date), estimateTime, total, pickup, destination, user.getUserId(),NULL);
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

    public boolean validateIsNumber(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (Character.isAlphabetic(number.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}