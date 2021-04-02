package com.example.farmersportal.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Product.class}, version = 1, exportSchema = false)
public abstract class MainDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService dbWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static MainDatabase INSTANCE;

    public static synchronized MainDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MainDatabase.class, "main_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            dbWriteExecutor.execute(() -> {
                UserDao userDao = INSTANCE.userDao();
                userDao.insert(new User("A", "A@g.com", "1111111111", "pass", "Mumbai", 1));
                userDao.insert(new User("B", "B@g.com", "2222222222", "pass", "Kolhapur", 0));
                userDao.insert(new User("C", "C@g.com", "3333333333", "pass", "Raipur", 1));
                userDao.insert(new User("D", "D@g.com", "4444444444", "pass", "Kolkata", 1));
                userDao.insert(new User("E", "E@g.com", "5555555555", "pass", "Chennai", 0));

                ProductDao productDao = INSTANCE.productDao();
                productDao.insert(new Product("Rice", "2067", 1, 0));
                productDao.insert(new Product("Tomato", "1454", 3, 0));
                productDao.insert(new Product("Corn", "3647", 1, 0));
                productDao.insert(new Product("Sugarcane", "10075", 1, 0));
                productDao.insert(new Product("Cotton", "50000", 3, 0));
                productDao.insert(new Product("Jute", "5400", 3, 0));
                productDao.insert(new Product("Coffee", "68494", 3, 0));
                productDao.insert(new Product("Sugarcane", "10075", 1, 0));
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            dbWriteExecutor.execute(() -> {
//                UserDao userDao = INSTANCE.userDao();
//                userDao.insert(new User("A", "asd", "123", "pass", "loc"));
//                userDao.insert(new User("B", "asd", "123", "pass", "loc"));
            });
        }
    };

    public abstract UserDao userDao();

    public abstract ProductDao productDao();
}
