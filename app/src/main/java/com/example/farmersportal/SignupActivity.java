package com.example.farmersportal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.farmersportal.viewmodel.MainFactory;
import com.example.farmersportal.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputName;
    private TextInputLayout textInputPhone;
    private TextInputLayout textInputPassword;
    private RadioGroup radioGroup;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Portal Sign Up");

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputName = findViewById(R.id.textInputName);
        textInputPhone = findViewById(R.id.textInputPhoneNumber);
        textInputPassword = findViewById(R.id.textInputPassword);
        radioGroup = findViewById(R.id.radioGroup);

        MainFactory factory = new MainFactory(getApplication());
        userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);
        userViewModel.getRepository().setCheckListener((emailExists, phoneExists) -> {
            if (emailExists && phoneExists) {
                runOnUiThread(() -> {
                    Toast.makeText(SignupActivity.this, "Email Id and Phone Number are already associated with another account", Toast.LENGTH_SHORT).show();
                    textInputEmail.setErrorEnabled(true);
                    textInputEmail.setError("Email Id is associated with another account");
                    textInputPhone.setErrorEnabled(true);
                    textInputPhone.setError("Phone number is associated with another account");
                    textInputEmail.requestFocus();
                });
            } else if (emailExists) {
                runOnUiThread(() -> {
                    Toast.makeText(SignupActivity.this, "This Email Id is already associated with another account", Toast.LENGTH_SHORT).show();
                    textInputEmail.setErrorEnabled(true);
                    textInputEmail.setError("Email Id is associated with another account");
                    textInputEmail.requestFocus();
                });
            } else if (phoneExists) {
                runOnUiThread(() -> {
                    Toast.makeText(SignupActivity.this, "This Phone Number is already associated with another account", Toast.LENGTH_SHORT).show();
                    textInputPhone.setErrorEnabled(true);
                    textInputPhone.setError("Phone number is associated with another account");
                    textInputPhone.requestFocus();
                });
            } else {
                runOnUiThread(() -> Toast.makeText(SignupActivity.this, "Accepted", Toast.LENGTH_SHORT).show());
            }
        });

        Button buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(v -> {
            if (!validateName() | !validateEmail() | !validatePhone() | !validatePassword()) {
                return;
            }
            userViewModel.valuesExist(textInputEmail.getEditText().getText().toString().trim(), textInputPhone.getEditText().getText().toString().trim());
        });

        TextView textViewLogIn = findViewById(R.id.textViewLogIn);
        textViewLogIn.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }

    private boolean validateName() {
        String nameInput = textInputName.getEditText().getText().toString().trim();
        if (nameInput.isEmpty()) {
            textInputName.setErrorEnabled(true);
            textInputName.setError("Please enter your name");
            return false;
        } else {
            textInputName.setErrorEnabled(false);
            textInputName.setError(null);
            return true;
        }
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

    private boolean validatePhone() {
        String phoneInput = textInputPhone.getEditText().getText().toString().trim();
        if (phoneInput.isEmpty()) {
            textInputPhone.setErrorEnabled(true);
            textInputPhone.setError("Please enter your phone number");
            return false;
        } else if (!phoneInput.matches("\\d{10}")) {
            textInputPhone.setErrorEnabled(true);
            textInputPhone.setError("Please enter a valid phone number");
            return false;
        } else {
            textInputPhone.setErrorEnabled(false);
            textInputPhone.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textInputPassword.setErrorEnabled(true);
            textInputPassword.setError("Please enter a password");
            return false;
        } else {
            textInputPassword.setErrorEnabled(false);
            textInputPassword.setError(null);
            return true;
        }
    }
}