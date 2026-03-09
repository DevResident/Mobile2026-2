package com.example.actividad3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView miGrupo;
    TextView miTexto;
    Button miBoton;

    String [][] alumnos = {
            { "Cristian", "Pedro", "Pablo", "Mauricio" }, // 2291
            { "Kenia", "Paulina", "Giselle", "Alicia", "Claudia" } //2292
    };
    String [] grupos = {"2691", "2692"};
    int indice = 0;
    int indiceGrupo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miBoton = findViewById(R.id.btnMiBoton);
        miGrupo = findViewById(R.id.txvGrupo);
        miTexto = findViewById(R.id.txvMiTexto);

        miBoton.setOnClickListener(this);

        if (alumnos.length > 0) {
            miGrupo.setText(grupos[indiceGrupo]);
            miGrupo.setText(alumnos[indiceGrupo][indice]);
        }
    }

    private void renderizar() {
        if (indiceGrupo >= grupos.length) {
            indiceGrupo = 0;
        }
        indice += 1;

        if (indice >= alumnos[indiceGrupo].length) {
            indice = 0;
            indiceGrupo++;

            if (indiceGrupo >= alumnos.length) {
                indiceGrupo = 0;
            }
        }

        miGrupo.setText(grupos[indiceGrupo]);
        miTexto.setText(alumnos[indiceGrupo][indice]);
    }

    @Override
    public void onClick(View v) {
        renderizar();
    }
}