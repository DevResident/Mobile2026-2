package com.example.weather;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Weather.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    private static String database = "weatherdb";

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, database)
                .setJournalMode(JournalMode.TRUNCATE)
                .build();
        }
        return instance;
    }

    public abstract WeatherDao weatherDao();
}