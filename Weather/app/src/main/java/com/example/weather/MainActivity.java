package com.example.weather;

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
import java.util.List;

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

        miButton = findViewById(R.id.button);
        miInput = findViewById(R.id.textInputEditText);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*AsyncTask.execute(() -> {
            WeatherDao weatherDao = AppDatabase.getInstance(this).weatherDao();
            //userDao.insertAll(new User("Felix ", "María"));
            List<Weather> weatherList = weatherDao.getAll();

            for(Weather weather: weatherList){
                Log.i("Fernando","Weather city: " + weather.city);
                Log.i("Fernando","Weather date: " + weather.date);
                Log.i("Fernando","Weather Celsius: " + weather.celsius);
                Log.i("Fernando","Weather Fahrenheit: " + weather.fahrenheit);
                Log.i("Fernando","Weather photo: " + weather.photo);
            }
        });

        AsyncTask.execute(() -> {
            ArrayList<Weather> misDatos = miCliente.getElements();

            runOnUiThread(() -> {
                adaptador = new MiAdaptador(misDatos, weather -> {
                    clima(weather);
                });
                recyclerView.setAdapter(adaptador);
            });
        });*/

        /*AsyncTask.execute(() -> {
            ArrayList<Weather> misDatos = miCliente.getElements();
            WeatherDao weatherDao = AppDatabase.getInstance(this).weatherDao();

            for (Weather w : misDatos){
                boolean condition = weatherDao.existsRecord(w.city, w.date) == 0;

                if (condition) {
                    weatherDao.insertAll(w);
                }

                List<Weather> weatherList = weatherDao.getAll();
                ArrayList<Weather> storageList = new ArrayList<>(weatherList);

                runOnUiThread(() -> {
                    adaptador = new MiAdaptador(storageList, weather -> {
                        clima(weather);
                    });
                    recyclerView.setAdapter(adaptador);
                });
            }
        });*/

        AsyncTask.execute(() -> {
            WeatherDao weatherDao = AppDatabase.getInstance(this).weatherDao();

            List<Weather> localList = weatherDao.getAll();
            if (!localList.isEmpty()) {
                ArrayList<Weather> storageList = new ArrayList<>(localList);
                runOnUiThread(() -> {
                    adaptador = new MiAdaptador(storageList, weather -> clima(weather));
                    recyclerView.setAdapter(adaptador);
                });
            }

            try {
                MiCliente miCliente = new MiCliente();
                ArrayList<Weather> misDatos = miCliente.getElements();

                for (Weather w : misDatos) {
                    if (weatherDao.existsRecord(w.city, w.date) == 0) {
                        weatherDao.insertAll(w);
                    }
                }

                List<Weather> updatedList = weatherDao.getAll();
                ArrayList<Weather> storageList = new ArrayList<>(updatedList);
                runOnUiThread(() -> {
                    adaptador = new MiAdaptador(storageList, weather -> clima(weather));
                    recyclerView.setAdapter(adaptador);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void clima(Weather weather) {
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("ciudad", weather.getCity());
        intent.putExtra("fecha", weather.getDate());
        intent.putExtra("foto", weather.getPhoto());
        intent.putExtra("celsius", weather.getCelsius());
        intent.putExtra("fahrenheit", weather.getFahrenheit());

        startActivity(intent);
    }
}