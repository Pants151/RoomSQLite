package com.example.roomsqlite.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alumno")
public class Alumno {

    // [cite: 692, 699]
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;

    // [cite: 701]
    @ColumnInfo(name = "nombre")
    String nombre;

    float nota;

    public Alumno(String nombre, float nota) {
        this.nombre = nombre;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}
