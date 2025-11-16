package com.example.roomsqlite.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AlumnosRepositorio {

    AlumnoDao alumnoDao;

    Executor executor = Executors.newSingleThreadExecutor();

    public AlumnosRepositorio(Application application) {
        alumnoDao = AlumnoDataBase.obtenerInstancia(application).getAlumnoDao();
    }

    public LiveData<List<Alumno>> obtener() {
        return alumnoDao.getAlumnos();
    }

    public void insertar(Alumno alumno) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                alumnoDao.addAlumno(alumno);
            }
        });
    }

    public void eliminar(Alumno alumno) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                alumnoDao.deleteAlumno(alumno);
            }
        });
    }

    public void actualizar(Alumno a, String nombre, float nota) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                a.setNombre(nombre);
                a.setNota(nota);
                alumnoDao.updateAlumno(a);
            }
        });
    }
}