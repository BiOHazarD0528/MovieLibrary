package com.example.movielibrary.login_provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {LoginData.class}, version = 1)
public abstract class LoginDatabase extends RoomDatabase {
    public static final String LOGIN_DATABASE_NAME = "login_database";
    public abstract LoginDao loginDao();
    private static volatile LoginDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static LoginDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LoginDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LoginDatabase.class, LOGIN_DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}
