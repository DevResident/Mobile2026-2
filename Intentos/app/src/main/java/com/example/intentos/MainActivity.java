package com.example.intentos;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnIntento;

    EditText editText;

    String miTexto = "";

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

        btnIntento = findViewById(R.id.btnIntento);
        btnIntento.setOnClickListener(v ->{
            //Aquí debemos de crear el Intent
            String inputText = editText.getText().toString().trim();
            long n = Long.parseLong(inputText);
            String result = Fibonacci.calcularFibonacci(n);
            Bundle bundle = new Bundle();
            bundle.putString("resultado", result);
            Intent i = new Intent(this, MainActivity2.class);
            i.putExtras(bundle);
            startActivity(i, bundle);
        });

        editText = findViewById(R.id.editInput);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                miTexto = String.valueOf(s);
                Log.i("Fernando", miTexto);
            }
        });
    }
}