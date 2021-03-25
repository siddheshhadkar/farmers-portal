package com.example.farmersportal.database;

import android.app.Application;

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
}
