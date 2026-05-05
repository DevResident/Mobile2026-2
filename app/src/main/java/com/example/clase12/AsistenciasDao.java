package com.example.clase12;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AsistenciasDao {
    @Query("SELECT * FROM asistencias WHERE alumnoId = :idAlumno ORDER BY id DESC")
    List<Asistencias> getAsistenciasByAlumno(int idAlumno);

    @Query("SELECT * FROM asistencias WHERE alumnoId = :idAlumno AND fecha = :fecha")
    List<Asistencias> getAsistenciasByAlumnoAndFecha(int idAlumno, String fecha);

    @Insert
    void insert(Asistencias asistencia);
}
