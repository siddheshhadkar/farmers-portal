package com.example.farmersportal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.farmersportal.R;
import com.example.farmersportal.application.BaseApplication;
import com.example.farmersportal.database.User;
import com.example.farmersportal.viewmodel.MainFactory;
import com.example.farmersportal.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private TextInputLayout textInputName;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPhone;
    private TextInputLayout textInputPassword;
    private Spinner spinnerLocation;

    private User user;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setSupportActionBar(findViewById(R.id.toolbar));

        textInputName = findViewById(R.id.textInputName);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPhone = findViewById(R.id.textInputPhoneNumber);
        textInputPassword = findViewById(R.id.textInputPassword);
        spinnerLocation = findViewById(R.id.spinnerLocation);
        Button buttonEditProfile = findViewById(R.id.buttonEditProfile);
        Button buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile);
        fillFields();
        enableViews(false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.location_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapter);
        spinnerLocation.setSelection(adapter.getPosition(location));

        buttonEditProfile.setOnClickListener(v -> {
            buttonEditProfile.setVisibility(View.GONE);
            buttonUpdateProfile.setVisibility(View.VISIBLE);
            enableViews(true);
        });

        buttonUpdateProfile.setOnClickListener(v -> {
            buttonUpdateProfile.setVisibility(View.GONE);
            buttonEditProfile.setVisibility(View.VISIBLE);
            updateDatabase();
            enableViews(false);
        });
    }

    private void enableViews(boolean enable) {
        textInputName.setEnabled(enable);
        textInputEmail.setEnabled(enable);
        textInputPhone.setEnabled(enable);
        textInputPassword.setEnabled(enable);
        spinnerLocation.setEnabled(enable);
    }

    private void fillFields() {
        user = BaseApplication.getApplicationInstance().getSessionUser();
        Objects.requireNonNull(textInputName.getEditText()).setText(user.getName());
        Objects.requireNonNull(textInputEmail.getEditText()).setText(user.getEmail());
        Objects.requireNonNull(textInputPhone.getEditText()).setText(user.getPhoneNumber());
        Objects.requireNonNull(textInputPassword.getEditText()).setText(user.getPassword());
        location = user.getLocation();
        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location = (String) spinnerLocation.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateDatabase() {
        MainFactory factory = new MainFactory(getApplication());
        UserViewModel userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);
        String name = Objects.requireNonNull(textInputName.getEditText()).getText().toString();
        String email = Objects.requireNonNull(textInputEmail.getEditText()).getText().toString();
        String phone = Objects.requireNonNull(textInputPhone.getEditText()).getText().toString();
        String password = Objects.requireNonNull(textInputPassword.getEditText()).getText().toString();

        User updatedUser = new User(name, email, phone, password, location, user.getUserType());
        updatedUser.setId(user.getId());
        userViewModel.update(updatedUser);
    }
}