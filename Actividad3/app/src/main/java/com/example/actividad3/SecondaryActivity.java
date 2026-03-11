package com.example.actividad3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SecondaryActivity extends AppCompatActivity {

    TextView txvTitle;
    Button btnIncremental;

    int contador = 0;

    String myKey = "activity.secondary.myKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        txvTitle = findViewById(R.id.txvTitle);
        btnIncremental = findViewById(R.id.btnIncremental);

        if(savedInstanceState != null && savedInstanceState.containsKey(myKey)){
            contador = savedInstanceState.getInt(myKey);
            showTitle();
        }

        btnIncremental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador++;
                showTitle();
            }
        });
        Log.i("FCA", "onCreate() execute");
    }

    private void showTitle(){
        txvTitle.setText("" + contador);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("FCA", "onCreate() execute");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("FCA", "onResume() execute");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i("FCA", "onPause() execute");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("FCA", "onDestroy() execute");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("FCA", "onRestart() execute");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(myKey, contador);
    }
}
