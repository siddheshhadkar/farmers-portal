package com.example.farmersportal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private RadioGroup radioGroup;
    private RadioButton radioBuyer;
    private RadioButton radioSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(v -> {
            if (!validateEmail() | !validatePassword() | !validateRadio()) {
                return;
            }
            Toast.makeText(this, "Otherwise", Toast.LENGTH_SHORT).show();
        });

        TextView textViewSignUp = findViewById(R.id.textViewSignUp);
        textViewSignUp.setOnClickListener(v -> startActivity(new Intent(this, SignupActivity.class)));
    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setErrorEnabled(true);
            textInputEmail.setError("Please enter Email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setErrorEnabled(true);
            textInputEmail.setError("Please enter a valid email address");
            return false;
        } else {
            textInputEmail.setErrorEnabled(false);
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String emailPassword = textInputPassword.getEditText().getText().toString().trim();
        if (emailPassword.isEmpty()) {
            textInputPassword.setErrorEnabled(true);
            textInputPassword.setError("Please enter a password");
            return false;
        } else {
            textInputPassword.setErrorEnabled(false);
            textInputPassword.setError(null);
            return true;
        }
    }

    private boolean validateRadio() {
        return true;
    }
}