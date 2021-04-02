package com.example.farmersportal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.farmersportal.database.User;
import com.example.farmersportal.viewmodel.MainFactory;
import com.example.farmersportal.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputName;
    private TextInputLayout textInputPhone;
    private TextInputLayout textInputPassword;
    private Spinner spinnerLocation;
    private RadioGroup radioGroup;

    private UserViewModel userViewModel;

    private String location;
    private String name;
    private String email;
    private String phone;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setSupportActionBar(findViewById(R.id.toolbar));

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputName = findViewById(R.id.textInputName);
        textInputPhone = findViewById(R.id.textInputPhoneNumber);
        textInputPassword = findViewById(R.id.textInputPassword);
        spinnerLocation = findViewById(R.id.spinnerLocation);
        radioGroup = findViewById(R.id.radioGroup);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.location_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapter);
        location = adapter.getItem(0).toString();
        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location = (String) spinnerLocation.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        MainFactory factory = new MainFactory(getApplication());
        userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);

        userViewModel.getRepository().setSignUpCheckListener((emailExists, phoneExists) -> {
            if (emailExists && phoneExists) {
                runOnUiThread(() -> {
                    textInputEmail.setErrorEnabled(true);
                    textInputEmail.setError("Email Id is associated with another account");
                    textInputPhone.setErrorEnabled(true);
                    textInputPhone.setError("Phone number is associated with another account");
                    textInputEmail.requestFocus();
                });
            } else if (emailExists) {
                runOnUiThread(() -> {
                    textInputEmail.setErrorEnabled(true);
                    textInputEmail.setError("Email Id is associated with another account");
                    textInputEmail.requestFocus();
                });
            } else if (phoneExists) {
                runOnUiThread(() -> {
                    textInputPhone.setErrorEnabled(true);
                    textInputPhone.setError("Phone number is associated with another account");
                    textInputPhone.requestFocus();
                });
            } else {
                int userType;

                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                if (radioButton.getText().equals("Buyer"))
                    userType = 0;
                else
                    userType = 1;

                User user = new User(name, email, phone, password, location, userType);
                userViewModel.insert(user);

                startActivity(new Intent(this, DashboardActivity.class).putExtra("EXTRA_EMAIL", email));
            }
        });

        Button buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(v -> {
            if (!validateName() | !validateEmail() | !validatePhone() | !validatePassword()) {
                return;
            }
            userViewModel.valuesExist(email, phone);
        });

        TextView textViewLogIn = findViewById(R.id.textViewLogIn);
        textViewLogIn.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }

    private boolean validateName() {
        name = Objects.requireNonNull(textInputName.getEditText()).getText().toString().trim();
        if (name.isEmpty()) {
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
        email = Objects.requireNonNull(textInputEmail.getEditText()).getText().toString().trim();
        if (email.isEmpty()) {
            textInputEmail.setErrorEnabled(true);
            textInputEmail.setError("Please enter Email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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
        phone = Objects.requireNonNull(textInputPhone.getEditText()).getText().toString().trim();
        if (phone.isEmpty()) {
            textInputPhone.setErrorEnabled(true);
            textInputPhone.setError("Please enter your phone number");
            return false;
        } else if (!phone.matches("\\d{10}")) {
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
        password = Objects.requireNonNull(textInputPassword.getEditText()).getText().toString().trim();
        if (password.isEmpty()) {
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