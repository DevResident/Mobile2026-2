package com.example.clase12;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Asistencias {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "fecha")
    public String fecha;

    @ColumnInfo(name = "status")
    public String status;

    @ColumnInfo(name = "alumnoId")
    public int alumnoId;

    public Asistencias(int id, String fecha, String status, int alumnoId) {
        this.id = id;
        this.fecha = fecha;
        this.status = status;
        this.alumnoId = alumnoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }
}
