package com.example.farmersportal.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("DELETE FROM Product")
    void deleteAll();

    @Query("SELECT * FROM Product WHERE id=:id")
    Product getProduct(int id);

    @Query("SELECT * FROM Product WHERE buyerId=0")
    LiveData<List<Product>> getUnsoldProducts();

    @Query("SELECT * FROM Product WHERE sellerId=:sellerId AND NOT buyerId=0")
    LiveData<List<Product>> getSoldProducts(int sellerId);

    @Query("SELECT * FROM Product WHERE buyerId=:buyerId")
    LiveData<List<Product>> getBoughtProducts(int buyerId);
}
