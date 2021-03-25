package com.example.farmersportal.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE)})
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private final int userId;
    private final String name;
    private final String price;

    public Product(int userId, String name, String price) {
        this.userId = userId;
        this.name = name;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
