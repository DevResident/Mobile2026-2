package com.example.clase12;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AlumnosDao {
    @Query("SELECT * FROM alumnos WHERE cursoId = :idCurso")
    List<Alumnos> getAlumnosByCurso(int idCurso);

    @Insert
    void insertAll(Alumnos... alumnos);
}
