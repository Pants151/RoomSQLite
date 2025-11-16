package com.example.roomsqlite.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.roomsqlite.data.Alumno;
import com.example.roomsqlite.databinding.FragmentNuevoAlumnoBinding;

public class NuevoAlumnoFragment extends Fragment {

    private FragmentNuevoAlumnoBinding binding;
    private AlumnoViewModel alumnoViewModel;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNuevoAlumnoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        alumnoViewModel = new ViewModelProvider(requireActivity()).get(AlumnoViewModel.class);

        // Lógica del botón Guardar
        binding.botonGuardar.setOnClickListener(v -> {
            String nombre = binding.textoNombre.getText().toString();
            String notaStr = binding.textoNota.getText().toString();

            if (nombre.isEmpty() || notaStr.isEmpty()) {
                Toast.makeText(getContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                float nota = Float.parseFloat(notaStr);
                // Insertar el alumno en la BBDD a través del ViewModel
                //
                alumnoViewModel.insertar(new Alumno(nombre, nota));
                navController.popBackStack(); // Volver a la lista
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "El formato de la nota no es válido", Toast.LENGTH_SHORT).show();
            }
        });

        // Lógica del botón Cancelar
        binding.botonCancelar.setOnClickListener(v -> {
            navController.popBackStack(); // Volver a la lista
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}