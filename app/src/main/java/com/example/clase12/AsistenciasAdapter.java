package com.example.clase12;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

    @Override
    public AsistenciaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_asistencias, viewGroup, false);
        return new AsistenciaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AsistenciaViewHolder viewHolder, int position) {
        Asistencias asistencia = localDataSet.get(position);

        viewHolder.getTextFecha().setText(asistencia.getFecha());
        viewHolder.getTextStatus().setText(asistencia.getStatus());

        boolean asistio = "Si".equalsIgnoreCase(asistencia.getStatus());
        int color = asistio ? Color.parseColor("#4CAF50") : Color.parseColor("#E53935");

        GradientDrawable badge = new GradientDrawable();
        badge.setShape(GradientDrawable.RECTANGLE);
        badge.setCornerRadius(40f);
        badge.setColor(color);
        viewHolder.getTextStatus().setBackground(badge);

        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(asistencia);
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
