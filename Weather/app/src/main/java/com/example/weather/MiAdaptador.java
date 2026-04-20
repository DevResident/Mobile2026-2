package com.example.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MiAdaptador extends RecyclerView.Adapter<MiViewHolder> {

    private ArrayList<Weather> localDataSet;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Weather weather);
    }

    public MiAdaptador(ArrayList<Weather> dataSet, OnItemClickListener listener) {
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
        Weather weather = localDataSet.get(position);

        // Aqui el set de datos
        String city = weather.getCity();
        viewHolder.getTextViewCity().setText(city);

        String date = weather.getDate();
        viewHolder.getTextViewDate().setText(date);

        // Aqui la imagen
        String url = weather.getPhoto();
        Glide
                .with(viewHolder.itemView.getContext())
                .load(url)
                .centerCrop()
                .into(viewHolder.getImageView());

        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(weather);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void addElemento(Weather newElement) {
        localDataSet.add(newElement);
    }
}
