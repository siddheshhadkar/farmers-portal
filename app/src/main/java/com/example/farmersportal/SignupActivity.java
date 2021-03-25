package com.example.farmersportal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Portal Sign Up");

        TextView textViewLogIn = findViewById(R.id.textViewLogIn);
        textViewLogIn.setOnClickListener(v-> startActivity(new Intent(this, LoginActivity.class)));
    }
}