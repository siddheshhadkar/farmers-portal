package com.example.farmersportal.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "sellerId", onDelete = ForeignKey.CASCADE)})
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String name;
    private final String price;
    private final int quantity;
    private final int sellerId;
    private final int buyerId;

    public Product(String name, String price, int quantity, int sellerId, int buyerId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
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

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSellerId() {
        return sellerId;
    }

    public int getBuyerId() {
        return buyerId;
    }
}
