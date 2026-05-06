package com.example.clase12;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class AlumnosFragment extends Fragment {

    private AlumnosAdapter adapter;
    private ArrayList<Alumnos> alumnosArrayList = new ArrayList<>();
    private TextView tvResumenAlumnos;
    private AppDatabase db;
    private int cursoId;

    public AlumnosFragment() {
        super(R.layout.fragment_alumnos);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SystemBarInsets.applyStatusBarPadding(view.findViewById(R.id.headerAlumnos));

        cursoId = getArguments() != null ? getArguments().getInt("cursoId", -1) : -1;
        String cursoNombre = getArguments() != null ? getArguments().getString("cursoNombre", "Alumnos") : "Alumnos";

        TextView tvTitulo = view.findViewById(R.id.tvTituloAlumnos);
        tvTitulo.setText(cursoNombre);
        tvResumenAlumnos = view.findViewById(R.id.tvResumenAlumnos);

        RecyclerView rvAlumnos = view.findViewById(R.id.rvAlumnos);
        rvAlumnos.setLayoutManager(new LinearLayoutManager(getContext()));

        db = AppDatabase.getInstance(getContext());

        adapter = new AlumnosAdapter(alumnosArrayList, alumno -> {
            AsistenciasFragment asistenciasFragment = new AsistenciasFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("alumnoId", alumno.getId());
            bundle.putString("alumnoNombre", alumno.getNombre() + " " + alumno.getPrimerApellido());
            asistenciasFragment.setArguments(bundle);
            ((MainActivity) requireActivity()).loadFragment(asistenciasFragment, true);
        });
        rvAlumnos.setAdapter(adapter);

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
            cargarAlumnos();
        });

        FloatingActionButton fab = view.findViewById(R.id.fabAgregarAlumno);
        fab.setOnClickListener(v -> mostrarDialogAgregarAlumno());
    }

    private void cargarAlumnos() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Alumnos> lista = db.alumnosDao().getAlumnosByCurso(cursoId);
            if (!isAdded()) return;
            requireActivity().runOnUiThread(() -> {
                if (!isAdded()) return;
                alumnosArrayList.clear();
                alumnosArrayList.addAll(lista);
                adapter.notifyDataSetChanged();
                int total = alumnosArrayList.size();
                tvResumenAlumnos.setText(total == 1 ? "1 alumno inscrito" : total + " alumnos inscritos");
            });
        });
    }

    private void mostrarDialogAgregarAlumno() {
        AgregarAlumnoBottomSheet sheet = new AgregarAlumnoBottomSheet();
        sheet.setListener((nombre, primerAp, segundoAp) ->
            Executors.newSingleThreadExecutor().execute(() -> {
                db.alumnosDao().insertAll(new Alumnos(0, nombre, primerAp, segundoAp, cursoId));
                cargarAlumnos();
            })
        );
        sheet.show(getParentFragmentManager(), "agregar_alumno");
    }
}
