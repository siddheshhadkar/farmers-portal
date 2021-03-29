package com.example.farmersportal.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final String location;
    private final int userType;

    public User(String name, String email, String phoneNumber, String password, String location, int userType) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.location = location;
        this.userType = userType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public int getUserType() {
        return userType;
    }
}
