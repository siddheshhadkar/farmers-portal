package com.example.farmersportal;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;

import com.example.farmersportal.fragment.BuyerCardsFragment;
import com.example.farmersportal.fragment.SellerCardsFragment;
import com.example.farmersportal.viewmodel.MainFactory;
import com.example.farmersportal.viewmodel.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class DashboardActivity extends AppCompatActivity {

    private FragmentContainerView fragmentContainerView;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        MainFactory factory = new MainFactory(getApplication());
        userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);

        fragmentContainerView = findViewById(R.id.fragmentContainerView);

        int userType = 1;
        if (userType == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new BuyerCardsFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new SellerCardsFragment()).commit();
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
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.log_out_menu) {
            Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}