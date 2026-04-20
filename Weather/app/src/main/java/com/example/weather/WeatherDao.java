package com.example.weather;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM weather")
    List<Weather> getAll();

    @Query("SELECT * FROM weather WHERE wid IN (:weatherIds)")
    List<Weather> loadAllByIds(int[] weatherIds);

    @Query("SELECT * FROM weather WHERE city = :city ORDER BY date DESC")
    List<Weather> getByCity(String city);

    @Query("SELECT COUNT(*) FROM weather WHERE city = :city AND date = :date")
    int existsRecord(String city, String date);

    @Insert
    void insertAll(Weather... weatherList);

    @Delete
    void delete(Weather weather);
}
