package com.example.clase13;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private SimulationEngine engine;
    private AirspaceView airspaceView;
    private TextView tvStep;
    private TextView tvCollisions;
    private Button btnPrev;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajusta el padding del root para que el contenido no quede
        // detrás de la barra de estado ni de la barra de navegación
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left + 12, bars.top + 16, bars.right + 12, bars.bottom + 12);
            return insets;
        });

        engine = new SimulationEngine();

        airspaceView = findViewById(R.id.airspaceView);
        tvStep = findViewById(R.id.tvStep);
        tvCollisions = findViewById(R.id.tvCollisions);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> { engine.nextStep(); updateUI(); });
        btnPrev.setOnClickListener(v -> { engine.prevStep(); updateUI(); });

        updateUI();
    }

    private void updateUI() {
        SimulationState state = engine.currentState();
        tvStep.setText("Paso: " + state.step + " | Grid: " + engine.getGridSize() + "x" + engine.getGridSize());
        tvCollisions.setText("Colisiones: " + state.collisions);
        airspaceView.setState(state, engine.getGridSize());
        btnPrev.setEnabled(engine.canGoPrev());
    }
}
