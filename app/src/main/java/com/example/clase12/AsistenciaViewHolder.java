package com.example.clase12;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AsistenciaViewHolder extends RecyclerView.ViewHolder {

    private final TextView textFecha;
    private final TextView textStatus;


    public AsistenciaViewHolder(View view) {
        super(view);

        this.textFecha = view.findViewById(R.id.textFecha);
        this.textStatus = view.findViewById(R.id.textStatus);
    }

    public TextView getTextFecha() {
        return textFecha;
    }

    public TextView getTextStatus() {
        return textStatus;
    }
}
