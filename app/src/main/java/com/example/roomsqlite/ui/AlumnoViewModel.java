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
        alumnosRepositorio = new AlumnosRepositorio(application);
    }

    public LiveData<List<Alumno>> obtener() {
        return alumnosRepositorio.obtener();
    }

    public void insertar(Alumno a) {
        alumnosRepositorio.insertar(a);
    }

    public void eliminar(Alumno a) {
        alumnosRepositorio.eliminar(a);
    }

    public void actualizar(Alumno elemento, String nombre, float nota) {
        alumnosRepositorio.actualizar(elemento, nombre, nota); // [cite: 863]
    }
}
