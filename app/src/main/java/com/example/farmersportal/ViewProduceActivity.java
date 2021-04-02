package com.example.farmersportal;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersportal.adapter.ProductAdapter;
import com.example.farmersportal.database.Product;
import com.example.farmersportal.viewmodel.MainFactory;
import com.example.farmersportal.viewmodel.ProductViewModel;
import com.example.farmersportal.viewmodel.UserViewModel;

import java.util.List;

public class ViewProduceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private UserViewModel userViewModel;
    private ProductViewModel productViewModel;

    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_produce);

        MainFactory factory = new MainFactory(getApplication());
        userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);
        productViewModel = new ViewModelProvider(this, factory).get(ProductViewModel.class);

        adapter = new ProductAdapter();

        productViewModel.getProducts().observe(this, productList -> {
            ViewProduceActivity.this.productList = productList;
            adapter.setProductList(productList);
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(product -> Toast.makeText(ViewProduceActivity.this, product.getName(), Toast.LENGTH_SHORT).show());
    }
}