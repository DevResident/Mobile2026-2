package com.example.clase12;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class CursosFragment extends Fragment {

    public CursosFragment(){
        super(R.layout.fragment_cursos);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvCursos = view.findViewById(R.id.rvCursos);
        rvCursos.setLayoutManager(new LinearLayoutManager(getContext()));

        AppDatabase db = AppDatabase.getInstance(getContext());

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Cursos> existing = db.cursosDao().getAll();
            if (existing.isEmpty()) {
                db.cursosDao().insert(new Cursos(0, "Math"));
            }

            List<Cursos> cursosList = db.cursosDao().getAll();
            ArrayList<Cursos> cursosArrayList = new ArrayList<>(cursosList);

            requireActivity().runOnUiThread(() -> {
                CursosAdapter adapter = new CursosAdapter(cursosArrayList, curso -> {
                    AlumnosFragment alumnosFragment = new AlumnosFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("cursoId", curso.getId());
                    bundle.putString("cursoNombre", curso.getNombre());
                    alumnosFragment.setArguments(bundle);
                    ((MainActivity) requireActivity()).loadFragment(alumnosFragment, true);
                });
                rvCursos.setAdapter(adapter);
            });
        });
    }
}
