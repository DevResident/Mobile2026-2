package com.example.clase12;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlumnosAdapter extends RecyclerView.Adapter<AlumnoViewHolder> {

    private ArrayList<Alumnos> localDataSet;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Alumnos alumnos);
    }

    public AlumnosAdapter(ArrayList<Alumnos> dataSet, OnItemClickListener listener) {
        this.localDataSet = dataSet;
        this.listener = listener;
    }

    @Override
    public AlumnoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_alumnos, viewGroup, false);
        return new AlumnoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlumnoViewHolder viewHolder, int position) {
        Alumnos alumno = localDataSet.get(position);

        String inicial = alumno.getNombre() != null && !alumno.getNombre().isEmpty()
                ? String.valueOf(alumno.getNombre().charAt(0)).toUpperCase()
                : "?";
        viewHolder.getTvInicial().setText(inicial);
        viewHolder.getTextNombre().setText(alumno.getNombre());
        viewHolder.getTextApellidos().setText(alumno.getPrimerApellido() + " " + alumno.getSegundoApellido());

        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(alumno);
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
