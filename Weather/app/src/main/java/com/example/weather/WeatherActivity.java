package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_characteristics);

        TextView txvCity = findViewById(R.id.textViewCity);
        TextView txvDate = findViewById(R.id.textViewDate);
        TextView txvCelsius = findViewById(R.id.textViewCelsius);
        TextView txvFahrenheit = findViewById(R.id.textViewFahrenheit);
        ImageView img = findViewById(R.id.imageView);

        String city = getIntent().getStringExtra("ciudad");
        txvCity.setText(city);
        String Date = getIntent().getStringExtra("fecha");
        txvDate.setText(Date);
        double celsius = getIntent().getDoubleExtra("celsius", 0.0);
        txvCelsius.setText("Temperatura ºC: " + celsius);
        double fahrenheit = getIntent().getDoubleExtra("fahrenheit", 0.0);
        txvFahrenheit.setText("Temperatura ºF: " + fahrenheit);

        String url = getIntent().getStringExtra("foto");
        Glide.with(this)
                .load(url)
                .centerCrop()
                .into(img);

    }
}
