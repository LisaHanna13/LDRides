package com.example.finalproject;

import java.io.Serializable;

public class Driver implements Serializable {
    int driverId;
    String firstName;
    String lastName;
    String email;
    String password;
    String languages;
    String dateJoined;
    String location;
    int rating;
    int numberOfRides;
    String otherNotes;
    String licensePlate;

    public Driver(){}

    public Driver(int driverId, String firstName, String lastName, String email, String password, String languages,
                  String dateJoined, String location, int rating, int numberOfRides, String otherNotes, String licensePlate) {
        this.driverId = driverId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.languages = languages;
        this.dateJoined = dateJoined;
        this.location = location;
        this.rating = rating;
        this.numberOfRides = numberOfRides;
        this.otherNotes = otherNotes;
        this.licensePlate = licensePlate;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getNumberOfRides() {
        return numberOfRides;
    }

    public void setNumberOfRides(int numberOfRides) {
        this.numberOfRides = numberOfRides;
    }

    public String getOtherNotes() {
        return otherNotes;
    }

    public void setOtherNotes(String otherNotes) {
        this.otherNotes = otherNotes;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}