package com.example.farmersportal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersportal.R;
import com.example.farmersportal.adapter.ProductAdapter;
import com.example.farmersportal.viewmodel.MainFactory;
import com.example.farmersportal.viewmodel.ProductViewModel;
import com.example.farmersportal.viewmodel.UserViewModel;

public class ViewProduceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_produce);

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
        productViewModel.getUnsoldProducts().observe(this, adapter::setProductList);
    }
}