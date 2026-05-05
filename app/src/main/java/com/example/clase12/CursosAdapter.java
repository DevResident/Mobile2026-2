package com.example.clase12;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CursosAdapter extends RecyclerView.Adapter<CursoViewHolder> {

    private ArrayList<Cursos> localDataSet;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Cursos cursos);
    }

    public CursosAdapter(ArrayList<Cursos> dataSet, OnItemClickListener listener) {
        this.localDataSet = dataSet;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CursoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cursos, viewGroup, false);

        return new CursoViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CursoViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Cursos curso = localDataSet.get(position);

        // Aqui el set de datos
        viewHolder.getTextCurso().setText(curso.getNombre());

        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(curso);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void addElemento(Cursos newElement) {
        localDataSet.add(newElement);
    }
}
