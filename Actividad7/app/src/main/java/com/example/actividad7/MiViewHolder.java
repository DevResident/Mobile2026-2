package com.example.actividad7;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class MiViewHolder extends RecyclerView.ViewHolder {
    private final TextView textViewName;

    private final TextView textViewDescription;
    private final TextView textViewAttack;
    private final TextView textViewDefense;
    private ImageView imageView;

    public MiViewHolder(View view) {
        super(view);
        // Define click listener for the ViewHolder's View
        this.textViewName = view.findViewById(R.id.textViewNombre);
        this.textViewDescription = view.findViewById(R.id.textViewDescription);
        this.textViewAttack = view.findViewById(R.id.textViewAttack);
        this.textViewDefense = view.findViewById(R.id.textViewDefense);
        this.imageView = view.findViewById(R.id.imageView);
    }

    public TextView getTextViewName() {
        return textViewName;
    }

    public TextView getTextViewDescription() {
        return textViewDescription;
    }

    public TextView getTextViewAttack() {
        return textViewAttack;
    }

    public TextView getTextViewDefense() {
        return textViewDefense;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}