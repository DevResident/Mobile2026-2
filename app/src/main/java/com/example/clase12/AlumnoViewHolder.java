package com.example.clase12;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AlumnoViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvInicial;
    private final TextView textNombre;
    private final TextView textApellidos;

    public AlumnoViewHolder(View view) {
        super(view);
        this.tvInicial = view.findViewById(R.id.tvInicial);
        this.textNombre = view.findViewById(R.id.textNombre);
        this.textApellidos = view.findViewById(R.id.textApellidos);
    }

    public TextView getTvInicial() { return tvInicial; }
    public TextView getTextNombre() { return textNombre; }
    public TextView getTextApellidos() { return textApellidos; }
}
