package com.example.farmersportal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.farmersportal.R;
import com.example.farmersportal.application.BaseApplication;
import com.example.farmersportal.database.User;
import com.example.farmersportal.fragment.BuyerCardsFragment;
import com.example.farmersportal.fragment.SellerCardsFragment;
import com.example.farmersportal.viewmodel.MainFactory;
import com.example.farmersportal.viewmodel.UserViewModel;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setSupportActionBar(findViewById(R.id.toolbar));

        MainFactory factory = new MainFactory(getApplication());
        UserViewModel userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);

        String email = getIntent().getStringExtra("EXTRA_EMAIL");
        User user;
        try {
            user = userViewModel.getUser(email);
            BaseApplication.getApplicationInstance().setSessionUser(user);
            if (user.getUserType() == 0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new BuyerCardsFragment()).commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new SellerCardsFragment()).commit();
            }
        } catch (Exception e) {
            Toast.makeText(this, "There was an error logging in.\nPlease try again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile_menu) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        } else if (item.getItemId() == R.id.log_out_menu) {
            BaseApplication.getApplicationInstance().setSessionUser(null);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}