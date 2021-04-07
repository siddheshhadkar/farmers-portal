package com.example.farmersportal.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.farmersportal.R;
import com.example.farmersportal.application.BaseApplication;
import com.example.farmersportal.database.Product;
import com.example.farmersportal.viewmodel.MainFactory;
import com.example.farmersportal.viewmodel.ProductViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class UploadProduceActivity extends AppCompatActivity {

    private TextInputLayout textInputProduceName;
    private TextInputLayout textInputQuantity;
    private TextInputLayout textInputPrice;

    private String name;
    private int quantity;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_produce);

        textInputProduceName = findViewById(R.id.textInputProduceName);
        textInputQuantity = findViewById(R.id.textInputQuantity);
        textInputPrice = findViewById(R.id.textInputPrice);
        Button buttonUpload = findViewById(R.id.buttonUploadProduce);

        buttonUpload.setOnClickListener(v -> {
            if (!validateQuantity() | !validatePrice() | !validateProduceName()) {
                return;
            }
            MainFactory factory = new MainFactory(getApplication());
            ProductViewModel productViewModel = new ViewModelProvider(this, factory).get(ProductViewModel.class);
            Product product = new Product(name, price, quantity, BaseApplication.getApplicationInstance().getSessionUser().getId(), 0);
            productViewModel.insert(product);
            Toast.makeText(this, "Produce details uploaded successfully", Toast.LENGTH_SHORT).show();
            finish();
        });

    }

    private boolean validateProduceName() {
        name = Objects.requireNonNull(textInputProduceName.getEditText()).getText().toString().trim();
        if (name.isEmpty()) {
            textInputProduceName.setErrorEnabled(true);
            textInputProduceName.setError("Please enter produce name");
            return false;
        } else {
            textInputProduceName.setErrorEnabled(false);
            textInputProduceName.setError(null);
            return true;
        }
    }

    private boolean validateQuantity() {
        String weight = Objects.requireNonNull(textInputQuantity.getEditText()).getText().toString().trim();
        if (weight.isEmpty()) {
            textInputQuantity.setErrorEnabled(true);
            textInputQuantity.setError("Please enter quantity of produce");
            return false;
        } else {
            quantity = Integer.parseInt(weight);
            textInputQuantity.setErrorEnabled(false);
            textInputQuantity.setError(null);
            return true;
        }
    }

    private boolean validatePrice() {
        price = Objects.requireNonNull(textInputPrice.getEditText()).getText().toString().trim();
        if (price.isEmpty()) {
            textInputPrice.setErrorEnabled(true);
            textInputPrice.setError("Please enter price for produce");
            return false;
        } else {
            textInputPrice.setErrorEnabled(false);
            textInputPrice.setError(null);
            return true;
        }
    }
}