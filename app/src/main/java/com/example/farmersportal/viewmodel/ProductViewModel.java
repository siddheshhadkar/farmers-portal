package com.example.farmersportal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.farmersportal.database.Product;
import com.example.farmersportal.database.ProductRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductViewModel extends AndroidViewModel {
    private final ProductRepository repository;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
    }

    public void insert(Product product) {
        repository.insert(product);
    }

    public void update(Product product) {
        repository.update(product);
    }

    public void delete(Product product) {
        repository.delete(product);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public Product getProduct(int id) throws ExecutionException, InterruptedException {
        return repository.getProduct(id);
    }

    public LiveData<List<Product>> getUnsoldProducts() {
        return repository.getUnsoldProducts();
    }
}
