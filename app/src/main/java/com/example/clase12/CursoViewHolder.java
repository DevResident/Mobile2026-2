package com.example.clase12;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CursoViewHolder extends RecyclerView.ViewHolder {

    private final TextView textCurso;

    public CursoViewHolder(View view) {
        super(view);

        this.textCurso = view.findViewById(R.id.textCurso);
    }

    public TextView getTextCurso() {
        return textCurso;
    }
}
