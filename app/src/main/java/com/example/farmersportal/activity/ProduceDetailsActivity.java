package com.example.farmersportal.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.farmersportal.R;
import com.example.farmersportal.database.Product;
import com.example.farmersportal.database.User;
import com.example.farmersportal.viewmodel.MainFactory;
import com.example.farmersportal.viewmodel.ProductViewModel;
import com.example.farmersportal.viewmodel.UserViewModel;

public class ProduceDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_details);
        setSupportActionBar(findViewById(R.id.toolbar));

        Intent i = getIntent();
        int productId = i.getIntExtra("EXTRA_PRODUCT_ID", 0);
        int sellerId = i.getIntExtra("EXTRA_SELLER_ID", 0);

        MainFactory factory = new MainFactory(getApplication());
        UserViewModel userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);
        ProductViewModel productViewModel = new ViewModelProvider(this, factory).get(ProductViewModel.class);

        TextView textViewProduct = findViewById(R.id.textViewProduct);
        TextView textViewSeller = findViewById(R.id.textViewSeller);
        TextView textViewLocation = findViewById(R.id.textViewLocation);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewPhone = findViewById(R.id.textViewPhone);
        TextView textViewWeight = findViewById(R.id.textViewWeight);
        TextView textViewPrice = findViewById(R.id.textViewPrice);

        if (productId == 0 || sellerId == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("There was an error displaying produce details. Please try again")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .show();
        } else {
            try {
                User seller = userViewModel.getUser(sellerId);
                Product product = productViewModel.getProduct(productId);
                textViewProduct.setText(product.getName());
                textViewSeller.setText(seller.getName());
                textViewLocation.setText(seller.getLocation());
                textViewEmail.setText(seller.getEmail());
                textViewPhone.setText(seller.getPhoneNumber());
                textViewWeight.setText("WEIGHT");
                textViewPrice.setText(product.getPrice());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}