package com.example.farmersportal.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM User")
    void deleteAll();

    @Query("SELECT EXISTS (SELECT * FROM User WHERE email=:enteredEmail)")
    boolean emailExists(String enteredEmail);

    @Query("SELECT EXISTS (SELECT * FROM User WHERE phoneNumber=:enteredPhone)")
    boolean phoneExists(String enteredPhone);

    @Query("SELECT EXISTS (SELECT * FROM User WHERE email=:enteredEmail AND password=:enteredPassword)")
    boolean accountExists(String enteredEmail, String enteredPassword);

    @Query("SELECT * FROM User WHERE email=:email")
    User getUser(String email);

    @Query("SELECT * FROM User")
    LiveData<List<User>> getUsers();
}
