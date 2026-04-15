package com.example.actividad7;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class CaracteristicaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_characteristics);

        TextView txvName = findViewById(R.id.textViewNombre);
        TextView txvDescription = findViewById(R.id.textViewDescription);
        TextView txvAttack = findViewById(R.id.textViewAttack);
        TextView txvDefense = findViewById(R.id.textViewDefense);
        ImageView img = findViewById(R.id.imageView);

        String name = getIntent().getStringExtra("nombre");
        txvName.setText(name);
        String description = getIntent().getStringExtra("descripcion");
        txvDescription.setText(description);
        int attack = getIntent().getIntExtra("ataque", 0);
        txvAttack.setText("Ataque: " + attack);
        int defense = getIntent().getIntExtra("defensa", 0);
        txvDefense.setText("Defensa: " + defense);

        String url = getIntent().getStringExtra("foto");
        Glide.with(this)
                .load(url)
                .centerCrop()
                .into(img);

    }
}
