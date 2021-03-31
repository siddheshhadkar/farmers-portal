package com.example.farmersportal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.farmersportal.database.UserRepository;
import com.example.farmersportal.viewmodel.MainFactory;
import com.example.farmersportal.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Portal Log In");

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);

        MainFactory factory = new MainFactory(getApplication());
        userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);
        userViewModel.getRepository().setLogInCheckListener(new UserRepository.LogInCheckCallback() {
            @Override
            public void emailCheck(boolean emailExists) {
                if (emailExists) {
                    userViewModel.checkPassword(textInputEmail.getEditText().getText().toString().trim(), textInputPassword.getEditText().getText().toString().trim());
                } else {
                    runOnUiThread(() -> {
                        textInputEmail.requestFocus();
                        textInputEmail.setErrorEnabled(true);
                        textInputEmail.setError("There is no account associated with this Email");
                    });
                }
            }

            @Override
            public void accountValidate(boolean validated) {
                if (validated) {
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                } else {
                    runOnUiThread(() -> {
                        textInputPassword.requestFocus();
                        textInputPassword.setErrorEnabled(true);
                        textInputPassword.setError("Entered password is incorrect. Please enter correct password");
                    });
                }
            }
        });

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(v -> {
            if (!validateEmail() | !validatePassword()) {
                return;
            }
            userViewModel.emailExists(textInputEmail.getEditText().getText().toString().trim());
        });

        TextView textViewSignUp = findViewById(R.id.textViewSignUp);
        textViewSignUp.setOnClickListener(v -> startActivity(new Intent(this, SignUpActivity.class)));
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
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textInputPassword.setErrorEnabled(true);
            textInputPassword.setError("Please enter your password");
            return false;
        } else {
            textInputPassword.setErrorEnabled(false);
            textInputPassword.setError(null);
            return true;
        }
    }
}