package com.example.farmersportal.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProductRepository {
    private final ProductDao productDao;

    public ProductRepository(Application application) {
        MainDatabase mainDatabase = MainDatabase.getInstance(application);
        productDao = mainDatabase.productDao();
    }

    public void insert(Product product) {
        MainDatabase.dbWriteExecutor.execute(() -> productDao.insert(product));
    }

    public void update(Product product) {
        MainDatabase.dbWriteExecutor.execute(() -> productDao.update(product));
    }

    public void delete(Product product) {
        MainDatabase.dbWriteExecutor.execute(() -> productDao.delete(product));
    }

    public void deleteAll() {
        MainDatabase.dbWriteExecutor.execute(productDao::deleteAll);
    }

    public Product getProduct(int id) throws ExecutionException, InterruptedException {
        Callable<Product> callable = () -> productDao.getProduct(id);
        Future<Product> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<Product>> getUnsoldProducts() {
        return productDao.getUnsoldProducts();
    }

    public LiveData<List<Product>> getSoldProducts(int sellerId) {
        return productDao.getSoldProducts(sellerId);
    }

    public LiveData<List<Product>> getBoughtProducts(int buyerId) {
        return productDao.getBoughtProducts(buyerId);
    }
}
