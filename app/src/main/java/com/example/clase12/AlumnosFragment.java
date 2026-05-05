package com.example.clase12;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class AlumnosFragment extends Fragment {

    public AlumnosFragment(){
        super(R.layout.fragment_alumnos);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int cursoId = getArguments() != null ? getArguments().getInt("cursoId", -1) : -1;
        String cursoNombre = getArguments() != null ? getArguments().getString("cursoNombre", "Alumnos") : "Alumnos";

        TextView tvTitulo = view.findViewById(R.id.tvTituloAlumnos);
        tvTitulo.setText(cursoNombre);

        RecyclerView rvAlumnos = view.findViewById(R.id.rvAlumnos);
        rvAlumnos.setLayoutManager(new LinearLayoutManager(getContext()));

        AppDatabase db = AppDatabase.getInstance(getContext());

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Alumnos> existing = db.alumnosDao().getAlumnosByCurso(cursoId);
            if (existing.isEmpty() && cursoId != -1) {
                db.alumnosDao().insertAll(
                    new Alumnos(0, "Josefina", "García", "López", cursoId),
                    new Alumnos(0, "Mario", "Pérez", "Martínez", cursoId),
                    new Alumnos(0, "Lucía", "Hernández", "Ramos", cursoId),
                    new Alumnos(0, "Carlos", "Torres", "Vega", cursoId),
                    new Alumnos(0, "Andrea", "Flores", "Soto", cursoId)
                );
            }

            List<Alumnos> alumnosList = db.alumnosDao().getAlumnosByCurso(cursoId);
            ArrayList<Alumnos> alumnosArrayList = new ArrayList<>(alumnosList);

            requireActivity().runOnUiThread(() -> {
                AlumnosAdapter adapter = new AlumnosAdapter(alumnosArrayList, alumno -> {
                    AsistenciasFragment asistenciasFragment = new AsistenciasFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("alumnoId", alumno.getId());
                    bundle.putString("alumnoNombre", alumno.getNombre() + " " + alumno.getPrimerApellido());
                    asistenciasFragment.setArguments(bundle);
                    ((MainActivity) requireActivity()).loadFragment(asistenciasFragment, true);
                });
                rvAlumnos.setAdapter(adapter);
            });
        });
    }
}
