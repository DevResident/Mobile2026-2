package com.example.clase12;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AsistenciasAdapter extends RecyclerView.Adapter<AsistenciaViewHolder> {

    private ArrayList<Asistencias> localDataSet;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Asistencias asistencias);
    }

    public AsistenciasAdapter(ArrayList<Asistencias> dataSet, OnItemClickListener listener) {
        this.localDataSet = dataSet;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AsistenciaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_asistencias, viewGroup, false);

        return new AsistenciaViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AsistenciaViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Asistencias asistencia = localDataSet.get(position);

        // Aqui el set de datos
        viewHolder.getTextFecha().setText(asistencia.getFecha());
        viewHolder.getTextStatus().setText(asistencia.getStatus());

        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(asistencia);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void addElemento(Asistencias newElement) {
        localDataSet.add(newElement);
    }
}
