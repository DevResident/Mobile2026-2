package com.example.clase12;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class AsistenciasFragment extends Fragment {

    private AsistenciasAdapter adapter;
    private ArrayList<Asistencias> asistenciasArrayList = new ArrayList<>();
    private AppDatabase db;
    private int alumnoId;

    public AsistenciasFragment() {
        super(R.layout.fragment_asistencias);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alumnoId = getArguments() != null ? getArguments().getInt("alumnoId", -1) : -1;
        String alumnoNombre = getArguments() != null ? getArguments().getString("alumnoNombre", "") : "";

        TextView tvTitulo = view.findViewById(R.id.tvTituloAsistencias);
        tvTitulo.setText(alumnoNombre.isEmpty() ? "Asistencias" : alumnoNombre);

        RecyclerView rvAsistencias = view.findViewById(R.id.rvAsistencias);
        rvAsistencias.setLayoutManager(new LinearLayoutManager(getContext()));

        db = AppDatabase.getInstance(getContext());
        adapter = new AsistenciasAdapter(asistenciasArrayList, asistencia -> { });
        rvAsistencias.setAdapter(adapter);

        view.findViewById(R.id.btnBuscarFecha).setOnClickListener(v -> mostrarDatePickerBusqueda());
        view.findViewById(R.id.btnVerTodas).setOnClickListener(v -> cargarAsistencias());

        FloatingActionButton fab = view.findViewById(R.id.fabAddAsistencia);
        fab.setOnClickListener(v -> mostrarDialogoFecha());

        cargarAsistencias();
    }

    private void cargarAsistencias() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Asistencias> lista = db.asistenciasDao().getAsistenciasByAlumno(alumnoId);
            if (lista.isEmpty() && alumnoId != -1) {
                db.asistenciasDao().insert(new Asistencias(0, "1/5/2026", "Si", alumnoId));
                db.asistenciasDao().insert(new Asistencias(0, "2/5/2026", "No", alumnoId));
                db.asistenciasDao().insert(new Asistencias(0, "3/5/2026", "Si", alumnoId));
                db.asistenciasDao().insert(new Asistencias(0, "4/5/2026", "Si", alumnoId));
                db.asistenciasDao().insert(new Asistencias(0, "5/5/2026", "No", alumnoId));
                lista = db.asistenciasDao().getAsistenciasByAlumno(alumnoId);
            }
            final List<Asistencias> resultado = lista;
            requireActivity().runOnUiThread(() -> actualizarLista(resultado));
        });
    }

    private void buscarPorFecha(String fecha) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Asistencias> lista = db.asistenciasDao().getAsistenciasByAlumnoAndFecha(alumnoId, fecha);
            requireActivity().runOnUiThread(() -> actualizarLista(lista));
        });
    }

    private void actualizarLista(List<Asistencias> lista) {
        asistenciasArrayList.clear();
        asistenciasArrayList.addAll(lista);
        adapter.notifyDataSetChanged();
    }

    private void mostrarDatePickerBusqueda() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (dp, y, m, d) -> {
            buscarPorFecha(d + "/" + (m + 1) + "/" + y);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void mostrarDialogoFecha() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (dp, y, m, d) -> {
            String fecha = d + "/" + (m + 1) + "/" + y;
            mostrarDialogoStatus(fecha);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void mostrarDialogoStatus(String fecha) {
        String[] opciones = {"Si", "No"};
        new AlertDialog.Builder(requireContext())
                .setTitle("¿Asistió el " + fecha + "?")
                .setItems(opciones, (dialog, which) -> guardarAsistencia(fecha, opciones[which]))
                .show();
    }

    private void guardarAsistencia(String fecha, String status) {
        Executors.newSingleThreadExecutor().execute(() -> {
            db.asistenciasDao().insert(new Asistencias(0, fecha, status, alumnoId));
            List<Asistencias> lista = db.asistenciasDao().getAsistenciasByAlumno(alumnoId);
            requireActivity().runOnUiThread(() -> actualizarLista(lista));
        });
    }
}
