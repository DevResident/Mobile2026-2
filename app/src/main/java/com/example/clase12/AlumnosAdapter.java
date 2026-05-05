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

    // Create new views (invoked by the layout manager)
    @Override
    public AlumnoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_alumnos, viewGroup, false);

        return new AlumnoViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AlumnoViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Alumnos alumno = localDataSet.get(position);

        // Aqui el set de datos
        viewHolder.getTextNombre().setText(alumno.getNombre());
        viewHolder.getTextPrimerApellido().setText(alumno.getPrimerApellido());
        viewHolder.getTextSegundoApellido().setText(alumno.getSegundoApellido());

        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(alumno);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void addElemento(Alumnos newElement) {
        localDataSet.add(newElement);
    }
}
