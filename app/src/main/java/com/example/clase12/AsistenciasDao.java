package com.example.clase12;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AsistenciasDao {
    @Query("SELECT * FROM asistencias WHERE alumnoId = :idAlumno")
    List<Asistencias> getAsistenciasByAlumno(int idAlumno);

    @Insert
    void insert(Asistencias asistencia);
}
