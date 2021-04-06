package com.example.farmersportal.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersportal.R;
import com.example.farmersportal.adapter.ProductAdapter;
import com.example.farmersportal.application.BaseApplication;
import com.example.farmersportal.database.User;
import com.example.farmersportal.viewmodel.MainFactory;
import com.example.farmersportal.viewmodel.ProductViewModel;
import com.example.farmersportal.viewmodel.UserViewModel;

public class OrderHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        setSupportActionBar(findViewById(R.id.toolbar));

        MainFactory factory = new MainFactory(getApplication());
        UserViewModel userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);
        ProductViewModel productViewModel = new ViewModelProvider(this, factory).get(ProductViewModel.class);

        ProductAdapter adapter = new ProductAdapter();
        adapter.setOnItemClickListener(product -> {
            Intent i = new Intent(this, ProduceDetailsActivity.class);
            i.putExtra("EXTRA_PRODUCT_ID", product.getId());
            i.putExtra("EXTRA_SELLER_ID", product.getSellerId());
            startActivity(i);
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        userViewModel.getUsers().observe(this, adapter::setUserList);
        User user = BaseApplication.getApplicationInstance().getSessionUser();
        if (user.getUserType() == 0) {
            productViewModel.getBoughtProducts(user.getId()).observe(this, adapter::setProductList);
        } else {
            productViewModel.getSoldProducts(user.getId()).observe(this, adapter::setProductList);
        }
    }
}