package com.example.clase12;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CursosDao {
    @Query("SELECT * FROM cursos")
    List<Cursos> getAll();

    @Insert
    void insert(Cursos curso);
}