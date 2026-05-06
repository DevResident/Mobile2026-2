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

public class CursosFragment extends Fragment {

    private CursosAdapter adapter;
    private ArrayList<Cursos> cursosArrayList = new ArrayList<>();
    private TextView tvResumenCursos;
    private AppDatabase db;

    public CursosFragment() {
        super(R.layout.fragment_cursos);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SystemBarInsets.applyStatusBarPadding(view.findViewById(R.id.headerCursos));

        RecyclerView rvCursos = view.findViewById(R.id.rvCursos);
        rvCursos.setLayoutManager(new LinearLayoutManager(getContext()));
        tvResumenCursos = view.findViewById(R.id.tvResumenCursos);

        db = AppDatabase.getInstance(getContext());

        adapter = new CursosAdapter(cursosArrayList, curso -> {
            AlumnosFragment alumnosFragment = new AlumnosFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("cursoId", curso.getId());
            bundle.putString("cursoNombre", curso.getNombre());
            alumnosFragment.setArguments(bundle);
            ((MainActivity) requireActivity()).loadFragment(alumnosFragment, true);
        });
        rvCursos.setAdapter(adapter);

        Executors.newSingleThreadExecutor().execute(() -> {
            if (db.cursosDao().getAll().isEmpty()) {
                db.cursosDao().insert(new Cursos(0, "Matemáticas"));
                db.cursosDao().insert(new Cursos(0, "Ciencias"));
                db.cursosDao().insert(new Cursos(0, "Historia"));
                db.cursosDao().insert(new Cursos(0, "Programación"));
            }
            cargarCursos();
        });

        FloatingActionButton fab = view.findViewById(R.id.fabAgregarCurso);
        fab.setOnClickListener(v -> mostrarDialogAgregarCurso());
    }

    private void cargarCursos() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Cursos> lista = db.cursosDao().getAll();
            if (!isAdded()) return;
            requireActivity().runOnUiThread(() -> {
                if (!isAdded()) return;
                cursosArrayList.clear();
                cursosArrayList.addAll(lista);
                adapter.notifyDataSetChanged();
                int total = cursosArrayList.size();
                tvResumenCursos.setText(total == 1 ? "1 curso disponible" : total + " cursos disponibles");
            });
        });
    }

    private void mostrarDialogAgregarCurso() {
        AgregarCursoBottomSheet sheet = new AgregarCursoBottomSheet();
        sheet.setListener(nombre -> Executors.newSingleThreadExecutor().execute(() -> {
            db.cursosDao().insert(new Cursos(0, nombre));
            cargarCursos();
        }));
        sheet.show(getParentFragmentManager(), "agregar_curso");
    }
}
