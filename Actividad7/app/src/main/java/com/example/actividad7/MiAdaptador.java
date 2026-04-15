package com.example.actividad7;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MiAdaptador extends RecyclerView.Adapter<MiViewHolder> {

    private ArrayList<Personaje> localDataSet;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Personaje personaje);
    }

    public MiAdaptador(ArrayList<Personaje> dataSet, OnItemClickListener listener) {
        this.localDataSet = dataSet;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MiViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new MiViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MiViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Personaje personaje = localDataSet.get(position);

        // Aqui el set de datos
        String name = personaje.getName();
        viewHolder.getTextViewName().setText(name);

        // Aqui la imagen
        String url = personaje.getPhoto();
        Glide
                .with(viewHolder.itemView.getContext())
                .load(url)
                .centerCrop()
                .into(viewHolder.getImageView());

        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(personaje);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void addElemento(Personaje newElement) {
        localDataSet.add(newElement);
    }
}