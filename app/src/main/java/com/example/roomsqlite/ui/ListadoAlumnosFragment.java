package com.example.roomsqlite.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomsqlite.R;
import com.example.roomsqlite.data.Alumno;
import com.example.roomsqlite.databinding.FragmentListadoAlumnosBinding;
import com.example.roomsqlite.databinding.ViewholderAlumnoBinding;

import java.util.List;

public class ListadoAlumnosFragment extends Fragment {

    // Habilitar ViewBinding
    private FragmentListadoAlumnosBinding binding;
    private AlumnoViewModel alumnoViewModel;
    private NavController navController;
    private AlumnosAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout usando ViewBinding
        binding = FragmentListadoAlumnosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar NavController y ViewModel (compartido con la Activity)
        navController = Navigation.findNavController(view);

        alumnoViewModel = new ViewModelProvider(requireActivity()).get(AlumnoViewModel.class);

        // Configurar RecyclerView
        adapter = new AlumnosAdapter();
        binding.recyclerView.setAdapter(adapter);

        // Observar la lista de alumnos desde el ViewModel

        alumnoViewModel.obtener().observe(getViewLifecycleOwner(), new Observer<List<Alumno>>() {
            @Override
            public void onChanged(List<Alumno> alumnos) {
                // Cuando la lista cambie, actualizar el adapter
                adapter.setAlumnos(alumnos);
            }
        });


        // Navegar a NuevoAlumnoFragment cuando se pulsa el FAB
        binding.irANuevoAlumno.setOnClickListener(v -> {
            navController.navigate(R.id.action_listadoAlumnosFragment_to_nuevoAlumnoFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Limpiar la referencia al binding
    }

    // --- Adapter y ViewHolder como clases internas ---
    class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.AlumnoViewHolder> {

        private List<Alumno> alumnos;

        @NonNull
        @Override
        public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflar el layout del viewholder usando su binding
            ViewholderAlumnoBinding vhBinding = ViewholderAlumnoBinding.inflate(getLayoutInflater(), parent, false);
            return new AlumnoViewHolder(vhBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int position) {
            Alumno alumno = alumnos.get(position);
            holder.bind(alumno);
        }

        @Override
        public int getItemCount() {
            return (alumnos != null) ? alumnos.size() : 0;
        }

        public void setAlumnos(List<Alumno> alumnos) {
            this.alumnos = alumnos;
            notifyDataSetChanged();
        }

        public Alumno getAlumnoAt(int position) {
            return alumnos.get(position);
        }

        // ViewHolder
        class AlumnoViewHolder extends RecyclerView.ViewHolder {
            private final ViewholderAlumnoBinding binding;

            public AlumnoViewHolder(@NonNull ViewholderAlumnoBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

                // Lógica de botones del ViewHolder

                // Botón Modificar
                binding.btnModificar.setOnClickListener(v -> {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        try {
                            String nuevoNombre = binding.etNombre.getText().toString();
                            float nuevaNota = Float.parseFloat(binding.etNota.getText().toString());
                            Alumno alumno = getAlumnoAt(position);

                            if (nuevoNombre.isEmpty()) {
                                Toast.makeText(getContext(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Llamar al ViewModel para actualizar
                            alumnoViewModel.actualizar(alumno, nuevoNombre, nuevaNota);
                            Toast.makeText(getContext(), "Alumno modificado", Toast.LENGTH_SHORT).show();

                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "Nota inválida", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // Botón Borrar
                binding.btnBorrar.setOnClickListener(v -> {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Alumno alumno = getAlumnoAt(position);

                        // Llamar al ViewModel para eliminar
                        alumnoViewModel.eliminar(alumno);
                    }
                });
            }

            // Rellenar el layout con los datos del alumno
            public void bind(Alumno alumno) {
                binding.etNombre.setText(alumno.getNombre());
                binding.etNota.setText(String.valueOf(alumno.getNota()));
            }
        }
    }
}