package com.example.clase12;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AlumnoViewHolder extends RecyclerView.ViewHolder {

    private final TextView textNombre;
    private final TextView textPrimerApellido;
    private final TextView textSegundoApellido;

    public AlumnoViewHolder(View view) {
        super(view);

        this.textNombre = view.findViewById(R.id.textNombre);
        this.textPrimerApellido = view.findViewById(R.id.textPrimerApellido);
        this.textSegundoApellido = view.findViewById(R.id.textSegundoApellido);
    }

    public TextView getTextNombre() {
        return textNombre;
    }

    public TextView getTextPrimerApellido() {
        return textPrimerApellido;
    }

    public TextView getTextSegundoApellido() {
        return textSegundoApellido;
    }
}
