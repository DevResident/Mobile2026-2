package com.example.weather;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MiViewHolder extends RecyclerView.ViewHolder {

    private final TextView textViewCity;
    private final TextView textViewDate;
    private final TextView textViewCelsius;
    private final TextView textViewFahrenheit;
    private ImageView imageView;

    public MiViewHolder(View view) {
        super(view);
        // Define click listener for the ViewHolder's View
        this.textViewCity = view.findViewById(R.id.textViewCity);
        this.textViewDate = view.findViewById(R.id.textViewDate);
        this.textViewCelsius = view.findViewById(R.id.textViewCelsius);
        this.textViewFahrenheit = view.findViewById(R.id.textViewFahrenheit);
        this.imageView = view.findViewById(R.id.imageView);
    }

    public TextView getTextViewCity() {
        return textViewCity;
    }

    public TextView getTextViewDate() {
        return textViewDate;
    }

    public TextView getTextViewCelsius() {
        return textViewCelsius;
    }

    public TextView getTextViewFahrenheit() {
        return textViewFahrenheit;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
