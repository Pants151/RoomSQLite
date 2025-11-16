package com.example.roomsqlite.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roomsqlite.data.Alumno;
import com.example.roomsqlite.data.AlumnosRepositorio;

import java.util.List;

public class AlumnoViewModel extends AndroidViewModel {

    AlumnosRepositorio alumnosRepositorio;

    public AlumnoViewModel(@NonNull Application application) {
        super(application);
        alumnosRepositorio = new AlumnosRepositorio(application); // [cite: 848]
    }

    public LiveData<List<Alumno>> obtener() {
        return alumnosRepositorio.obtener(); // [cite: 852]
    }

    public void insertar(Alumno a) {
        alumnosRepositorio.insertar(a); // [cite: 857]
    }

    public void eliminar(Alumno a) {
        alumnosRepositorio.eliminar(a); // [cite: 860]
    }

    public void actualizar(Alumno elemento, String nombre, float nota) {
        alumnosRepositorio.actualizar(elemento, nombre, nota); // [cite: 863]
    }
}
