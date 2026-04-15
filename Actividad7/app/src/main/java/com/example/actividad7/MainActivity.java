package com.example.actividad7;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    MiAdaptador adaptador;

    Button miButton;

    EditText miInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MiCliente miCliente = new MiCliente();
        AsyncTask.execute(() -> {
            ArrayList<Personaje> misDatos = miCliente.getElements();

            runOnUiThread(() -> {
                adaptador = new MiAdaptador(misDatos, personaje -> {
                    caracteristica(personaje);
                });
                recyclerView.setAdapter(adaptador);
            });
        });

        miButton = findViewById(R.id.button);
        miInput = findViewById(R.id.textInputEditText);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*miButton.setOnClickListener(v -> {
            // Obtener elemento
            if (!miInput.getText().toString().isBlank()){
                String newElemento = miInput.getText().toString();
                adaptador.addElemento(newElemento);
            }
        });*/
    }

    private void caracteristica(Personaje personaje) {
        Intent intent = new Intent(this, CaracteristicaActivity.class);
        intent.putExtra("nombre", personaje.getName());
        intent.putExtra("descripcion", personaje.getDescription());
        intent.putExtra("foto", personaje.getPhoto());
        intent.putExtra("ataque", personaje.getAttack());
        intent.putExtra("defensa", personaje.getDefense());

        startActivity(intent);
    }
}