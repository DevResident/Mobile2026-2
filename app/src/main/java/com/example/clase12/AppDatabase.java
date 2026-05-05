package com.example.clase12;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Cursos.class, Alumnos.class, Asistencias.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    private static String database = "escuela_db";

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, database)
                    .setJournalMode(JournalMode.TRUNCATE)
                    .build();
        }
        return instance;
    }

    public abstract AlumnosDao alumnosDao();
    public abstract CursosDao cursosDao();
    public abstract AsistenciasDao asistenciasDao();
}