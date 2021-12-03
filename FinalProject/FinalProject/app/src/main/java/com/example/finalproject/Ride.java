package com.example.finalproject;

public class Ride {
    String date;
    int duration;
    double cost;
    String pickup;
    String destination;
    String userId;
    String driverId;

    public Ride(String date, int duration, double cost, String pickup, String destination,
                String userId, String driverId) {
        this.date = date;
        this.duration = duration;
        this.cost = cost;
        this.pickup = pickup;
        this.destination = destination;
        this.userId = userId;
        this.driverId = driverId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}