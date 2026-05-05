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

    public AsistenciasFragment(){
        super(R.layout.fragment_asistencias);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alumnoId = getArguments() != null ? getArguments().getInt("alumnoId", -1) : -1;
        String alumnoNombre = getArguments() != null ? getArguments().getString("alumnoNombre", "Asistencias") : "Asistencias";

        TextView tvTitulo = view.findViewById(R.id.tvTituloAsistencias);
        tvTitulo.setText("Asistencias - " + alumnoNombre);

        RecyclerView rvAsistencias = view.findViewById(R.id.rvAsistencias);
        rvAsistencias.setLayoutManager(new LinearLayoutManager(getContext()));

        db = AppDatabase.getInstance(getContext());

        adapter = new AsistenciasAdapter(asistenciasArrayList, asistencia -> { });
        rvAsistencias.setAdapter(adapter);

        cargarAsistencias();

        /*FloatingActionButton fab = view.findViewById(R.id.fabAddAsistencia);
        fab.setOnClickListener(v -> mostrarDialogoFecha());*/
    }

    private void cargarAsistencias() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Asistencias> lista = db.asistenciasDao().getAsistenciasByAlumno(alumnoId);
            requireActivity().runOnUiThread(() -> {
                asistenciasArrayList.clear();
                asistenciasArrayList.addAll(lista);
                adapter.notifyDataSetChanged();
            });
        });
    }

    /*private void mostrarDialogoFecha() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (datePicker, y, m, d) -> {
            String fecha = d + "/" + (m + 1) + "/" + y;
            mostrarDialogoStatus(fecha);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void mostrarDialogoStatus(String fecha) {
        String[] opciones = {"Si", "No"};
        new AlertDialog.Builder(requireContext())
                .setTitle("Selecciona el status")
                .setItems(opciones, (dialog, which) -> guardarAsistencia(fecha, opciones[which]))
                .show();
    }

    private void guardarAsistencia(String fecha, String status) {
        Executors.newSingleThreadExecutor().execute(() -> {
            db.asistenciasDao().insert(new Asistencias(0, fecha, status, alumnoId));
            List<Asistencias> lista = db.asistenciasDao().getAsistenciasByAlumno(alumnoId);
            requireActivity().runOnUiThread(() -> {
                asistenciasArrayList.clear();
                asistenciasArrayList.addAll(lista);
                adapter.notifyDataSetChanged();
            });
        });
    }*/
}
